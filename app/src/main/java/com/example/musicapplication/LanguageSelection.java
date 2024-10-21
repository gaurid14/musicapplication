package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.example.musicapplication.database.DBHandler;
import com.example.musicapplication.homepage.HomePage;

public class LanguageSelection extends AppCompatActivity {

    private Button next,logout;
    private DBHandler dbHandler;
    private MainActivity mainActivity;
    private CheckBox english,hindi,marathi,tamil,punjabi;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME = "user_name";
    String strUsername;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        strUsername = sharedPreferences.getString(USERNAME,null);

        dbHandler = new DBHandler(LanguageSelection.this);

        next= findViewById(R.id.next);
        next.setOnClickListener(v ->{
            getLanguageSelection();
            homePage();
        });

//        logout= findViewById(R.id.logout);
//        logout.setOnClickListener(v ->{
//            logout();
//        });
    }

    public void getLanguageSelection(){
        english = findViewById(R.id.english);
        hindi = findViewById(R.id.hindi);
        marathi = findViewById(R.id.marathi);
        tamil = findViewById(R.id.tamil);
        punjabi = findViewById(R.id.punjabi);
        System.out.println(strUsername);
        if(english.isChecked()){
            dbHandler.storeData(strUsername,1);
        }
        if(hindi.isChecked()){
            dbHandler.storeData(strUsername,2 );
        }
        if(marathi.isChecked()){
            dbHandler.storeData(strUsername,3 );
        }
        if(tamil.isChecked()){
            dbHandler.storeData(strUsername,4 );
        }
        if(punjabi.isChecked()){
            dbHandler.storeData(strUsername,5 );
        }
        dbHandler.checkLanguage();
    }

    public void homePage(){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}