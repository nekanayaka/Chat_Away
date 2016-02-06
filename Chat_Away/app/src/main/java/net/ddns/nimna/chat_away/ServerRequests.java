package net.ddns.nimna.chat_away;

import android.os.AsyncTask;
import android.util.Log;

import net.ddns.nimna.chat_away.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Chris on 2016-01-22.
 */
public class ServerRequests {

    public static final String SERVER_ADDRESS = "http://nekanaya.hccis.info/webservices/"; //http://nimna.ddns.net

    public ServerRequests(){

    }

    public void storeUserDataInBackground(User user){
        new StoreUserDataAsyncTask(user).execute();
    }

    public void fetchUserDataInBackground(String username, String password){
        new FetchUserDataAsyncTask(username, password).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void>{

        User user;

        public StoreUserDataAsyncTask(User user){
            this.user = user;
        }
        @Override
        protected Void doInBackground(Void... params) {

            String fileName = "chataway_signup.php";

            try {

                Log.d("ASYNCTASK", "Got inside task");
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user.getUserName(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(user.getPassword(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("coordinates", "UTF-8") + "=" + URLEncoder.encode(user.getCoordinates(), "UTF-8");
                data += "&" + URLEncoder.encode("accountLevel", "UTF-8") + "=" + URLEncoder.encode(user.getAccountLevel(), "UTF-8");

                URL url = new URL(SERVER_ADDRESS+fileName);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                Log.d("SERVER-RESPONSE", sb.toString());
                return null;

            } catch (MalformedURLException e) {
                Log.d("MALFORMED", "EXCEPTION");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.d("IO", "EXCEPTION");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, String>{

        String username;
        String password;

        String fileName = "chataway_login.php";

        public FetchUserDataAsyncTask(String username, String password){

            this.username = username;
            this.password = password;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {

                Log.d("ASYNCTASK", "Got inside task");
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");


                URL url = new URL(SERVER_ADDRESS+fileName);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                String userData = sb.toString();
                Log.d("User_Data", userData);
                return userData;

            } catch (MalformedURLException e) {
                Log.d("MALFORMED", "EXCEPTION");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.d("IO", "EXCEPTION");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userData) {
            super.onPostExecute(userData);

            try {
                JSONObject jobject = new JSONObject(userData);
                if(jobject.length() == 0) {

                } else {
                    String username = jobject.getString("username");
                    String password = jobject.getString("password");
                    String accountLevel = jobject.getString("accountLevel");
                    String coordinates = jobject.getString("coordinates");
                    int userID = jobject.getInt("userID");

                    User user = new User(username, userID, "something", password, accountLevel, coordinates);
                    SigninActivity signinInstance = new SigninActivity();
                    signinInstance.setUser(user);
                    Log.d("USER", user.getUserName()+", "+user.getId()+", "+user.getPassword());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class FetchChatUsersDataAsyncTask extends AsyncTask<Void, Void, String>{

        String latitude;
        String longitude;

        String fileName = "chataway_login.php";

        public FetchChatUsersDataAsyncTask(String lat, String lon){

            this.latitude = lat;
            this.longitude = lon;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {

                Log.d("ASYNCTASK", "Got inside task");
                String data  = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");


                URL url = new URL(SERVER_ADDRESS+fileName);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                String userData = sb.toString();
                Log.d("User_Data", userData);
                return userData;

            } catch (MalformedURLException e) {
                Log.d("MALFORMED", "EXCEPTION");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.d("IO", "EXCEPTION");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userData) {
            super.onPostExecute(userData);

            try {
                JSONObject jobject = new JSONObject(userData);
                if(jobject.length()==0){

                } else {
                    String username = jobject.getString("username");
                    String password = jobject.getString("password");
                    String accountLevel = jobject.getString("accountLevel");
                    String latitiude = jobject.getString("latitude");
                    String longitude = jobject.getString("longitude");
                    int userID = jobject.getInt("userID");

                    User user = new User(username, userID, "something", password, accountLevel, latitude, longitude);
                    Log.d("USER", user.getUserName()+", "+user.getId()+", "+user.getPassword());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


