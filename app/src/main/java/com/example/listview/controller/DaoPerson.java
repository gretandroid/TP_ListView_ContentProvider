package com.example.listview.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.listview.database.ContractDB;
import com.example.listview.model.Person;

import java.util.ArrayList;

public class DaoPerson {

    @SuppressLint("Range")
    public static ArrayList<Person> getPersonsFromDB(Context context) {
        ArrayList<Person> persons = new ArrayList<>();
        try(Cursor cursor = context.getContentResolver().query(ContractDB.Person.CONTENT_URI,
                null,
                null,
                null,
                null,
                null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(ContractDB.Person.COL_ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_NAME));
                    String firstName = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_FIRSTNAME));
                    persons.add(new Person(id, name, firstName));
                } while (cursor.moveToNext());
            }
        }
        return persons;
    }


    public static void addPerson(Context context, Person person){
        ContentValues value = new ContentValues();
        value.put(ContractDB.Person.COL_NAME, person.getName());
        value.put(ContractDB.Person.COL_FIRSTNAME, person.getFirstName());
        Uri retour = context.getContentResolver().insert(ContractDB.Person.CONTENT_URI, value);
        Log.d("Uri", retour.toString());
        person.setId(Integer.parseInt(retour.getLastPathSegment()));
    }

    public static void editPerson(Context context, int id, Person person){
        ContentValues values = new ContentValues();
        values.put(ContractDB.Person.COL_NAME, person.getName());
        values.put(ContractDB.Person.COL_FIRSTNAME, person.getFirstName());
        context.getContentResolver().update(ContentUris.withAppendedId(ContractDB.Person.CONTENT_URI, id),
                values,
                null,
                null);

    }
    public static void removePerson(Context context, int id){
        context.getContentResolver().delete(ContentUris.withAppendedId(ContractDB.Person.CONTENT_URI, id),
                null,
                null);
    }

    private static boolean checkEmptyValue(Person person){
        if(person.getName().isEmpty() || person.getFirstName().isEmpty())
            return  false;

        return true;
    }





}
