package com.example.listview.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteMisuseException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private DBHelper dbHelper;

    //UTILISATION D'URIMATCHER:
    //On définit tous les type de ressource auquels on veut accéder et à chacune on affecte une uri
    //list des uri:
    //uri 1: content://com.example.listview.database.MyContentProvider/PERSON
    //correspond à une collection de personne. (cette uri est stocker dans le ContractDB)
    //uri 2 : content://com.example.listview.database.MyContentProvider/PERSON/#
    //correcpond à une personne spécifique selon son id

    //On définit des constante qui seront utilisé comme code de retour par l'uri
    private static final int PERSON = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sURIMatcher.addURI(ContractDB.AUTHORITY, ContractDB.Person.TABLE_NAME, PERSON);
        sURIMatcher.addURI(ContractDB.AUTHORITY, ContractDB.Person.TABLE_NAME + "/#", PERSON_ID);
    }



    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), ContractDB.DB_NAME, null, DBHelper.VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] arguments, @Nullable String sort) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch(sURIMatcher.match(uri)){
            case PERSON:
                return  db.query(ContractDB.Person.TABLE_NAME,
                        columns,
                        selection,
                        arguments,
                        null,
                        null,
                        sort);

            case PERSON_ID:
                return  db.query(ContractDB.Person.TABLE_NAME,
                        columns,
                        ContractDB.Person.COL_ID + " = " + uri.getLastPathSegment(),
                        null,
                        null,
                        null,
                        sort);
            default:
                throw new IllegalArgumentException("failed query uri not match: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            switch (sURIMatcher.match(uri)) {
                case PERSON:
                    long id = db.insertOrThrow(ContractDB.Person.TABLE_NAME, null, contentValues);

                    return ContentUris.withAppendedId(uri, id);
                default:
                    throw new IllegalArgumentException("Failed insertion, uri not march: " + uri);
            }
        }finally {
            db.close();
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            switch(sURIMatcher.match(uri)){
                case PERSON_ID:
                    return db.delete(ContractDB.Person.TABLE_NAME, ContractDB.Person.COL_ID + " = " + uri.getLastPathSegment(), null);
                default:
                    throw new IllegalArgumentException("Failed insertion, uri not march: " + uri);
            }
        }finally {
            db.close();
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            switch(sURIMatcher.match(uri)){
                case PERSON_ID:
                    return db.update(ContractDB.Person.TABLE_NAME, contentValues, ContractDB.Person.COL_ID + " = " + uri.getLastPathSegment(), null);
                default:
                    throw new IllegalArgumentException("Failed insertion, uri not march: " + uri);
            }
        }finally {
            db.close();
        }
    }
}
