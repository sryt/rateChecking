package com.example.grocerieslist.db.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 08/12/20.
 */
public class ProductAccess {
    String TAG = ProductAccess.class.getSimpleName();
    private SQLiteDatabase database;
    private ProductDB balanceHelper;
    AppGlobal global;

    public ProductAccess(Context context)	{
        balanceHelper = new ProductDB(context);
        global = new AppGlobal(context);
    }

    public void open() {
        database = balanceHelper.getWritableDatabase();
    }
    public void close()	{
        balanceHelper.close();
    }

    public long addProdDetails(ProductClass prod) {
        if(isExist(prod))
            return -1;
        ContentValues values = new ContentValues();
        values.put(ProductDB.KEY_NAME,prod.getName());
        values.put(ProductDB.KEY_PACK_UOM,prod.getPackuom());
        values.put(ProductDB.KEY_HSN,prod.getHsn());
        values.put(ProductDB.KEY_GST,prod.getGst());
        values.put(ProductDB.KEY_UOM,prod.getUom());
        values.put(ProductDB.KEY_PACKING_SIZE,prod.getPackingsize());
        values.put(ProductDB.KEY_CASE_QTY,prod.getCaseqty());
        values.put(ProductDB.KEY_SHEET_NO,prod.getSheetNo());
        values.put(ProductDB.KEY_MRP,prod.getMrp());
        values.put(ProductDB.KEY_COST,prod.getCost());
        values.put(ProductDB.KEY_SPECIAL,prod.getSpecial());
        values.put(ProductDB.KEY_RETAIL,prod.getRetail());
        values.put(ProductDB.KEY_TYPE,prod.getType());
        values.put(ProductDB.KEY_GROUP,prod.getGroup());
        values.put(ProductDB.KEY_TS,prod.getTs());
        values.put(ProductDB.KEY_PUR_RATE,prod.getPurRate());
        values.put(ProductDB.KEY_STATUS,prod.getStatus());

        long res = database.insert(ProductDB.TABLE, null, values);
        if(res > 0) {
            Log.i(TAG,"product classes is "+prod.toString());
            global.storeToFB(Constant.FB_Product_Path, prod);
        }
        return res;
    }

    public boolean isExist(ProductClass prod){
        boolean res = false;
        String myquery = "select * from " + ProductDB.TABLE +
                " where "+ ProductDB.KEY_NAME+" = '"+ prod.getName()+"' and "+
                ProductDB.KEY_PACKING_SIZE+" = '"+prod.getPackingsize()+"' and "+
                ProductDB.KEY_PACK_UOM+" = '"+prod.getPackuom()+"'";

        Cursor cursor = database.rawQuery(myquery,null);
        if(cursor.getCount() > 0)
            res = true;
        return res;
    }

    /*******************************************************************/
    /*******************************************************************/
    public ProductClass getProdDetails(String name,String packSize,String packUOM){
        ProductClass comments = null;
        String myquery = "select * from " + ProductDB.TABLE +
                " where "+ ProductDB.KEY_NAME+" = '"+ name+"' and "+
                ProductDB.KEY_PACKING_SIZE+" = '"+packSize+"' and "+
                ProductDB.KEY_PACK_UOM+" = '"+packUOM+"'";

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
    /*******************************************************************/
    public List<ProductClass> getProdDetails(){
        List<ProductClass> comments = new ArrayList<>();
        String myquery = "select * from " + ProductDB.TABLE ;

        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ProductClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    /*******************************************************************/
    /*******************************************************************/
    public ProductClass getProdDetails(String id){
        ProductClass comments = new ProductClass();
        String myquery = "select * from " + ProductDB.TABLE +" where "+
                ProductDB.KEY_ID + " = '"+ id+"'";

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
    /*******************************************************************/
    /*public List<ProductClass> getProdDetails(String item,String gro){
        List<ProductClass> comments = new ArrayList<>();
        String myquery = "select * from " + ProductDB.TABLE ;
        if(item.equals(Constants.GROUP)){
            myquery = myquery +" where "+
                    ProductDB.KEY_GROUP + " = '"+ gro+"'";
        }
        Cursor cursor = database.rawQuery(myquery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ProductClass name = cursorTotemp(cursor);
            comments.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }*/

    /*******************************************************************/
    private ProductClass cursorTotemp(Cursor cursor)	{
        ProductClass temp = new ProductClass(
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_TS)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_PACK_UOM)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_UOM)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_PACKING_SIZE)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_MRP)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_PUR_RATE)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_COST)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_SPECIAL)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_RETAIL)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_CASE_QTY)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_SHEET_NO)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_GST)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_HSN)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_TYPE)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_GROUP)),
                cursor.getString(cursor.getColumnIndex(ProductDB.KEY_STATUS)));
        return temp;
    }
}
