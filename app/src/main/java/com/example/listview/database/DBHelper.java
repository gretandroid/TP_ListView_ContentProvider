package com.example.listview.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 4;
    private static final  String CREATE_TABLE_PERSONNES = "CREATE TABLE " + ContractDB.Person.TABLE +
            " (" + ContractDB.Person.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ContractDB.Person.COL_NAME + " TEXT NOT NULL, " +
            ContractDB.Person.COL_FIRSTNAME + " TEXT NOT NULL);";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PERSONNES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContractDB.Person.TABLE);
        onCreate(sqLiteDatabase);
    }
}
