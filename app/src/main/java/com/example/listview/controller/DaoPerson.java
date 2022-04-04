package com.example.listview.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listview.database.ContractDB;
import com.example.listview.model.Person;

import java.util.ArrayList;

public class DaoPerson {

    @SuppressLint("Range")
    public static ArrayList<Person> getPersonsFromDB(Context context) {
        ArrayList<Person> persons = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContractDB.Person.CONTENT_URI, null, null, null, null, null);
        if(cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(ContractDB.Person.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_NAME));
                String firstName = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_FIRSTNAME));
                persons.add(new Person(id, name, firstName));
            } while (cursor.moveToNext());
        }
        return persons;
    }
    @SuppressLint("Range")
    public static Person getPersonFromDB(Context context, int id){
        Cursor cursor = context.getContentResolver().query(ContentUris.withAppendedId(ContractDB.Person.CONTENT_URI, id), null, null, null, null, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_NAME));
            String firstName = cursor.getString(cursor.getColumnIndex(ContractDB.Person.COL_FIRSTNAME));
            return new Person(id, name, firstName);
        }
        return null;
    }

    public static void addPerson(Context context, Person person){
        ContentValues values = new ContentValues();
        values.put(ContractDB.Person.COL_NAME, person.getName());
        values.put(ContractDB.Person.COL_FIRSTNAME, person.getFirstName());
        context.getContentResolver().insert(ContractDB.Person.CONTENT_URI, values);
    }

    public static void editPerson(Context context, Person personToUpdate, Person person){
        ContentValues value = new ContentValues();
        value.put(ContractDB.Person.COL_NAME, person.getName());
        value.put(ContractDB.Person.COL_FIRSTNAME, person.getFirstName());
        context.getContentResolver().update(ContentUris.withAppendedId(ContractDB.Person.CONTENT_URI, personToUpdate.getId()), value, null, null );

    }
    public static void removePerson(Context context, Person person){
        context.getContentResolver().delete(ContentUris.withAppendedId(ContractDB.Person.CONTENT_URI, person.getId()), null, null);
    }

    private static boolean checkEmptyValue(Person person){
        if(person.getName().isEmpty() || person.getFirstName().isEmpty())
            return  false;

        return true;
    }
    




}
