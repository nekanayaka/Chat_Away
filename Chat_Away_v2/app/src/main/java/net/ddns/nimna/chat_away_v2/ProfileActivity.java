package net.ddns.nimna.chat_away_v2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

import com.google.android.gms.location.LocationServices;

/**
 * Created by Nimna Ekanayaka on 20/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsernameProfile;
    private TextView tvUsernameProfileAccount;
    private TextView tvEmailProfile;
    private TextView tvAccountLevelProfile;
    private String username;
    private Button btnChat;
    private ProgressDialog mProgressDialog;
    private BroadcastReceiver mReceiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
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

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LocationManager local = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                Location location = null;
//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_FINE);
//
//                    location = local.getLastKnownLocation(local.getBestProvider(criteria, true));
//
//                location.getLatitude();
//                location.getLongitude();
//                Log.d("LOCATION", "Lat="+location.getLatitude()+" Long="+location.getLongitude());

                Intent intent = new Intent(ProfileActivity.this, MessagingActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("RECIPIENT_ID", "1");
                startActivity(intent);
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
}
