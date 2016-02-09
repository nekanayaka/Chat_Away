package net.ddns.nimna.chat_away_v2;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nimna Ekanayaka on 20/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsernameProfile;
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
        tvUsernameProfile = (TextView)findViewById(R.id.tvUsernameProfile);
        //Intent intent = getIntent();
        tvUsernameProfile.setText(username);
        btnChat = (Button)findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
