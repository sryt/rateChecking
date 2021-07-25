package com.example.grocerieslist.db.stock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 22/07/21.
 */
public class StockAccess {
    String TAG = StockAccess.class.getSimpleName();

    private SQLiteDatabase database;
    private StockDB balanceHelper;
    AppGlobal global;

    public StockAccess(Context context)	{
        balanceHelper = new StockDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }

    public long addProductStockDetails(StockClass prod) {
        if (isExist(prod)) {
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(StockDB.KEY_NAME,prod.getProName());
        values.put(StockDB.KEY_TS,prod.getTs());
        values.put(StockDB.KEY_CUST_ID,prod.getCustId());
        values.put(StockDB.KEY_LOCATION_ID,prod.getLocationId());
        values.put(StockDB.KEY_PERSON_ID,prod.getPersonId());
        values.put(StockDB.KEY_TYPE,prod.getType());
        values.put(StockDB.KEY_QTY,prod.getQty());
        values.put(StockDB.KEY_REMARK,prod.getRemark());

        long res = database.insert(StockDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    public boolean isExist(StockClass prod){
        boolean res = false;
        String myquery = "select * from " + StockDB.TABLE +
                " where "+ StockDB.KEY_NAME+" = '"+prod.getProName()+"' and "+ StockDB.KEY_TS+" = '"+prod.getTs()+"'";

        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0){
            res = true;
        }
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public boolean updateStock(StockClass prod){
        boolean res = false;
        String myquery = "UPDATE "+ StockDB.TABLE+" SET "+ StockDB.KEY_NAME+" = '"+prod.getProName()+"',"
                + StockDB.KEY_TS+" ='"+prod.getTs()+"',"+ StockDB.KEY_TYPE+" ='"+prod.getType()+"',"+ StockDB.KEY_CUST_ID+" ='"+prod.getCustId()+"',"
                + StockDB.KEY_REMARK+" ='"+prod.getRemark()+"',"+ StockDB.KEY_QTY+" ='"+prod.getQty()+"' WHERE "
                + StockDB.KEY_ID+" = "+Integer.parseInt(prod.getId());

        Log.i(TAG,"qry is "+myquery);
        Cursor cursor = database.rawQuery(myquery,null);
        Log.i(TAG,"Cursor count is "+cursor.getCount());
        if(cursor.getCount() == 0){
            res = true;
        }
        return res;
    }


    /*******************************************************************/
    /*******************************************************************/
    public List<StockClass> getProductStockDetails(){
        List<StockClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }


    /*******************************************************************/
    /*******************************************************************/
    public List<StockClass> getProductStock(){
        List<StockClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDB.TABLE +" order by "+ StockDB.KEY_TS+" desc";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<StockClass> getProductStockByName(String name){
        List<StockClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDB.TABLE +" where "+ StockDB.KEY_NAME+" == '"+name +"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass psc = cursorTotemp(cursor);
            comments.add(psc);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<StockClass> getProductStockByDate(String from, String to){
        List<StockClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDB.TABLE +" where "+ StockDB.KEY_TS+" >= '"+from +"' and "+ StockDB.KEY_TS+" >= '"+to +"' order by "+ StockDB.KEY_TS+" desc";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass psc = cursorTotemp(cursor);
            comments.add(psc);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<StockClass> getProductStockByCustId(String id){
        List<StockClass> comments = new ArrayList<>();
        String myquery = "select * from " + StockDB.TABLE +" where "+ StockDB.KEY_CUST_ID+" == '"+id +"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass psc = cursorTotemp(cursor);
            comments.add(psc);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }


    /*******************************************************************/
    /*******************************************************************/
    public StockClass getProductStockById(String id){
        StockClass comments = new StockClass();
        String myquery = "select * from " + StockDB.TABLE +" where "+ StockDB.KEY_ID+" == '"+id +"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StockClass psc = cursorTotemp(cursor);
            comments = psc;
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }


    /*******************************************************************/
    private StockClass cursorTotemp(Cursor cursor)	{
        StockClass temp = new StockClass(
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_CUST_ID)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_LOCATION_ID)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_PERSON_ID)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_TS)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_TYPE)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_QTY)),
                cursor.getString(cursor.getColumnIndex(StockDB.KEY_REMARK)));
        return temp;
    }

}