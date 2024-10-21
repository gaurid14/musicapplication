package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.database.DBHandler;
import com.example.musicapplication.homepage.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button signup,proceed_login;
    private DBHandler dbHandler;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME = "user_name";
    public static final String EMAIL = "email_id";
    public static final String PASSWORD = "password";
    SharedPreferences sharedPreferences;
    public String strUsername,strEmail,strPassword;
    public static String finalUsername = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(MainActivity.this);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        strUsername = sharedPreferences.getString(USERNAME,null);
        strEmail = sharedPreferences.getString(EMAIL,null);
        strPassword = sharedPreferences.getString(PASSWORD,null);

        signup= (Button) findViewById(R.id.signup);
        signup.setOnClickListener(v ->{
            checkEditText();
            dbHandler.login();
        });


        proceed_login= (Button) findViewById(R.id.proceed);
        proceed_login.setOnClickListener(v ->{
            loginActivity();
        });
    }

    public  void selectLanguage(){
        Intent intent = new Intent(this,LanguageSelection.class);
        startActivity(intent);
    }

    public  void loginActivity(){
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
    }

    public void homePage(){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void checkEditText(){
        EditText username = findViewById(R.id.name);
        finalUsername = username.getText().toString();
        EditText email = findViewById(R.id.emailText);
        EditText password = findViewById(R.id.passwordText);

        if (TextUtils.isEmpty(username.getText().toString())){
            username.setError("Username cannot be empty");
        }
        else if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Email cannot be empty");
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Password cannot be empty");
        }
        else if(dbHandler.user_exist == 1){
            username.setError("User already exists");
        }
        else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERNAME,username.getText().toString());
            System.out.println("Username: "+USERNAME);
            dbHandler.addNewUser(username.getText().toString());
            editor.putString(EMAIL,email.getText().toString());
            editor.putString(PASSWORD,password.getText().toString());
            editor.apply();
            editor.commit();
//            homePage();
            System.out.println("User_exist: "+dbHandler.user_exist);
            selectLanguage();
            finish();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(strUsername!=null && strEmail!=null && strPassword!=null){
//            selectLanguage();
            homePage();
        }
    }
}