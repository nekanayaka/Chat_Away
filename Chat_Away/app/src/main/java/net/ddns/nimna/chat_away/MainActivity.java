package net.ddns.nimna.chat_away;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nimna on 24/01/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
<<<<<<< HEAD
=======
        //just commenting out for now to avoid the crash.
//        DBHelper db = new DBHelper(this);
//        SQLiteDatabase mydatabase = openOrCreateDatabase("messages.db",MODE_PRIVATE,null);
//        db.onCreate(mydatabase);
//        db.saveMessage(0, 1, "message test", "date11");
//        try {
//            Thread.sleep(5000);
//            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
//            startActivity(intent);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
>>>>>>> cf7d866bc0fa27fa6f018c6980022358ad1ccaef
    }
}
