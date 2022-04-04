package com.example.listview.database;

import android.net.Uri;

public class ContractDB {
    public static final String  DB_NAME = "myDatabase.db";
    public static final String AUTHORITY = "com.example.listview.database.MyContentProvider";
    public static class Person{
        public static final  String TABLE = "PERSON";
        public static final  String COL_ID = "ID";
        public static final  String COL_NAME = "NAME";
        public static final  String COL_FIRSTNAME = "FIRSTNAME";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    }


}
