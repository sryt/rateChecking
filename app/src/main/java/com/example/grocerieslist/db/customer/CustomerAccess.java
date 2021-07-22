package com.example.grocerieslist.db.customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class CustomerAccess {
    String TAG = CustomerAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private CustomerDB balanceHelper;
    AppGlobal global;

    public CustomerAccess(Context context)	{
        balanceHelper = new CustomerDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }


    public long addCustomerDetails(CustomerClass prod) {
        ContentValues values = new ContentValues();
        values.put(CustomerDB.KEY_NAME,prod.getName());
        values.put(CustomerDB.KEY_ADDR,prod.getAddress());
        values.put(CustomerDB.KEY_ADD1,prod.getAddress1());
        values.put(CustomerDB.KEY_ADD2,prod.getAddress2());
        values.put(CustomerDB.KEY_PLACE,prod.getPlace());
        values.put(CustomerDB.KEY_GST,prod.getGst());
        values.put(CustomerDB.KEY_STATE,prod.getState());
        values.put(CustomerDB.KEY_SCODE,prod.getScode());
        values.put(CustomerDB.KEY_PINCODE,prod.getPincode());
        values.put(CustomerDB.KEY_PHONE,prod.getPhone1());
        values.put(CustomerDB.KEY_PHONE1,prod.getPhone2());
        values.put(CustomerDB.KEY_EMAIL,prod.getEmail());
        values.put(CustomerDB.KEY_TYPE,prod.getType());
        values.put(CustomerDB.KEY_STATUS,prod.getStatus());

        long res = database.insert(CustomerDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    public long updateCustomerDetails(CustomerClass cc){
        long res = 0;
        String myquery = "update " + CustomerDB.TABLE + " set " + CustomerDB.KEY_NAME + " = '" + cc.getName() +"',"+
                CustomerDB.KEY_ADDR + " = '" + cc.getAddress() +"',"+
                CustomerDB.KEY_ADD1 + " = '" + cc.getAddress1() +"',"+
                CustomerDB.KEY_ADD2 + " = '" + cc.getAddress2() +"',"+
                CustomerDB.KEY_PLACE + " = '" + cc.getPlace() +"',"+
                CustomerDB.KEY_GST + " = '" + cc.getGst() +"',"+
                CustomerDB.KEY_STATE + " = '" + cc.getState() +"',"+
                CustomerDB.KEY_SCODE + " = '" + cc.getScode() +"',"+
                CustomerDB.KEY_PHONE1 + " = '" + cc.getPhone2() +"',"+
                CustomerDB.KEY_PHONE + " = '" + cc.getPhone1() +"',"+
                CustomerDB.KEY_EMAIL + " = '" + cc.getEmail() +"',"+
                CustomerDB.KEY_TYPE + " = '" + cc.getType() +"',"+
                CustomerDB.KEY_PINCODE + " = '" + cc.getPincode() +"',"+
                CustomerDB.KEY_STATUS + " = '" + cc.getStatus() +"'"+
                " where " + CustomerDB.KEY_ID + " = '" + cc.getId() + "'";
        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0){
            res = cursor.getCount();
            Log.i(TAG,"update result is "+res);
        }
        return res;
    }


    /*******************************************************************/
    /*******************************************************************/
    public List<CustomerClass> getCustomerDetails(){
        List<CustomerClass> comments = new ArrayList<>();
        String myquery = "select * from " + CustomerDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CustomerClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public CustomerClass getCustomerDetailsById(String id){
        CustomerClass comments = new CustomerClass();
        String myquery = "select * from " + CustomerDB.TABLE +" where "+CustomerDB.KEY_ID+" = '"+id+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CustomerClass name = cursorTotemp(cursor);
            comments = name;
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public CustomerClass getCustomerDetailsByName(String name){
        CustomerClass comments = new CustomerClass();
        String myquery = "select * from " + CustomerDB.TABLE +" where "+CustomerDB.KEY_NAME+" = '"+name+"'";

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            comments = cursorTotemp(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    private CustomerClass cursorTotemp(Cursor cursor)	{
        CustomerClass temp = new CustomerClass(
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_ADDR)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_ADD1)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_ADD2)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_PLACE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_STATE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_SCODE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_PINCODE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_PHONE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_PHONE1)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_GST)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_TYPE)),
                cursor.getString(cursor.getColumnIndex(CustomerDB.KEY_STATUS)));
        return temp;
    }
}
