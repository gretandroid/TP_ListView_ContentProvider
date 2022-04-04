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
    public static ArrayList<Person> getPersonsFromDB() {
        return null;
    }


    public static void addPerson(Context context, Person person){
        ContentValues value = new ContentValues();
        value.put(ContractDB.Person.COL_NAME, person.getName());
        value.put(ContractDB.Person.COL_FIRSTNAME, person.getFirstName());
        Uri retour = context.getContentResolver().insert(ContractDB.Person.CONTENT_URI, value);
        Log.d("Uri", retour.toString());
        person.setId(Integer.parseInt(retour.getLastPathSegment()));
    }

    public static void editPerson(){


    }
    public static void removePerson(){

    }

    private static boolean checkEmptyValue(Person person){
        if(person.getName().isEmpty() || person.getFirstName().isEmpty())
            return  false;

        return true;
    }
    




}
