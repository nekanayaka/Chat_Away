package net.ddns.nimna.chat_away;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by nekanayaka on 1/18/2016.
 */
public class SignupActivity extends AppCompatActivity {

    private String username = "", email = "", password = "", password2 = "";
    private Button registerButton;
    private EditText tfUsernameReg;
    private EditText tfEmailReg;
    private EditText tfPasswordReg;
    private EditText tfPasswordReg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tfUsernameReg = (EditText)findViewById(R.id.tfUsernameReg);
        tfEmailReg = (EditText)findViewById(R.id.tfEmailReg);
        tfPasswordReg = (EditText)findViewById(R.id.tfPasswordReg);
        tfPasswordReg2 = (EditText)findViewById(R.id.tfPasswordReg2);
        registerButton = (Button) findViewById(R.id.bttnRegister);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = tfPasswordReg.getText().toString();
                password2 = tfPasswordReg2.getText().toString();

                username = tfUsernameReg.getText().toString();
                email = tfEmailReg.getText().toString();

                Log.d("PASSWORD1",password);
                Log.d("PASSWORD2",password2);
                if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show(); //getApplication(), getApplicationContext(), SignupActivity.this
                }
                else {
                    User user = new User(username, 0, email, encryptPassword(password));
                    userSignUp(user);
                    Toast.makeText(SignupActivity.this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(SignupActivity.this, ProfileActivity.class);
//                    i.putExtra("username", username);
//                    startActivity(i);
                }
            }
        });



    }

    private void userSignUp(User user){
        ServerRequests sr = new ServerRequests();
        sr.storeUserDataInBackground(user);
    }

    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
