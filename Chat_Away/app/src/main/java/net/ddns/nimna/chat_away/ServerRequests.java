package net.ddns.nimna.chat_away;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Chris on 2016-01-22.
 */
public class ServerRequests {

    public static final String SERVER_ADDRESS = "http://nimna.ddns.net/webservices/signup.php"; //http://nimna.ddns.net

    public ServerRequests(){

    }

    public void storeUserDataInBackground(User user){
        new StoreUserDataAsyncTask(user).execute();
    }

    public void fetchUserDataInBackground(){

    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void>{

        User user;

        public StoreUserDataAsyncTask(User user){
            this.user = user;
        }
        @Override
        protected Void doInBackground(Void... params) {

            try {

                Log.d("ASYNCTASK", "Got inside task");
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user.getUsername(), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(user.getPassword(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user.getEmail(), "UTF-8");

                URL url = new URL(SERVER_ADDRESS);
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
}
