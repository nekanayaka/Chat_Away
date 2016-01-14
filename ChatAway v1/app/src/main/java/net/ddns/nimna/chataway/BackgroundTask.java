package net.ddns.nimna.chataway;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Nimna on 14/01/2016.
 */
public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private Context context;

    public BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
