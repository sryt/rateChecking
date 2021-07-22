package com.example.grocerieslist.db.sales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerieslist.utilities.Constant;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class SalesDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Constant.DB_Sales;
    public static final String TABLE = "Sales";
    public static final String KEY_BILL_ON = "billNo";
    public static final String KEY_CUST_NAME = "custName";
    public static final String KEY_TS = "ts";
    public static final String KEY_PROD = "prod";
    public static final String KEY_QTY= "qty";
    public static final String KEY_RATE = "rate";
    public static final String KEY_MRP = "mrp";
    public static final String KEY_AMT = "amount";
    public static final String KEY_FLAG = "flag";

    public SalesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_BILL_ON + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_CUST_NAME + " TEXT,"
                + KEY_TS + " TEXT,"
                + KEY_PROD + " TEXT,"
                + KEY_QTY + " TEXT,"
                + KEY_RATE + " TEXT,"
                + KEY_MRP + " TEXT,"
                + KEY_AMT + " TEXT,"
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
