package com.abbisqq.testingsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by babis on 2/2/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "my_database";
    public static  final String  TABLE_NAME  = "testing_tables";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "NAME";
    public static  final String COL_3 = "SURNAME";
    public static  final String COL_4 = "MARKS";



    //for simplicity reason we change the constructor to pass only context
    // factory = null
    // version is 1
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the table by executing the query
        db.execSQL("create table " + TABLE_NAME + " (" +
                COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2+ " TEXT, "+
                COL_3+ " TEXT, "+
                COL_4+ " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop the table if it already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public boolean insertData(String name,String surname,String marks){
        //how to insert data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        // if there is an error the insert method will return -1
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result ==-1){
            return false;
        }else {
            return true;
        }
    }

    //getting all the data using a cursor
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //querying to get the data
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }


    public boolean updateData(String id,String name, String surname,String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});

        return  true;


    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        // the second argument ask for the id  with ID=? and the third argument pass that id
         return db.delete(TABLE_NAME,"ID = ?",new String[] {id});

    }

}
