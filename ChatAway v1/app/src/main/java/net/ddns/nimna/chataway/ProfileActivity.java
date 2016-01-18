package net.ddns.nimna.chataway;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Nimna on 13/01/2016.
 */
public class ProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String username = getIntent().getStringExtra("Username");
        TextView textView = (TextView)findViewById(R.id.tvUsername);
        textView.setText(username + "!");
    }
}
