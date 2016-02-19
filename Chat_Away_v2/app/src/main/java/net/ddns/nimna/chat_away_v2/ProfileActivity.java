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


import com.google.android.gms.common.ConnectionResult;
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
public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    //views
    private TextView tvUsernameProfile;
    private TextView tvUsernameProfileAccount;
    private TextView tvEmailProfile;
    private TextView tvAccountLevelProfile;
    private Button btnChat;
    private Button btnGroup;
    private ProgressDialog mProgressDialog;
    //User info
    private String username;
    private int userID;
    private boolean requestingGroupChat;
    //Services
    private ServerRequests sr;
    private BroadcastReceiver mReceiver = null;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private MessageService.MessageServiceInterface messageService;
    private MessageClientListener messageClientListener;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);

        //getting users information via intent getExtras()
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        userID = Integer.parseInt(extra.getString("userID"));
        String email = extra.getString("email");
        String accountLevel = extra.getString("accountLevel");

        //accessing the Views TextViews
        tvUsernameProfile = (TextView)findViewById(R.id.tvUsernameProfile);
        tvUsernameProfileAccount = (TextView)findViewById(R.id.tvUsernameProfileAccount);
        tvEmailProfile = (TextView)findViewById(R.id.tvEmailProfile);
        tvAccountLevelProfile = (TextView)findViewById(R.id.tvAccountLevelProfile);

        //setting the text to display the proper users information
        tvUsernameProfile.setText(username + "!");
        tvUsernameProfileAccount.setText(username);
        tvEmailProfile.setText(email);
        tvAccountLevelProfile.setText(accountLevel);

        btnChat = (Button)findViewById(R.id.btnChat);
        btnGroup = (Button)findViewById(R.id.btnGroup);

        //groupchat boolean is initially set to false
        requestingGroupChat = false;
        
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //listening for if user clicks Chat
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestingGroupChat = false;
                fetchUser();
            }
        });

        //listening for if user clicks Group Chat
        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestingGroupChat = true;
                fetchGroup();
            }
        });

        //The following is for displaying a progress dialog of the Sinch MessageService starting
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

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy(){
        //when the profile activity is destroyed then the users requestingChat is set to 0 in the database
        super.onDestroy();
        sr = new ServerRequests();
        sr.RequestInBackground(userID);
    }

    @Override
    public void onConnected(Bundle bundle) {
        //REQUIRED check for permissions
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
        }
        //Getting location
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //Checking location and logging
        if (mLastLocation != null) {

            Log.d("LOCATION", "Latitude ="+String.valueOf(mLastLocation.getLatitude()));
            Log.d("LOCATION", "Longtude ="+String.valueOf(mLastLocation.getLongitude()));

        }
    }
    //REQUIRED
    @Override
    public void onConnectionSuspended(int i) {

    }

    public void setUsername(String username) {
        this.username = username;

    }


    /**
     * This method will check for an available user and connect the user or display a message if no user is found
     */
    public void fetchUser(){

        //new ServerRequests object is created and the method to fetch an available user is called
        sr = new ServerRequests();
        sr.fetchChatUsersDataInBackground(String.valueOf(mLastLocation.getLongitude()), String.valueOf(mLastLocation.getLatitude()), userID, new AsyncResponse(){
            @Override
            public void done(User user) {
                //if no user is found the user is displayed an appropriate message
                if(user==null){
                    Toast.makeText(ProfileActivity.this,
                            "No users found. We will connect you once somebody is available.",
                            Toast.LENGTH_LONG).show();
                    Log.d("FETCHUSERS", "no user found");
                } else {
                    //if another user is found then the user is brough to the messaging activity
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

    /**
     * This method will check to see if a group chat is available
     */
    public void fetchGroup(){

        sr = new ServerRequests();
        sr.fetchGroupDataInBackground(String.valueOf(mLastLocation.getLongitude()), String.valueOf(mLastLocation.getLatitude()), userID, new AsyncReturnRecipients() {
            @Override
            public void returnRecipients(List<String> recipients) {
                //if no group is found then the user is displayed a message and a new group chat is created in the database
                if (recipients == null) {
                    Toast.makeText(ProfileActivity.this,
                            "No group found. We will connect you once somebody is available.",
                            Toast.LENGTH_LONG).show();

                } else {
                    //if a group is found then the recipients of the groupchat are obtained via the async task
                    String[] theRecipients = new String[recipients.size()];
                    int count = 0;
                    for (String recip : recipients) {
                        theRecipients[count] = recip;
                        count++;
                    }
                    //the user is then brough to the GroupMessage activity and the list of recipients is passed via the intent
                    Intent intent = new Intent(ProfileActivity.this, GroupMessagingActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("RECIPIENT_ID", theRecipients);

                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    /**
     * A listener is implemented on this activity is case a user is not found and the user is waiting to be connected to another user
     */
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

            if(requestingGroupChat){
                String[] theRecipients = new String[1];
                theRecipients[0] = message.getSenderId();

                Intent intent = new Intent(ProfileActivity.this, GroupMessagingActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("RECIPIENT_ID", theRecipients);
                messageService.removeMessageClientListener(messageClientListener);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ProfileActivity.this, MessagingActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("RECIPIENT_ID", message.getSenderId());
                messageService.removeMessageClientListener(messageClientListener);
                startActivity(intent);
            }



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


}
