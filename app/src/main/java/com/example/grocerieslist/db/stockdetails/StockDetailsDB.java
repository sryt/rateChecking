package com.example.grocerieslist.db.stockdetails;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerieslist.utilities.Constant;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class StockDetailsDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Constant.DB_Stock_Details;
    public static final String TABLE = "place";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_STATE = "state";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_DESC = "descripton";
    public static final String KEY_TYPE = "type";
    public static final String KEY_STATUS = "status";
    public static final String KEY_FLAG = "flag";

    public StockDetailsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_PINCODE + " TEXT,"
                + KEY_NUMBER + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_FLAG + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }
}
