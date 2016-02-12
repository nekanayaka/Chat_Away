package net.ddns.nimna.chat_away_v2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.*;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;

import net.ddns.nimna.chat_away_v2.Model.User;

import java.util.List;

/**
 * Created by Nimna Ekanayaka on 20/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsernameProfile;
    private TextView tvUsernameProfileAccount;
    private TextView tvEmailProfile;
    private TextView tvAccountLevelProfile;
    private String username;
    private int userID;
    private Button btnChat;
    private ProgressDialog mProgressDialog;
    private ServerRequests sr;
    private BroadcastReceiver mReceiver = null;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private MessageService.MessageServiceInterface messageService;
    private LocationManager locationManager;
    private Criteria criteria;
    private MessageClientListener messageClientListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);

        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        userID = Integer.parseInt(extra.getString("userID"));
        String email = extra.getString("email");
        String accountLevel = extra.getString("accountLevel");
        tvUsernameProfile = (TextView)findViewById(R.id.tvUsernameProfile);
        tvUsernameProfileAccount = (TextView)findViewById(R.id.tvUsernameProfileAccount);
        tvEmailProfile = (TextView)findViewById(R.id.tvEmailProfile);
        tvAccountLevelProfile = (TextView)findViewById(R.id.tvAccountLevelProfile);
        //Intent intent = getIntent();
        tvUsernameProfile.setText(username + "!");
        tvUsernameProfileAccount.setText(username);
        tvEmailProfile.setText(email);
        tvAccountLevelProfile.setText(accountLevel);
        btnChat = (Button)findViewById(R.id.btnChat);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();



        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
        }
//        else{
//            try{
//                Log.d("LOCATION", locationManager.getBestProvider(criteria, true)+"");
//
//                Location local = locationManager.getLastKnownLocation(locationManager.getProvider("gps").getName());
//                local.getLatitude();
//                Log.d("LOCATION", local.getLatitude()+"");
//            }
//
//            catch(NullPointerException e){
//                Log.d("LOCATION", "null pointer exception");
//            }
//        }
        // Create an instance of GoogleAPIClient.
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }







        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchUser();
            }
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
                Toast.makeText(ProfileActivity.this, "Sinch has started and is working!", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();

                // Show a toast message is the Sinch service failed to start
                if (!success) {
                    Toast.makeText(ProfileActivity.this,
                            "Messaging service failed to start",
                            Toast.LENGTH_LONG).show();
                }

            }

        };

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(
                        mReceiver,
                        new IntentFilter(
                                "net.ddns.nimna.chat_away_v2.ProfileActivity"));
    }
    public void setUsername(String username) {
        this.username = username;

    }

    public void fetchUser(){

        sr = new ServerRequests();
        sr.fetchChatUsersDataInBackground("50", "50", userID, new AsyncResponse(){
            @Override
            public void done(User user) {
                if(user==null){
                    Toast.makeText(ProfileActivity.this,
                            "No users found. We will connect you once somebody is available.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(ProfileActivity.this, MessagingActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("RECIPIENT_ID", user.getUserName());
                    messageService.removeMessageClientListener(messageClientListener);
                    startActivity(intent);
                    Log.d("USER_FOUND", "-->"+user.getUserName());
                }
            }
        });

    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SERVICE_CONNECTED", "Service is connected");
            messageService = (MessageService.MessageServiceInterface) iBinder;
            messageClientListener = new MyMessageClientListener();
            messageService.addMessageClientListener(messageClientListener);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messageService = null;
        }
    }

    private class MyMessageClientListener implements MessageClientListener {
        //Notify the user if their message failed to send
        @Override
        public void onMessageFailed(MessageClient client, Message message,
                                    MessageFailureInfo failureInfo) {
            Log.d("FAILURE_INFO", "-->"+failureInfo.toString());
            //Toast.makeText(MessagingActivity.this, "Message failed to send.", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {

        }

        @Override
        public void onIncomingMessage(MessageClient client, Message message) {

            Intent intent = new Intent(ProfileActivity.this, MessagingActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("RECIPIENT_ID", message.getSenderId());
            messageService.removeMessageClientListener(messageClientListener);
            startActivity(intent);


        }
        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {
//            WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
//            messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_OUTGOING);
        }

        //Don't worry about this right now
        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 11: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("LOCATION", "gps enabled");

                    try{
                        Log.d("LOCATION", locationManager.getBestProvider(criteria, true)+"");
                        //noinspection ResourceType
                        Location local = locationManager.getLastKnownLocation(locationManager.getProvider("gps").getName());
                        local.getLatitude();
                        Log.d("LOCATION", local.getLatitude()+"");
                    }

                    catch(NullPointerException e){
                        Log.d("LOCATION", "null pointer exception");
                    }


                } else {
                    Log.d("LOCATION", "gps not enabled");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
