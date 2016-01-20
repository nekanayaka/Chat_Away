package net.ddns.nimna.chat_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Nimna Ekanayaka
 * @since 1/18/2016
 */
public class MainActivity extends AppCompatActivity {

//    private TextView tvSignup;
//    private Button bttnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tvSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                tvSignup_Click(v);
//                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void bttnSignup_Click(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
