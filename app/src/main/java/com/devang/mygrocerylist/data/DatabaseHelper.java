package com.devang.mygrocerylist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.devang.mygrocerylist.model.Grocery;
import com.devang.mygrocerylist.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context mContext;


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
        this.mContext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE=" CREATE TABLE "+Constants.TBL_NAME +" ("
                + Constants.KEY_ID +" INTEGER PRIMARY KEY,"
                + Constants.KEY_GROCERY_ITEM + " TEXT,"
                + Constants.KEY_QUANTITY_NUMBER + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG);" ;
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+Constants.TBL_NAME);
        onCreate(sqLiteDatabase);

    }


    /*
    CRUD OPERATIONS
     */

    //Add Grocery
    public void addGrocery(Grocery grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QUANTITY_NUMBER,grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());

        db.insert(Constants.TBL_NAME,null,values);

        Log.d("MYAPP","Saved to db");
    }

    //Get Grocery of particular id

    private Grocery getGrocery(int id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(Constants.TBL_NAME, new String[]
                {
                        Constants.KEY_ID,
                        Constants.KEY_GROCERY_ITEM,
                        Constants.KEY_QUANTITY_NUMBER,
                        Constants.KEY_DATE_NAME

                }, Constants.KEY_ID + "=?", new String[]
                {
                        String.valueOf(id)
                },null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

            Grocery grocery=new Grocery();
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY_NUMBER)));
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));

            //convert timestamp to something readable
            java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
            String formatDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            grocery.setDateItemAdded(formatDate);

            return grocery;

    }
    public List<Grocery> getAllGroceries()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        List<Grocery> groceryList=new ArrayList<>();
        Cursor cursor=db.query(Constants.TBL_NAME,new String[]
                {
                        Constants.KEY_ID,
                        Constants.KEY_GROCERY_ITEM,
                        Constants.KEY_QUANTITY_NUMBER,
                        Constants.KEY_DATE_NAME

                },null,null,null,null,Constants.KEY_DATE_NAME + " DESC");
        if(cursor.moveToFirst())
        {
           do {


               Grocery grocery=new Grocery();
               grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
               grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY_NUMBER)));
               grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));

               //convert timestamp to something readable
               java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
               String formatDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
               grocery.setDateItemAdded(formatDate);

               groceryList.add(grocery);



           } while (cursor.moveToNext());
        }
        return groceryList;
    }

    public int updateGrocery(Grocery grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QUANTITY_NUMBER,grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());

    return db.update(Constants.TBL_NAME,values,Constants.KEY_ID +"=?"
        ,new String[]
                        {
                                String.valueOf(grocery.getId())

                        });
    }
    public void deleteGrocery(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TBL_NAME,Constants.KEY_ID + "=?",new String[]
                {
                        String.valueOf(id)
                });
    }
    public int getGroceryCount()
    {
        String countQuery=" SELECT * FROM "+Constants.TBL_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);

        return  cursor.getCount();
    }
}
