//package net.ddns.nimna.chat_away;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//
///**
// * Created by Nimna Ekanayaka on 19/01/2016.
// */
//public class HTTPSignup extends AsyncTask<Void,Void,Void> {
//    private final String signupURL = "http://nimna.ddns.net/webservices/signup.php";
//    private final Context context;
//    private String username = "", password = "", email = "";
//
//    public HTTPSignup(Context context) {
//        this.context = context;
//
//    }
//
//    @Override
//    protected Void doInBackground(String... arg0) {
//
//        try {
//            String username = (String) arg0[0];
//            String password = (String) arg0[1];
//            String email = (String) arg0[2];
//
//            URL url = new URL(signupURL);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoOutput(true);
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//            BufferedWriter bufferedWriter =
//                    new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//            String userInfo = URLEncoder.encode("username", "UTF-8") + "=" +
//                    URLEncoder.encode(this.username, "UTF-8") + "&" +
//                    URLEncoder.encode("password", "UTF-8") + "=" +
//                    URLEncoder.encode(this.password, "UTF-8") + "&" +
//                    URLEncoder.encode("email", "UTF-8") + "=" +
//                    URLEncoder.encode(this.email, "UTF-8");
//            bufferedWriter.write(userInfo);
//            bufferedWriter.flush();
//            bufferedWriter.close();
//            outputStream.close();
////            InputStream inputStream = httpURLConnection.getInputStream();
////            inputStream.close();
////            httpURLConnection.disconnect();
//            return null;
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}

