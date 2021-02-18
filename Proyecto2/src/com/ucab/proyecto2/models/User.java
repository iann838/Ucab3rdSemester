package com.ucab.proyecto2.models;

import java.io.IOException;

import com.ucab.proyecto2.django.Manager;
import com.ucab.proyecto2.django.Model;
import com.ucab.proyecto2.django.Validator.ValidationError;

public class User extends Model {

    public String alias;
    public String email;
    public int pointRecord;
    public int assertRecord;
    public String savedGame;
    public boolean hasUnfinished;

    public static transient Manager<User> objects = new Manager<User>("db/users", User.class);
    @SuppressWarnings("unchecked") public Manager<User> getObjects() { return objects; }

    public User() {}
    public User(int id, String alias, String email, int pointRecord, int assertRecord, String savedGame, boolean hasUnfinished) {
        this.id = id;
        this.alias = alias;
        this.email = email;
        this.pointRecord = pointRecord;
        this.assertRecord = assertRecord;
        this.savedGame = savedGame;
        this.hasUnfinished = hasUnfinished;
    }

    public void clean() throws ValidationError {
        getObjects().validator.email(this.email);
        try {
            getObjects().validator.unique(this, "email", this.email);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationError("Could not connect to db");
        }
    }

}
