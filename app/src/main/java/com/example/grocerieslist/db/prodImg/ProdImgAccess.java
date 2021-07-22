package com.example.grocerieslist.db.prodImg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.BitmapUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 09/12/20.
 */
public class ProdImgAccess {
    String TAG = ProdImgAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private ProdImgDB balanceHelper;
    AppGlobal global;

    public ProdImgAccess(Context context)	{
        balanceHelper = new ProdImgDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }

    public long addProdImgDetails(ProdImgClass prod) {
        if (isExist(prod))
            return -1;

        ContentValues values = new ContentValues();
        values.put(ProdImgDB.KEY_NAME,prod.getName());
        values.put(ProdImgDB.KEY_PHOTO, BitmapUtilities.getBytes(prod.getImg()));
        values.put(ProdImgDB.KEY_FLAG,prod.getFlag());

        long res = database.insert(ProdImgDB.TABLE, null, values);
        Log.i(TAG,"result is: "+res);
        return res;
    }

    public boolean isExist(ProdImgClass prod){
        boolean res = false;
        String myquery = "select * from " + ProdImgDB.TABLE +
                " where "+ ProdImgDB.KEY_NAME+" = '"+prod.getName()+"'";

        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0){
            res = true;
        }
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public List<ProdImgClass> getProdImgDetails(){
        List<ProdImgClass> comments = new ArrayList<>();
        String myquery = "select * from " + ProdImgDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ProdImgClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    private ProdImgClass cursorTotemp(Cursor cursor)	{
        ProdImgClass temp = new ProdImgClass(
                cursor.getString(cursor.getColumnIndex(ProdImgDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(ProdImgDB.KEY_NAME)),
                BitmapUtilities.getPhoto(cursor.getBlob(cursor.getColumnIndex(ProdImgDB.KEY_PHOTO))),
                cursor.getString(cursor.getColumnIndex(ProdImgDB.KEY_FLAG)));
        return temp;
    }

}
