package net.ddns.nimna.chat_away_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.nimna.chat_away_v2.Model.User;

import java.io.UnsupportedEncodingException;
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


        //listening for register button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting data that the user entered
                password = tfPasswordReg.getText().toString();
                password2 = tfPasswordReg2.getText().toString();

                username = tfUsernameReg.getText().toString();
                email = tfEmailReg.getText().toString();

                //if the user was unable to confirm password they are displayed a message
                if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show(); //getApplication(), getApplicationContext(), SignupActivity.this
                    //message displayed if the user doesn't fill out the form completely
                } else if (username.equals("") || password.equals("") || email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in the information!", Toast.LENGTH_SHORT).show();
                } else {
                    //if the information is filled in properly the userSignUp method is called
                    String accountLevel = "regular";
                    String latitude = "50";
                    String longitude = "50";
                    User user = new User(0, username, email, encryptPassword(password), "0", "0", accountLevel, latitude, longitude);
                    userSignUp(user);

                }
            }
        });



    }

    /**
     * This method will use the ServerRequests class to store users data in database or display a message if an issue occured
     * @param user
     * @return
     */
    private ServerRequests userSignUp(User user){
        //creating new ServerRequests object and calling the method for storing users data
        ServerRequests sr = new ServerRequests();
        sr.storeUserDataInBackground(user, new AsyncValidSignUp() {
            @Override
            public void done(String result) {
                //if the server responds with fail then the users info was not stored and they are displayed an error
                if(result.equalsIgnoreCase("fail")){
                    Toast.makeText(SignupActivity.this, "Profile failed to create", Toast.LENGTH_SHORT).show();
                } else {
                    //if the server didn't respond with fail then the user is displayed a message and brought to the sign in page
                    Toast.makeText(SignupActivity.this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(i);
                }
            }
        });
        return sr;
    }

    /**
     * Method for encrypting password
     * @param password
     * @return
     */
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
