package com.example.listview.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listview.model.Person;

import java.util.ArrayList;

public class DaoPerson {

    @SuppressLint("Range")
    public static ArrayList<Person> getPersonsFromDB() {
        return null;
    }


    public static void addPerson(){

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
