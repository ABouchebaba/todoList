package com.example.malistedetches.db;

import android.provider.BaseColumns;

public class TodoEntrySchema {

    public static final String DB_NAME = "com.example.mytodolist.db";
    public static final int DB_VERSION = 1;

    public class TodoEntry implements BaseColumns{

        public static final String TABLE = "todo_entries";
        public static final String COL_TODOENTRY = "title";
    }
}
