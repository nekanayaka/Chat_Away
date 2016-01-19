package net.ddns.nimna.chat_away;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by nekanayaka on 1/18/2016.
 */
public class SignupActivity extends AppCompatActivity {

    private String username = "", password = "", password2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText tfUsernameReg = (EditText)findViewById(R.id.tfUsernameReg);
        EditText tfPasswordReg = (EditText)findViewById(R.id.tfPasswordReg);
        EditText tfPasswordReg2 = (EditText)findViewById(R.id.tfPasswordReg2);

        username = tfUsernameReg.getText().toString();
        password = tfPasswordReg.getText().toString();
        password2 = tfPasswordReg2.getText().toString();
    }

    public void bttnRegister_Click() {

        if (!password.equals(password2)) {
            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show(); //getApplication(), getApplicationContext(), SignupActivity.this
        }
        else {
            Toast.makeText(SignupActivity.this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
        }
//            try{
//
//                String link= "http://nimna.ddns.net/webservices/signup.php";
//                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
//                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//
//                URL url = new URL(link);
//                URLConnection conn = url.openConnection();
//
//                conn.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                wr.write(data);
//                wr.flush();

//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                StringBuilder sb = new StringBuilder();
//                String line = null;

//                // Read Server Response
//                while((line = reader.readLine()) != null)
//                {
//                    sb.append(line);
//                    break;
//                }
//                return sb.toString();
//            catch(Exception e){
//                return new String("Exception: " + e.getMessage());
//            }
//        }
    }
}
