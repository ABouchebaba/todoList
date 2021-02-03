package com.example.malistedetches.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoEntryDBHelper extends SQLiteOpenHelper{

    public TodoEntryDBHelper ( Context context ) {
        super ( context , TodoEntrySchema.DB_NAME , null , TodoEntrySchema.DB_VERSION ) ;
    }

    @ Override
    public void onCreate ( SQLiteDatabase db ) {
        String table = TodoEntrySchema.TodoEntry.TABLE;
        String col_todo_entry = TodoEntrySchema.TodoEntry.COL_TODOENTRY;
        String createTable =
                "CREATE TABLE "+ table
                        +" ( _id INTEGER PRIMARY KEY AUTOINCREMENT , "
                        + col_todo_entry + " TEXT NOT NULL)";
        db . execSQL ( createTable ) ;
    }

    @ Override
    public void onUpgrade ( SQLiteDatabase db , int oldVersion , int newVersion ) {
        String table = TodoEntrySchema.TodoEntry.TABLE;
        String dropTable = "DROP TABLE IF EXISTS " + table;
        db . execSQL ( dropTable ) ;
        onCreate ( db ) ;
    }
}
