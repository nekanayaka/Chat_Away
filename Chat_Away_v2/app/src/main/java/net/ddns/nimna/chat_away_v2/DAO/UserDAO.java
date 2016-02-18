package net.ddns.nimna.chat_away_v2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.ddns.nimna.chat_away_v2.Model.User;

public class UserDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "";          // <=== Set to 'null' for an in-memory database
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_ID = "_id"; // <=== This is required
    public static final String COLUMN_USERID = "userId";
    public static final String COLUMN_USERNAME = "username";
    //public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BANSTATUS = "banStatus";
    public static final String COLUMN_REQUESTING = "requestingChat";
    public static final String COLUMN_LEVEL = "accountLevel";
    //public static final String COLUMN_COORDINATES = "coordinates";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final int DB_VERSION = 1;                 // the version of the database

    private static final String SQL_CREATE_TABLE_DELIVERABLES = "CREATE TABLE " + TABLE_NAME + "( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERID + "INTEGER"+
            COLUMN_USERNAME + " INTEGER, " +
            //COLUMN_PASSWORD + " INTEGER, "+
            COLUMN_EMAIL + " TEXT, "+
            //COLUMN_BANSTATUS + " DOUBLE, "+
            //COLUMN_REQUESTING + " INTEGER, "+
            COLUMN_LEVEL + " TEXT, "+
            COLUMN_LATITUDE + " DOUBLE, " +
            COLUMN_LONGITUDE + "DOUBLE);";

    private SQLiteDatabase db;
    private Cursor cursor;


    public UserDAO(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DB_VERSION, errorHandler);
    }

    public UserDAO(Context context, SQLiteDatabase.CursorFactory factory) {
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
    public boolean insertUser(User user) {
        boolean success;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            // NAME, WEIGHT
            values.put("userId", user.getId());
            values.put("username", user.getUserName());
            //values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            //values.put("banStatus", user.getBanStatus());
            //values.put("requestingChat", user.getRequestingChat());
            values.put("accountLevel", user.getAccountLevel());
            values.put("latitude", user.getLatitude());
            values.put("longitude", user.getLongitude());


            db.insert(TABLE_NAME, null, values);

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


    public boolean deleteUser(int userId) {
        boolean success;

        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_USERID + "=" + userId, null);

            success = true;

        } catch( SQLiteException ex ) {
            Log.d("DeliverableDAO", "Failed to delete the record");
            success = false;
        }
        return success;

    }

//    public Cursor getMessagesCursor() {
//
//        ArrayList<Message> messages = new ArrayList<>();
//
//        try {
//            db = this.getReadableDatabase();
//            //db.query(TABLE_NAME, new String[] {COLUMN_NAME, COLUMN_WEIGHT}, COLUMN_NAME+"=?", )
//            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//            cursor.moveToFirst();
//
//        } catch( SQLiteException ex ) {
//        }
//
//
//        return cursor;
//
//    }

//    public ArrayList<Message> getDeliverables() {
//
//        ArrayList<Message> messages = new ArrayList<>();
//
//        try {
//            db = this.getReadableDatabase();
//            //db.query(TABLE_NAME, new String[] {COLUMN_NAME, COLUMN_WEIGHT}, COLUMN_NAME+"=?", )
//            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//            if(cursor.moveToFirst()) {
//                do {
//                    int messageId = cursor.getInt(0);
//                    int messageSender = Integer.parseInt(cursor.getString(1));
//                    int messageReceiver = Integer.parseInt(cursor.getString(2));
//                    String messageMessage = cursor.getString(3);
//                    String messageDateTime = cursor.getString(4);
//                    messages.add(new Message(messageId, messageSender, messageReceiver, messageMessage, messageDateTime));
//                } while( cursor.moveToNext());
//            }
//
//        } catch( SQLiteException ex ) {
//            Log.d("DeliverableDAO", "Failed to retrieve deliverables");
//        }
//
//        return messages;
//
//    }

    public User getUser(int id){

        User user = null;
        db = this.getReadableDatabase();
        cursor =  db.rawQuery( "select * from " + TABLE_NAME + " where " + COLUMN_ID + "="+id+"", null );
        if(cursor.moveToFirst()) {

            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        }
        return user;
    }

    public void closeConnection() {

        if( cursor != null) { cursor.close(); }
        if( db != null) { db.close(); }

    }


}


