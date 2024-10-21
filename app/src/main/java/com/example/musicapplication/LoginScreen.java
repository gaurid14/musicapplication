package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.musicapplication.homepage.HomePage;

public class LoginScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";
    public String strUsername,strEmail,strPassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        strUsername = sharedPreferences.getString(USERNAME,null);
        strPassword = sharedPreferences.getString(PASSWORD,null);

        login = findViewById(R.id.login);
        login.setOnClickListener(v ->{
            checkEditText();
        });
    }

    public void homePage(){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void checkEditText(){
        EditText username = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);

        if (TextUtils.isEmpty(username.getText().toString())){
            username.setError("Username cannot be empty");
        }

        else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Password cannot be empty");
        }

        else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERNAME,username.getText().toString());
            editor.putString(PASSWORD,password.getText().toString());
            editor.apply();
            homePage();
            finish();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(strUsername!=null && strPassword!=null){
            homePage();
        }
    }

}