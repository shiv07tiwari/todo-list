package com.shivansh.attendance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 10-06-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE = "attendance.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

         final String SQL_CREATE_TABLE = "CREATE TABLE "+ DataContract.SubjectData.TABLE_NAME +" ("+
                DataContract.SubjectData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DataContract.SubjectData.TASK_NAME + " TEXT NOT NULL, "+
                DataContract.SubjectData.TASK_PRIORITY + " INTEGER NOT NULL, "+
                DataContract.SubjectData.DEADLINE + " STRING NOT NULL"+");";

         sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DataContract.SubjectData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
