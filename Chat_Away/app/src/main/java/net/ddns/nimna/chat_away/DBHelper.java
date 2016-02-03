package net.ddns.nimna.chat_away;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by mhutchinson110082 on 2/1/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "messages.db";
    public static final String TABLE_NAME = "messages";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_RECEIVER = "receiver";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CREATED_AT = "created at";

    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table messages (id integer primary key, sender integer, receiver integer, message text, createdAt text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveMessage(int senderId, int receiverId, String message, String createdAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("senderId", senderId);
        contentValues.put("receiverId", receiverId);
        contentValues.put("message", message);
        contentValues.put("createdAt", createdAt);
        db.insert("messages", null, contentValues);
    }
}
