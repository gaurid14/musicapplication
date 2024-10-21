package com.example.musicapplication;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapplication.database.CSVHandler;
import com.example.musicapplication.database.DBHandler;
//import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaPlayerActivity extends AppCompatActivity {

    private static int o_Time = 0, s_Time = 0, e_Time = 0;
    private Button next, previous, playPause;
    private Handler handle = new Handler();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private AudioManager audioManager;
    private AudioAttributes audioAttributes;
    private ImageView musicImage;
    private TextView songname;
    private TextView startTime;
    private TextView totaltime;
    private SeekBar progress;
    private DBHandler dbHandler;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME = "user_name";
    String strUsername;
    SharedPreferences sharedPreferences;
    AssetFileDescriptor assetFileDescriptor;
    public static int currentSong = 0;
    public static int count = 0;
    Thread updateProgress;
    static String click_song_name;
    private static List<String> recommendations = new ArrayList<>();
    private static String[] nextSongs;
    private static int num = 0;
//    private SlidingUpPanelLayout slidingLayout;
    //    private  String[] allSongs = {"ajab_si","chandra","creepin","die_for_you","jaan_nisaar","kya_mujhe_pyaar","reminder","starboy","the_hills","tum_hi_ho","yeh_fitoor_mera"};
    public static String[] allSongs;

    public MediaPlayerActivity(){

    }

//    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = focusChange -> {
//        if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
//            mediaPlayer.start();
//        }
//        else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
//            mediaPlayer.pause();
//            playPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
//            mediaPlayer.seekTo(0);
//            try {
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
//            mediaPlayer.release();
//        }
//    };

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            s_Time = mediaPlayer.getCurrentPosition();
            startTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(s_Time), TimeUnit.MILLISECONDS.toSeconds(s_Time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(s_Time))));
            progress.setProgress(s_Time);
            handle.postDelayed(this, 100);
        }
    };

    public void assignSong(int number){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            assetFileDescriptor = getAssets().openFd(("songs/"+allSongs[number]+".mp3"));

            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            songname.setText(allSongs[number].replace("_"," "));
            int images = getResources().getIdentifier(allSongs[number], "drawable", getPackageName());
            musicImage.setImageResource(images);

            dbHandler.userSongData(strUsername,allSongs[number],count = count+1);
            dbHandler.checkUserSongData();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mediaPlayer){
                    playNext();
                }
            });

//            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        strUsername = sharedPreferences.getString(USERNAME,null);
        System.out.println(strUsername);

        dbHandler = new DBHandler(MediaPlayerActivity.this);

        CSVHandler csvReader = new CSVHandler(getApplicationContext());
        allSongs = csvReader.getColumnData(1);

        Intent intent = getIntent();
        click_song_name =  intent.getStringExtra("songName");
        currentSong = Arrays.asList(allSongs).indexOf(click_song_name);

        musicImage = findViewById(R.id.songcover);
        previous = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        playPause = findViewById(R.id.playPause);
        songname = findViewById(R.id.songname);
        startTime = findViewById(R.id.time);
        totaltime = findViewById(R.id.totaltime);
        progress = findViewById(R.id.seekbar);
        progress.setClickable(true);

        thread.start();

//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//
//        audioAttributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                                .build();
//
//        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//                .setAudioAttributes(audioAttributes)
//                           .setAcceptsDelayedFocusGain(true)
//                                   .setOnAudioFocusChangeListener(audioFocusChangeListener)
//                                           .build();
//
//        final int audioFocusRequest = audioManager.requestAudioFocus(focusRequest);

//        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
////        slidingLayout.setPanelSlideListener(onSlideListener());
//        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//        slidingLayout.setTouchEnabled(true);
//        slidingLayout.setDragView(null);
//        slidingLayout.setEnabled(false);
//        slidingLayout.setClickable(false);

//        int c=0;
        assignSong(currentSong);

//        System.out.println(mediaPlayer.isPlaying());
//
//        if(c==1){
//            System.out.println(mediaPlayer.isPlaying());
//            System.out.println("Trueeee");
//            mediaPlayer.pause();
//            mediaPlayer = null;
//        }
//
//        else if(!mediaPlayer.isPlaying()){
//            System.out.println("Falseeee");
//            System.out.println(mediaPlayer.isPlaying());
//            mediaPlayer.start();
//            c=1;
//            playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
//            getTime();
//        }

        mediaPlayer.start();
        playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        getTime();


        playPause.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                playPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            }
//            else if(mediaPlayer!=null){
//                mediaPlayer.stop();
//                playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
//            }
            else if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
                playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            }

            getTime();
            playPause.setEnabled(true);
        });

        next.setOnClickListener(v -> {
            playNext();
        });

        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        updateProgress= new Thread(){
            public  void  run(){
                int currentpos = 0;
                try {
                    while (currentpos < mediaPlayer.getDuration())
                        currentpos = mediaPlayer.getDuration();
                    progress.setProgress(currentpos);
                    sleep(800);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateProgress.start();

        previous.setOnClickListener(v -> {
            if (true) {
                currentSong = (currentSong - 1);
                assignSong(currentSong);
                mediaPlayer.start();
            } else {
                Toast.makeText(getApplicationContext(), "Error in playing song", Toast.LENGTH_LONG).show();
            }
            if (!playPause.isEnabled()) {
                playPause.setEnabled(true);
            }
        });
    }

    private void playNext(){
        if (true) {
            currentSong = Arrays.asList(allSongs).indexOf(nextSongs[num]);
            assignSong(currentSong);
            mediaPlayer.start();
            num++;
        }
//            else {
//                Toast.makeText(getApplicationContext(), "Song Over", Toast.LENGTH_LONG).show();
//            }
        if (!playPause.isEnabled()) {
            playPause.setEnabled(true);
        }
       getTime();
    }

    private void getTime(){
        o_Time = 0;
        e_Time = mediaPlayer.getDuration();
        s_Time = mediaPlayer.getCurrentPosition();
        if (o_Time == 0) {
            progress.setMax(e_Time);
            o_Time = 1;
        }
        totaltime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(e_Time), TimeUnit.MILLISECONDS.toSeconds(e_Time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(e_Time))));
        startTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(s_Time), TimeUnit.MILLISECONDS.toSeconds(s_Time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(s_Time))));
        progress.setProgress(s_Time);
        handle.postDelayed(UpdateSongTime, 100);
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            getRecommendations();
        }
    });

    public static void getRecommendations() {
        try {
            System.out.println(click_song_name);
            String urlString= "http://192.168.0.104:5000/get_recommendations?song=sukh_kalale&recs=50";

//            URL url = new URL("http://httpbin.org/get?song=sukh_kalale&recs=10");

            String apiUrl = "http://192.168.90.176:5000/get_recommendations?song=songname&recs=50";
//            String currentSongName = MediaPlayerActivity.allSongs[currentSong].replace("_", " ");
            System.out.println("allSongs[currentSong]"+currentSong);
            String formattedUrl = apiUrl.replace("songname",click_song_name);
            System.out.println(formattedUrl);
            URL url = new URL(formattedUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            System.out.println(connection.getResponseCode());

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//            connection.setReadTimeout(10000);
//            connection.setConnectTimeout(10000);

//            System.out.println("b");
            connection.setRequestProperty("Accept", "application/json");
//            System.out.println("c");

            System.out.println("connection.getResponseCode()");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

//            System.out.println("d");

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.getInputStream().close();
            connection.disconnect();

            JSONObject json = new JSONObject(response.toString());
            if(json.has("recomms")) {
                JSONArray recomms = json.getJSONArray("recomms");
                for (int i = 0; i < recomms.length(); i++) {
                    recommendations.add(recomms.getString(i));
//                    System.out.println(recomms.getString(i));
//                    System.out.println("recommendations: " + recommendations);
                }
                System.out.println("Recommendations: " + recommendations);
                nextSongs = recommendations.toArray(new String[0]);
                num = 0;
                System.out.println("nextSongs: "+ Arrays.toString(nextSongs));
                recommendations.clear();
            }
            else {
                System.out.println("Invalid request");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

