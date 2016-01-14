package net.ddns.nimna.chataway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        if(view.getId() == R.id.bttnLogin) {
            EditText a = (EditText)findViewById(R.id.tfUname);
            String str = a.getText().toString();
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("Username", str);
            startActivity(intent);
        }
        if(view.getId() == R.id.bttnSignup) {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    }
}
