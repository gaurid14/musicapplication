package com.example.musicapplication.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicapplication.MediaPlayerActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.database.DBHandler;
import com.example.musicapplication.homepage.HomePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Collections;
import java.util.List;

public class Profile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME = "user_name";
    public static final String EMAIL = "email_id";
    public String strUsername,strEmail;
    SharedPreferences sharedPreferences;
    private DBHandler dbHandler;
    TextView username,email;
    ListView recentSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        dbHandler = new DBHandler(Profile.this);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        strUsername = sharedPreferences.getString(USERNAME,null);
        strEmail = sharedPreferences.getString(EMAIL,null);

        username = findViewById(R.id.name_textview);
        email = findViewById(R.id.profile_email);
        recentSongs = findViewById(R.id.recent_songs);

        username.setText(strUsername);
        email.setText(strEmail);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        viewPager2 = findViewById(R.id.viewpager2);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
//                        viewPager2.setCurrentItem(0);
                        intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                        break;

                    case R.id.profile:
//                        viewPager2.setCurrentItem(1);
//                        intent = new Intent(getApplicationContext(), Profile.class);
//                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

//        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_profile, Collections.singletonList(DBHandler.listened_songs));
//        recentSongs.setAdapter(adapter);
    }
}