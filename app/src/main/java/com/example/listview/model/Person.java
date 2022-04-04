package com.example.listview.model;

import java.io.Serializable;
import java.util.Objects;

//On sérialise l'objet Personne, ça veut dire qu'il est converti en chaine pour etre envoyé à l'autre activity,
// qui ba le désérialiser pour le tranformer en objet
public class Person implements Serializable {
    private int id;
    private String name, firstName;


    //Constructor
    public Person(String name, String firstName) {

        this.name = name;
        this.firstName = firstName;
    }
    public Person(int id, String name, String firstName){
        this.id = id;
        this.name = name;
        this.firstName = firstName;
    }


    //Getter Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //toString
    @Override
    public String toString() {
        return id + " - "+ name.toUpperCase() + " " + firstName.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(firstName, person.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName);
    }
}
