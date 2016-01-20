package net.ddns.nimna.chat_away;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nimna Ekanayaka on 20/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {
    private TextView tvUsernameProfile;
    private String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsernameProfile = (TextView)findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(username);
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
