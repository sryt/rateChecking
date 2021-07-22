package com.example.grocerieslist.db.company;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 30/06/21.
 */
public class CompanyAccess {
    String TAG = CompanyAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private CompanyDB balanceHelper;
    AppGlobal global;

    public CompanyAccess(Context context)	{
        balanceHelper = new CompanyDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }


    public long addCompDetails(CompanyClass companyClass) {
        if(isExist(companyClass))
            return -1;
        ContentValues values = new ContentValues();
        values.put(CompanyDB.KEY_COMPANY_NAME,companyClass.getCompanyName());
        values.put(CompanyDB.KEY_COMPANY_DESC,companyClass.getCompanyDesc());
        values.put(CompanyDB.KEY_COMPANY_OWNER,companyClass.getCompanyOwner());
        values.put(CompanyDB.KEY_ADDRESS,companyClass.getAddress());
        values.put(CompanyDB.KEY_PLACE,companyClass.getPlace());
        values.put(CompanyDB.KEY_PINCODE,companyClass.getPinCode());
        values.put(CompanyDB.KEY_PHONENUMBER,companyClass.getPhoneNumber());
        values.put(CompanyDB.KEY_EMAILID,companyClass.getEmailId());
        values.put(CompanyDB.KEY_GSTNO,companyClass.getGstNo());
        values.put(CompanyDB.KEY_BANKNAME,companyClass.getBankName());
        values.put(CompanyDB.KEY_BANKBRANCH,companyClass.getBankBranch());
        values.put(CompanyDB.KEY_ACCNUMBER,companyClass.getAccNumber());
        values.put(CompanyDB.KEY_IFSCCODE,companyClass.getIfscCode());
        values.put(CompanyDB.KEY_FLAG,companyClass.getFlag());
        values.put(CompanyDB.KEY_STATUS,companyClass.getStatus());

        long res = database.insert(CompanyDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    public boolean isExist(CompanyClass companyClass){
        boolean res = false;
        String myquery = "select * from " + CompanyDB.TABLE +
                " where "+ CompanyDB.KEY_GSTNO+" = '"+ companyClass.getGstNo()+"'";

        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0){
            res = true;
        }
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<CompanyClass> getCompaniesDetails(){
        List<CompanyClass> comments = new ArrayList<>();
        String myquery = "select * from " + CompanyDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CompanyClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public CompanyClass getCompanyDetails(String id){
        CompanyClass comments = new CompanyClass();
        String myquery = "select * from " + CompanyDB.TABLE +" where "+
                CompanyDB.KEY_ID + " = '"+ id+"'";

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
    private CompanyClass cursorTotemp(Cursor cursor)	{
        CompanyClass temp = new CompanyClass(
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_COMPANY_NAME)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_COMPANY_OWNER)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_COMPANY_DESC)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_PLACE)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_PINCODE)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_PHONENUMBER)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_EMAILID)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_GSTNO)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_ACCNUMBER)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_BANKNAME)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_IFSCCODE)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_BANKBRANCH)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_STATUS)),
                cursor.getString(cursor.getColumnIndex(CompanyDB.KEY_FLAG)));
        return temp;
    }

}
