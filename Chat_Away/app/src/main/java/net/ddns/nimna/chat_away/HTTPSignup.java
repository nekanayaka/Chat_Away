package net.ddns.nimna.chat_away;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Nimna Ekanayaka on 19/01/2016.
 */
public class HTTPSignup {
    private final String signupURL = "http://nimna.ddns.net/webservices/signup.php";
    private String username = "", password = "", email = "";

    public HTTPSignup(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        try {
            URL url = new URL(signupURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String userInfo = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(this.username, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(this.password, "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(this.email, "UTF-8");
            bufferedWriter.write(userInfo);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            //InputStream inputStream = httpURLConnection.getInputStream();
            //inputStream.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
