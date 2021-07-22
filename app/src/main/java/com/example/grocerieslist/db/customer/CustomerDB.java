package com.example.grocerieslist.db.customer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerieslist.utilities.Constant;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class CustomerDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Constant.DB_Customer;
    public static final String TABLE = "customer";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDR = "address";
    public static final String KEY_ADD1 = "address1";
    public static final String KEY_ADD2 = "address2";
    public static final String KEY_PLACE = "place";
    public static final String KEY_STATE = "state";
    public static final String KEY_SCODE = "scode";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PHONE1 = "phone1";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TYPE = "type";
    public static final String KEY_GST = "GST";
    public static final String KEY_STATUS = "status";

    public CustomerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_NAME + " TEXT,"
                + KEY_ADDR + " TEXT,"
                + KEY_ADD1 + " TEXT,"
                + KEY_ADD2 + " TEXT,"
                + KEY_PLACE + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_SCODE + " TEXT,"
                + KEY_PINCODE + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_PHONE1 + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_GST + " TEXT,"
                + KEY_STATUS + " TEXT" + ")";
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
