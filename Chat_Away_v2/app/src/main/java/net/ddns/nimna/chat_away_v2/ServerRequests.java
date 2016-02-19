package net.ddns.nimna.chat_away_v2;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.rebtel.repackaged.com.google.gson.reflect.TypeToken;

import net.ddns.nimna.chat_away_v2.DAO.UserDAO;
import net.ddns.nimna.chat_away_v2.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Chris on 2016-01-22.
 */
public class ServerRequests {

    public static final String SERVER_ADDRESS = "http://nekanaya.hccis.info/webservices/"; //http://nimna.ddns.net

    public ServerRequests(){

    }

    /**
     * This method will instantiate the storing user data AsyncTask and pass it the user that is passed to it
     * @param user
     */
    public void storeUserDataInBackground(User user){
        new StoreUserDataAsyncTask(user).execute();
    }

    /**
     * This method will instantiate the fetch user data Asynctask and pass it the username, password and the AsyncResponse
     * @param username
     * @param password
     * @param response
     */
    public void fetchUserDataInBackground(String username, String password, AsyncResponse response){
        new FetchUserDataAsyncTask(username, password, response).execute();
    }

    public void fetchChatUsersDataInBackground(String lon, String lat, int userID, AsyncResponse response){
        new FetchChatUsersDataAsyncTask(lon, lat, userID, response).execute();
    }

    public void fetchGroupDataInBackground(String lon, String lat, int userID, AsyncReturnRecipients response){
        new FetchGroupDataAsyncTask(lon, lat, userID, response).execute();
    }
    public void RequestInBackground(int userID){
        new RequestAsyncTask(userID).execute();
    }


    /**
     * This method will be used to send data to the server and get a response from the server
     * @param data
     * @param fileName
     * @return
     */
    public String getServerResponse(String data, String fileName){

        String serverResponse = "";

        try{
            //the URL to access the server
            URL url = new URL(SERVER_ADDRESS+fileName);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            //writing data to the server
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

            serverResponse = sb.toString();

            Log.d("SERVER-RESPONSE", sb.toString());
        }catch (MalformedURLException e){
            Log.d("MALFORMED", "EXCEPTION");
            e.printStackTrace();
            return null;
        }catch(IOException e){
            Log.d("IO", "EXCEPTION");
            e.printStackTrace();
            return null;
        }

        return serverResponse;
    }

    /**
     * This is an AsyncTask that will be used to store user data when the register
     */
    public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void>{

        //this user object will be used later
        User user;

        //constructor for the async task that accepts a user as a paramater
        public StoreUserDataAsyncTask(User user){
            this.user = user;
        }
        //The task that will be done in the background
        @Override
        protected Void doInBackground(Void... params) {

            //declaring the file name of the webservice that will communicate with the database
            String fileName = "chataway_signup.php";
            String data = "";

            try{
                //getting the data that will be sent to the php webservice
                data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user.getUserName(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(user.getPassword(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user.getEmail(), "UTF-8");
                data += "&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(user.getLatitude(), "UTF-8");
                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(user.getLongitude(), "UTF-8");
                data += "&" + URLEncoder.encode("accountLevel", "UTF-8") + "=" + URLEncoder.encode(user.getAccountLevel(), "UTF-8");
                data += "&" + URLEncoder.encode("banStatus", "UTF-8") + "=" + URLEncoder.encode(user.getBanStatus(), "UTF-8");
                data += "&" + URLEncoder.encode("requestingChat", "UTF-8") + "=" + URLEncoder.encode(user.getRequestingChat(), "UTF-8");

            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
                return null;
            }
                //calling the method that will get a response and passing it the filename and the data
                getServerResponse(data, fileName);
                //nothing should be returned
                return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /**
     * This class is an AsyncTask that will get the users data when they sign in
     */
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, String>{

        //the username, password and an AsyncResponse that will allow the users info to be passed back to the calling activity
        String username;
        String password;
        AsyncResponse response;

        String fileName = "chataway_login.php";

        //constructor that will accept a username, password, and asyncresponse
        public FetchUserDataAsyncTask(String username, String password, AsyncResponse response){

            this.username = username;
            this.password = password;
            this.response = response;
        }
        //the following is done in the background
        @Override
        protected String doInBackground(Void... params) {

            try {
                //the username and password will be passed to the php webservice
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                //the server will respond with the users data in json format
                String userData = getServerResponse(data, fileName);

                Log.d("User_Data", "-->"+userData+"<--");
                return userData;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userData) {
            super.onPostExecute(userData);

            //if the user was not found then the user is kept on the login page
            if(userData.equalsIgnoreCase("not found ")){
                response.done(null);
            } else {
                //if the user is found the user data that the server responed with is converted from json format
                try {
                    JSONObject jobject = new JSONObject(userData);
                    if(jobject.length()==0){

                    } else {
                        String username = jobject.getString("username");
                        //String password = jobject.getString("password");
                        String accountLevel = jobject.getString("accountLevel");
                        String email = jobject.getString("email");
                        //String coordinates = jobject.getString("coordinates");
                        String latitude = jobject.getString("latitude");
                        String longitude = jobject.getString("longitude");
                        int userID = jobject.getInt("userID");

                        //creating a new User object and passing that back to the calling activity via the AsyncResponse interface
                        User user = new User(userID, username, email, accountLevel, latitude, longitude);
                        response.done(user);
                        Log.d("USER", user.getUserName()+", "+user.getId());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class FetchChatUsersDataAsyncTask extends AsyncTask<Void, Void, String>{

        String latitude;
        String longitude;
        int userID;
        AsyncResponse response;

        String fileName = "chataway_users.php";

        public FetchChatUsersDataAsyncTask(String lat, String lon, int userID, AsyncResponse response){

            this.latitude = lat;
            this.longitude = lon;
            this.userID = userID;
            this.response = response;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                String data  = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
                data += "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID+"", "UTF-8");

                String userData = getServerResponse(data, fileName);

                Log.d("User_Data", userData);
                return userData;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userData) {
            super.onPostExecute(userData);
            Log.d("POSTEXECUTE", "IN POST EXECUTE");
            try {
                if(userData.equalsIgnoreCase("not found ")){
                    response.done(null);
                } else {
                    JSONObject jobject = new JSONObject(userData);
                    if(jobject.length()==0){
                        response.done(null);
                    } else {
                        String username = jobject.getString("username");
                        //String password = jobject.getString("password");
                        String accountLevel = jobject.getString("accountLevel");
                        String email = jobject.getString("email");
                        String latitude = jobject.getString("latitude");
                        String longitude = jobject.getString("longitude");
                        int userID = jobject.getInt("userID");

                        User user = new User(userID, username, email, accountLevel, latitude, longitude);
                        Log.d("USER", user.getUserName()+", "+user.getId());
                        //UserDAO db = new UserDAO(null, null);
                        //db.insertUser(user);
                        response.done(user);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class FetchGroupDataAsyncTask extends AsyncTask<Void, Void, String>{

        String latitude;
        String longitude;
        int userID;
        AsyncReturnRecipients response;

        String fileName = "chataway_groupchat.php";

        public FetchGroupDataAsyncTask(String lat, String lon, int userID, AsyncReturnRecipients response){

            this.latitude = lat;
            this.longitude = lon;
            this.userID = userID;
            this.response = response;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                String data  = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
                data += "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID+"", "UTF-8");

                String userData = getServerResponse(data, fileName);

                Log.d("User_Data", userData);
                return userData;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userData) {
            super.onPostExecute(userData);


            if(userData.equalsIgnoreCase("not found ")){
                response.returnRecipients(null);
            } else {
                Log.d("GROUP_FOUND", "-->"+userData);
                Gson gson = new Gson();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(userData);
                    Type listType = new TypeToken<List<String>>(){}.getType();
                    List<String> bookList = gson.fromJson(jsonArray.toString(), listType);
                    response.returnRecipients(bookList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }
    public class RequestAsyncTask extends AsyncTask<Void, Void, Void>{

        int userID;

        String fileName = "chataway_reqeuesting.php";

        public RequestAsyncTask(int userID){

            this.userID = userID;

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                String data  = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID+"", "UTF-8");


                String userData = getServerResponse(data, fileName);

                Log.d("User_Data", userData);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



        }
    }
}


