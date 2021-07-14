package com.example.babyneedsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.babyneedsapp.model.Item;
import com.example.babyneedsapp.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DataBaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_BABY_ITEM + " TEXT,"
                + Constants.KEY_COLOR + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " INTEGER,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_DATE_NAME + " LONG);";


        db.execSQL(CREATE_BABY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);


    }

    //CRUD operations


    public void additem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BABY_ITEM, item.getItemName());
        values.put(Constants.KEY_COLOR, item.getItemcolor());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemsize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

//        Log.d("additem", "additem:called ");


    }

    public Item getItem(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM, Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        }
        item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BABY_ITEM)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
        item.setItemcolor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
        item.setItemsize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                .getTime());

        item.setDateItemAdded(formattedDate);


        return item;
    }


    public List<Item> getAllItems() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM, Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE_NAME},
                null, null,
                null, null, null);

        if (cursor.moveToFirst()) {

            do {
                Item item = new Item();

                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BABY_ITEM)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
                item.setItemcolor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemsize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                        .getTime());

                item.setDateItemAdded(formattedDate);

                itemList.add(item);

            } while (cursor.moveToNext());
        }


        return itemList;
    }

    public int update(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BABY_ITEM, item.getItemName());
        values.put(Constants.KEY_COLOR, item.getItemcolor());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemsize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",
                         new String[]{String.valueOf(item.getId())});


    }

    public void  deleteItem(int id){

        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",
                  new String[]{String.valueOf(id)});

        db.close();

    }

    public  int getItemCount(){
         String countquery = "SELECT * FROM "+Constants.TABLE_NAME;
         SQLiteDatabase db=this.getReadableDatabase();

         Cursor cursor=db.rawQuery(countquery,null);
          return cursor.getCount();

    }


}




    



