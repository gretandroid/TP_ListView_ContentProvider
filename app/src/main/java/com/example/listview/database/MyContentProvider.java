package com.example.listview.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {


    public static final int PERSONS = 1;
    public static final int PERSONS_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(ContractDB.AUTHORITY, ContractDB.Person.TABLE, PERSONS);
        sURIMatcher.addURI(ContractDB.AUTHORITY, ContractDB.Person.TABLE + "/#", PERSONS_ID);

    }

    private DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), ContractDB.DB_NAME, null, DBHelper.VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] arguments, @Nullable String sort) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (sURIMatcher.match(uri)){
            case PERSONS:
                return db.query(ContractDB.Person.TABLE, columns, selection, arguments, null, null, sort);
            case PERSONS_ID:
                return db.query(ContractDB.Person.TABLE, columns, ContractDB.Person.COL_ID + " = " + uri.getLastPathSegment(), arguments, null, null, sort);
            default:
                throw new RuntimeException("Failed query uri not match: " + uri);

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
        long id;
        switch(sURIMatcher.match(uri)){
            case PERSONS:
                id = db.insertOrThrow(ContractDB.Person.TABLE, null, contentValues);
                if (id == -1)
                    throw new RuntimeException("Failed insertion in: " + uri);
                else {
                    getContext().getContentResolver().notifyChange(ContractDB.Person.CONTENT_URI, null);
                    return ContentUris.withAppendedId(uri, id);
                }
            default:
                throw new RuntimeException("Failed insertion, uri not match: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch(sURIMatcher.match(uri)){
            case PERSONS:
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete(ContractDB.Person.TABLE, selection, selectionArgs);
            case PERSONS_ID:
                if (TextUtils.isEmpty(selection)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return db.delete(ContractDB.Person.TABLE,ContractDB.Person.COL_ID + "=" + uri.getLastPathSegment(),null);
                } else {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return db.delete(ContractDB.Person.TABLE,ContractDB.Person.COL_ID + "=" + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (sURIMatcher.match(uri)) {
            case PERSONS:
                return db.update(ContractDB.Person.TABLE,
                        contentValues,
                                selection,
                                selectionArgs);
            case PERSONS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    getContext().getContentResolver().notifyChange(uri,null);
                    return db.update(ContractDB.Person.TABLE, contentValues,ContractDB.Person.COL_ID + "=" + id,null);
                } else {
                    getContext().getContentResolver().notifyChange(uri,null);
                    return db.update(ContractDB.Person.TABLE, contentValues,ContractDB.Person.COL_ID + "=" + id + " and "+ selection, selectionArgs);
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
