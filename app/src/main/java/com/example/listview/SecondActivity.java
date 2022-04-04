package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.listview.controller.DaoPerson;
import com.example.listview.model.Person;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView listView;

    //on définit un ArrayAdapter pour adapter la lise des personne au listView
    ArrayAdapter<Person> personArrayAdapter;
    public static final String PERSON = "person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        ArrayList<Person> personsList = (ArrayList<Person>) intent.getSerializableExtra(MainActivity.PERSONSLIST);

        //On instancie l'arrayAdapter
        ArrayList<Person> _persons = DaoPerson.getPersonsFromDB(this);
        personArrayAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, _persons);
        listView.setAdapter(personArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //On récupère la personne sur laquelle on a cliqué
                Person person = _persons.get(i);

                //on crée un intent
                Intent intentOut = new Intent();

                intentOut.putExtra(PERSON, person);

                //On renvoi un code à la main activity pour lui indiquer que tous s'est bien passé
                setResult(RESULT_OK, intentOut);

                //on ferme l'activity courente
                finish();



            }
        });
    }

    public void returnOnClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}