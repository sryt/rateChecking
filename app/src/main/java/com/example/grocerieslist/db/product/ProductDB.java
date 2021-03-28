package com.example.grocerieslist.db.product;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import com.example.grocerieslist.utilities.Constant;

/**
 * Created by Tejaswi on 08/12/20.
 */
public class ProductDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Constant.DB_Product;
    public static final String TABLE = "products";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_TS = "ts";
    public static final String KEY_PACK_UOM = "packuom";
    public static final String KEY_UOM = "uom";
    public static final String KEY_PACKING_SIZE = "packingsize";
    public static final String KEY_MRP = "mrp";
    public static final String KEY_PUR_RATE = "purRate";
    public static final String KEY_COST = "cost";
    public static final String KEY_SPECIAL = "special";
    public static final String KEY_RETAIL = "retail";
    public static final String KEY_CASE_QTY = "caseqty";
    public static final String KEY_SHEET_NO = "sheetNo";
    public static final String KEY_GST = "GST";
    public static final String KEY_HSN = "HSN";
    public static final String KEY_TYPE = "types";
    public static final String KEY_GROUP = "groups";
    public static final String KEY_FireBase_Id = "firebaseId";
    public static final String KEY_STATUS = "status";

    public ProductDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_TS + " TEXT,"
                + KEY_FireBase_Id + " TEXT,"
                + KEY_PACK_UOM + " TEXT,"
                + KEY_HSN + " TEXT,"
                + KEY_GST + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_GROUP + " TEXT,"
                + KEY_PACKING_SIZE + " TEXT,"
                + KEY_UOM + " TEXT,"
                + KEY_CASE_QTY + " TEXT,"
                + KEY_SHEET_NO + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_MRP + " TEXT,"
                + KEY_PUR_RATE + " TEXT,"
                + KEY_COST + " TEXT,"
                + KEY_SPECIAL + " TEXT,"
                + KEY_RETAIL + " TEXT" + ")";
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
