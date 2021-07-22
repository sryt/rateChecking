package com.example.grocerieslist.db.sales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.db.company.CompanyDB;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class SalesAccess {
    String TAG = SalesAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private SalesDB balanceHelper;
    AppGlobal global;

    public SalesAccess(Context context)	{
        balanceHelper = new SalesDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }

    public long addSalesDetails(SalesClass salesClass) {
        ContentValues values = new ContentValues();
        values.put(SalesDB.KEY_BILL_ON,salesClass.getBillNo());
        values.put(SalesDB.KEY_CUST_NAME,salesClass.getCustName());
        values.put(SalesDB.KEY_PROD,salesClass.getProName());
        values.put(SalesDB.KEY_TS,salesClass.getTs());
        values.put(SalesDB.KEY_QTY,salesClass.getQty());
        values.put(SalesDB.KEY_RATE,salesClass.getRate());
        values.put(SalesDB.KEY_MRP,salesClass.getMrp());
        values.put(SalesDB.KEY_AMT,salesClass.getAmt());
        values.put(SalesDB.KEY_FLAG,salesClass.getFlag());

        long res = database.insert(SalesDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<SalesClass> getSalesDetails(){
        List<SalesClass> comments = new ArrayList<>();
        String myquery = "select * from " + SalesDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            SalesClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<SalesClass> getSalesDetails(String billNo){
        List<SalesClass> comments = new ArrayList<>();
        String myquery = "select * from " + SalesDB.TABLE +" where "+
                SalesDB.KEY_BILL_ON + " = '"+ billNo+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            SalesClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }


    /*******************************************************************/
    private SalesClass cursorTotemp(Cursor cursor)	{
        SalesClass temp = new SalesClass(
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_BILL_ON)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_CUST_NAME)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_TS)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_PROD)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_QTY)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_RATE)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_MRP)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_AMT)),
                cursor.getString(cursor.getColumnIndex(SalesDB.KEY_FLAG)));
        return temp;
    }

}
