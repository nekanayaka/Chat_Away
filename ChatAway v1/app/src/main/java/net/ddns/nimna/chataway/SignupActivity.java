package net.ddns.nimna.chataway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nimna on 13/01/2016.
 */
public class SignupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onBttnSignupClick(View view) {
        if(view.getId() == R.id.bttnSignup) {
            EditText username = (EditText)findViewById(R.id.tfUname);
            EditText password = (EditText)findViewById(R.id.tfPass);
            EditText password2 = (EditText)findViewById(R.id.tfPass2);

            String strUsername = username.getText().toString();
            String strPassword = password.getText().toString();
            String strPassword2 = password2.getText().toString();

            if(!strPassword.equals(strPassword2)) {
                Toast pass = Toast.makeText(SignupActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                pass.show();
            }
            else {

            }
        }
    }
}
