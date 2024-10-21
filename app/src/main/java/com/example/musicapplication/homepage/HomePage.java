package com.example.musicapplication.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.MediaPlayerActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.database.CSVHandler;
//import com.example.musicapplication.navigation.ViewPagerAdapter;
import com.example.musicapplication.navigation.Profile;
import com.example.musicapplication.navigation.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    private Button logout;
    private ImageView cover1,cover2,cover3,cover4,cover5,cover6,cover7,cover8,cover9,cover10,cover11,cover12;
    private ImageView cover;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
//    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
    public static String songPath;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        getSupportActionBar().hide();

        CSVHandler csvReader = new CSVHandler(getApplicationContext());
        String[] columnData = csvReader.getColumnData(1);

        for(int i=0;i<columnData.length;i++){
            String [] splitWords = columnData[i].split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for(String columndata:splitWords){
                stringBuilder.append(Character.toUpperCase(columndata.charAt(0)));
                if(columndata.length()>1){
                    stringBuilder.append(columndata.substring(1));
                }
            }
            columnData[i] = stringBuilder.toString().trim();
            columnData[i] = columnData[i].replace("_"," ");
        }

//        System.out.println(Arrays.toString(columnData) +" ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, columnData);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.search);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            songPath = "songs/" + selectedItem.toLowerCase().replace(" ","_") + ".mp3";
            setMediaPlayer(selectedItem.toLowerCase().replace(" ","_"));
        });

//        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setAdapter(new SongAdapter(songs));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        viewPager2 = findViewById(R.id.viewpager2);
//        viewPagerAdapter  = new ViewPagerAdapter(this);
//        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
//                        viewPager2.setCurrentItem(0);
//                        intent = new Intent(getApplicationContext(), HomePage.class);
//                        startActivity(intent);
                        break;

                    case R.id.profile:
//                        viewPager2.setCurrentItem(1);
                        intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });

//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                switch(position)
//                {
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
//                        break;
//                }
//                super.onPageSelected(position);
//            }
//        });

        logout= findViewById(R.id.logout);
        logout.setOnClickListener(v ->{
            logout();
        });

        cover1= findViewById(R.id.cover1);
        cover1.setOnClickListener(v ->{
            setMediaPlayer("ajab_si");
        });

        cover2= findViewById(R.id.cover2);
        cover2.setOnClickListener(v ->{
            setMediaPlayer("creepin");
        });

        cover3= findViewById(R.id.cover3);
        cover3.setOnClickListener(v ->{
            setMediaPlayer("starboy");
        });

        cover4= findViewById(R.id.cover4);
        cover4.setOnClickListener(v ->{
            setMediaPlayer("jaan_nisaar");
        });

        cover5= findViewById(R.id.cover5);
        cover5.setOnClickListener(v ->{
            setMediaPlayer("kya_mujhe_pyar_hai");
        });

        cover6= findViewById(R.id.cover6);
        cover6.setOnClickListener(v ->{
            setMediaPlayer("yeh_fitoor_mera");
        });

        cover7= findViewById(R.id.cover7);
        cover7.setOnClickListener(v ->{
            setMediaPlayer("halamaithi_habibo");
        });

        cover8= findViewById(R.id.cover8);
        cover8.setOnClickListener(v ->{
            setMediaPlayer("masraaru_mastaaru");
        });

        cover9= findViewById(R.id.cover9);
        cover9.setOnClickListener(v ->{
            setMediaPlayer("one_dance");
        });

        cover10= findViewById(R.id.cover10);
        cover10.setOnClickListener(v ->{
            setMediaPlayer("vanilla");
        });

        cover11= findViewById(R.id.cover11);
        cover11.setOnClickListener(v ->{
            setMediaPlayer("chandra");
        });

        cover12= findViewById(R.id.cover12);
        cover12.setOnClickListener(v ->{
            setMediaPlayer("sukh_kalale");
        });

//        recyclerView.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
//                if (child != null) {
//                    int position = recyclerView.getChildAdapterPosition(child);
//                    Song song = songs.get(position);
//                    try {
//                        AssetFileDescriptor afd = getAssets().openFd(song.getSongName());
//                        MediaPlayer mediaPlayer = new MediaPlayer();
//                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return true;
//        });
    }

//    private void loadFragment(Fragment fragment) {
//// create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//// create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//// replace the FrameLayout with new Fragment
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit(); // save the changes
//    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setMediaPlayer(String name){
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("songName", name);
//        if(mediaPlayer!=null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//        mediaPlayer.start();
        startActivity(intent);
//        MediaPlayerActivity.playSong();
    }

}