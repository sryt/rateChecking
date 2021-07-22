package com.example.grocerieslist.db.stock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerieslist.utilities.Constant;

/**
 * Created by Tejaswi on 22/07/21.
 */
public class StockDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Constant.DB_Stock;
    public static final String TABLE = "prodcutstock";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CUST_ID = "custId";
    public static final String KEY_TS = "ts";
    public static final String KEY_TYPE = "type";
    public static final String KEY_QTY = "qty";
    public static final String KEY_REMARK = "remark";

    public StockDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_NAME + " TEXT,"
                + KEY_CUST_ID + " TEXT,"
                + KEY_TS + " TEXT,"
                + KEY_QTY + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_REMARK + " TEXT" + ")";
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
