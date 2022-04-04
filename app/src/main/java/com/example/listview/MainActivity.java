package com.example.listview;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listview.controller.DaoPerson;
import com.example.listview.model.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PERSONSLIST = "personsList";

    EditText eName, eFirstName;
    TextView tSelectedPerson;
    Button btnSave;

    //on déclare un lanceur pour un appel préalablement préparé (l'intent) pour démarer le processus d'exécution d'un ActivityResultContract
    ActivityResultLauncher<Intent> intentActivityResultLauncher;
    Person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eName = findViewById(R.id.eName);
        eFirstName = findViewById(R.id.eFirstName);
        btnSave = findViewById(R.id.btnSave);
        tSelectedPerson = findViewById(R.id.tSelectedPerson);
        selectedPerson = null;


        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //on test que le résultat est OK
                if(result.getResultCode() == Activity.RESULT_OK){
                    //on recupère l'intent que l'on a crée dans listView
                    Intent data = result.getData();

                    //on récupère la person stocké
                    selectedPerson = (Person) data.getSerializableExtra(SecondActivity.PERSON);
                    Toast.makeText(getBaseContext(), "Personne sélectioné: "+selectedPerson.toString(), Toast.LENGTH_LONG).show();

                    eName.setText(selectedPerson.getName());
                    eFirstName.setText(selectedPerson.getFirstName());

                    tSelectedPerson.setText("Une personne selectionné:\n"+selectedPerson.toString());
                    //btnSave.setEnabled(false);

                }else{
                    selectedPerson = null;
                    tSelectedPerson.setText("");
                    eName.setText("");
                    eFirstName.setText("");
                    //btnSave.setEnabled(true);
                }
            }
        }
        );

    }

    public void saveOnClick(View view) {
        ArrayList<Person> _persons = DaoPerson.getPersonsFromDB(getBaseContext());

        String name = eName.getText().toString().trim();
        String firstName = eFirstName.getText().toString().trim();
        Person newPerson = new Person(name, firstName);
        DaoPerson.addPerson(this, newPerson);
        Toast.makeText(this, "Personne enregistré avec succès: " + newPerson, Toast.LENGTH_LONG).show();
    }

    public void showOnClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        //startActivity(intent);
        intentActivityResultLauncher.launch(intent);

    }

    public void editOnClick(View view) {

        if(selectedPerson == null){
            Toast.makeText(getBaseContext(), "Aucune person sélectioné", Toast.LENGTH_LONG).show();
            return;
        }
        String name = eName.getText().toString();
        String firstName = eFirstName.getText().toString();
        selectedPerson.setName(name);
        selectedPerson.setFirstName(firstName);
        DaoPerson.editPerson(this, selectedPerson.getId(), selectedPerson );

        Toast.makeText(getBaseContext(), "Personne modifié avec succès : " + selectedPerson, Toast.LENGTH_LONG).show();
        tSelectedPerson.setText("Une personne modifié:\n" + selectedPerson);

// btnSave.setEnabled(true);
    }

    public void deleteOnClick(View view) {
        if(selectedPerson == null){
            Toast.makeText(getBaseContext(), "Aucune personne sélectioné", Toast.LENGTH_LONG).show();
            return;
        }
        DaoPerson.removePerson(this, selectedPerson.getId());


        tSelectedPerson.setText("");
        eName.setText("");
        eFirstName.setText("");
        Toast.makeText(getBaseContext(), "Personne supprimé avec succès : " + selectedPerson, Toast.LENGTH_LONG).show();
        selectedPerson = null;
    }


}

///cursoradapter