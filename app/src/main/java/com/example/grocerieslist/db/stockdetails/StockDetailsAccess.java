package com.example.grocerieslist.db.stockdetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.db.prodImg.ProdImgAccess;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class StockDetailsAccess {
    String TAG = ProdImgAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private StockDetailsDB balanceHelper;
    AppGlobal global;

    public StockDetailsAccess(Context context)	{
        balanceHelper = new StockDetailsDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }

    public long addStockDetails(StockDetailsClass prod) {
        if (isExist(prod))
            return -1;

        ContentValues values = new ContentValues();
        values.put(StockDetailsDB.KEY_NAME,prod.getName());
        values.put(StockDetailsDB.KEY_ADDRESS,prod.getAddress());
        values.put(StockDetailsDB.KEY_STATE,prod.getState());
        values.put(StockDetailsDB.KEY_PINCODE,prod.getPincode());
        values.put(StockDetailsDB.KEY_NUMBER,prod.getNumber());
        values.put(StockDetailsDB.KEY_DESC,prod.getDesc());
        values.put(StockDetailsDB.KEY_TYPE,prod.getType());
        values.put(StockDetailsDB.KEY_STATUS, prod.getStatus());
        values.put(StockDetailsDB.KEY_FLAG,prod.getFlag());

        long res = database.insert(StockDetailsDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    public boolean isExist(StockDetailsClass prod){
        boolean res = false;
        String myquery = "select * from " + StockDetailsDB.TABLE +
                " where "+ StockDetailsDB.KEY_NAME+" = '"+prod.getName()+"' and "+StockDetailsDB.KEY_TYPE+" = '"+prod.getType()+"'";

        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0){
            res = true;
        }
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<StockDetailsClass> getStockDetails(){
        List<StockDetailsClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDetailsDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockDetailsClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public StockDetailsClass getStockDetailsById(String id){
        StockDetailsClass comments = null;
        String myquery = "select * from " + StockDetailsDB.TABLE +
                " where "+ StockDetailsDB.KEY_ID+" = '"+id+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockDetailsClass name = cursorTotemp(cursor);
            comments = name;
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public StockDetailsClass getStockDetailsByName(String name){
        StockDetailsClass comments = null;
        String myquery = "select * from " + StockDetailsDB.TABLE +
                " where "+ StockDetailsDB.KEY_NAME+" = '"+name+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockDetailsClass sdc = cursorTotemp(cursor);
            comments = sdc;
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }


    /*******************************************************************/
    /*******************************************************************/
    public List<StockDetailsClass> getStockDetailsByType(String type){
        List<StockDetailsClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDetailsDB.TABLE +
                " where "+ StockDetailsDB.KEY_TYPE+" = '"+type+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockDetailsClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    private StockDetailsClass cursorTotemp(Cursor cursor)	{
        StockDetailsClass temp = new StockDetailsClass(
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_STATE)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_PINCODE)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_NUMBER)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_DESC)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_TYPE)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_STATUS)),
                cursor.getString(cursor.getColumnIndex(StockDetailsDB.KEY_FLAG)));
        return temp;
    }
}
