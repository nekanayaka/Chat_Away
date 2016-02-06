package net.ddns.nimna.chat_away_v2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.ddns.nimna.chat_away_v2.Model.Message;

import java.util.ArrayList;

public class MessageDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatAway";          // <=== Set to 'null' for an in-memory database
    public static final String TABLE_NAME = "Messages";
    public static final String COLUMN_ID = "_id";           // <=== This is required
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_RECEIVER = "receiver";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATETIME = "dateTime";

    private static final int DB_VERSION = 1;                 // the version of the database

    private static final String SQL_CREATE_TABLE_DELIVERABLES = "CREATE TABLE " + TABLE_NAME + "( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SENDER + " INTEGER, " +
            COLUMN_RECEIVER + " INTEGER, "+
            COLUMN_MESSAGE + " TEXT, "+
            COLUMN_DATETIME + " TEXT);";

    private SQLiteDatabase db;
    private Cursor cursor;


    public MessageDAO(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DB_VERSION, errorHandler);
    }

    public MessageDAO(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the database
        db.execSQL(SQL_CREATE_TABLE_DELIVERABLES);
        // Insert a row into 'Deliverables'.  _id autoincrements so you do not need to add it
//        insertDeliverable(db, "Assignments", 30);
//        insertDeliverable(db, "Midterm", 30);
//        insertDeliverable(db, "Final", 40);

    }

    /**
     *  onUpgrade will execute if the DB_VERSION value is changed.  Android will compare the
     *  new value of version with the old value of version.  Based on that it will call the
     * 'onUpgrade' method
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    // http://www.vogella.com/tutorials/AndroidSQLite/article.html
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        if(oldVersion == 1) {
//            Log.i(CanesCampDAO.class.getName(), "Database version is at 1");
//        } else {
//
//            Log.i(CanesCampDAO.class.getName(),
//                    "Upgrading database from version " + oldVersion + " to "
//                            + newVersion + ", which will destroy all old data");
//            db.execSQL("DROP TABLE IF EXISTS ");
//            onCreate(db);
//        }
    }

    /**
     *
     * Helper method to insert a row into the 'Deliverables' table
     *
     */
    public boolean insertMessage(String name, double weight) {
        boolean success;

        try {
            db = this.getWritableDatabase();
            ContentValues deliverableValues = new ContentValues();

            // NAME, WEIGHT
            deliverableValues.put("name", name);
            deliverableValues.put("weight", weight);


            db.insert(TABLE_NAME, null, deliverableValues);

            success = true;
        } catch ( SQLiteException ex ) {
            Log.d("DeliverableDAO", "Failed to insert into the database");
            success = false;
        }

        return success;
    }

//    public boolean updateMessages(int id, String name, double weight) {
//        boolean success;
//
//        try {
//            db = this.getWritableDatabase();
//            ContentValues deliverableValues = new ContentValues();
//
//            // NAME, WEIGHT
//            deliverableValues.put("name", name);
//            deliverableValues.put("weight", weight);
//
//
//            db.update(TABLE_NAME, deliverableValues, COLUMN_ID + "=" + id, null);
//
//            success = true;
//        } catch( SQLiteException ex ) {
//            Log.d("DeliverableDAO", "Failed to update the record");
//            success = false;
//        }
//
//        return success;
//
//    }


    public boolean deleteMessage(int id) {
        boolean success;

        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + "=" + id, null);

            success = true;

        } catch( SQLiteException ex ) {
            Log.d("DeliverableDAO", "Failed to delete the record");
            success = false;
        }
        return success;

    }

    public Cursor getMessagesCursor() {

        ArrayList<Message> messages = new ArrayList<>();

        try {
            db = this.getReadableDatabase();
            //db.query(TABLE_NAME, new String[] {COLUMN_NAME, COLUMN_WEIGHT}, COLUMN_NAME+"=?", )
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();

        } catch( SQLiteException ex ) {
            Log.d("MessageDAO", "Failed to retrieve messages cursor");
        }


        return cursor;

    }

    public ArrayList<Message> getDeliverables() {

        ArrayList<Message> messages = new ArrayList<>();

        try {
            db = this.getReadableDatabase();
            //db.query(TABLE_NAME, new String[] {COLUMN_NAME, COLUMN_WEIGHT}, COLUMN_NAME+"=?", )
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if(cursor.moveToFirst()) {
                do {
                    int messageId = cursor.getInt(0);
                    int messageSender = Integer.parseInt(cursor.getString(1));
                    int messageReceiver = Integer.parseInt(cursor.getString(2));
                    String messageMessage = cursor.getString(3);
                    String messageDateTime = cursor.getString(4);
                    messages.add(new Message(messageId, messageSender, messageReceiver, messageMessage, messageDateTime));
                } while( cursor.moveToNext());
            }

        } catch( SQLiteException ex ) {
            Log.d("DeliverableDAO", "Failed to retrieve deliverables");
        }

        return messages;

    }

//    public Message getDeliverable(int id){
//
//        Message message = null;
//        db = this.getReadableDatabase();
//        cursor =  db.rawQuery( "select * from " + TABLE_NAME + " where " + COLUMN_ID + "="+id+"", null );
//        if(cursor.moveToFirst()) {
//
//            message = new Message(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
//
//        }
//        return message;
//    }

    public void closeConnection() {

        if( cursor != null) { cursor.close(); }
        if( db != null) { db.close(); }

    }


}


