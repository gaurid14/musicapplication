package com.example.musicapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicapplication.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBHandler extends SQLiteOpenHelper{
    private static final String DB_NAME = "music_application";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE = "user";
    private static final String LANGUAGE_TABLE = "language";
    private static final String USER_LANGUAGE_TABLE = "user_language";
    private static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String USER_NAME = "username";
    public static final String USER = "username";
    public static final String LANGUAGE_ID = "language_id";
    public static final String USER_LANGUAGE = "language_id";
    private static final String LANGUAGE_NAME = "language_name";
    private static final String USER_LANGUAGE_ID = "user_language_id";
    private static final String USER_SONG_DATA = "user_song_data";
    private static final String USER_SONG_ID = "user_song_id";
    private static final String SONG_NAME = "song_name";
    private static final String LISTEN_COUNT = "listen_count";
    public String savedSongs;

    public int user_exist;
    public static Map<String, List<String>> listened_songs = new HashMap<>();

//    public static final String EMAIL = "email_id";
//    public static final String PASSWORD = "password";
    public DBHandler(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "create table "+ USER_TABLE + " (" + USER_ID + " integer primary key autoincrement, " + USERNAME + " varchar(255) not null unique)";
        String languageTable = "create table "+ LANGUAGE_TABLE + " (" + LANGUAGE_ID + " integer primary key autoincrement, " + LANGUAGE_NAME + " varchar(255))";
        String userLanguageTable = "create table "+ USER_LANGUAGE_TABLE + " (" + USER_LANGUAGE_ID + " integer primary key autoincrement, username varchar(255), language_id integer, " +
                "foreign key (" + USER_NAME + ") references " + USER_TABLE + " (" + USERNAME + ") ," +
                "foreign key (" + USER_LANGUAGE + ") references " + LANGUAGE_TABLE + " (" + LANGUAGE_ID + ") on update cascade on delete cascade)";
//        String userLanguageTableConstraint = "create unique index combo_name on "+USER_LANGUAGE_TABLE + "(" + USER_ID + "," + LANGUAGE_ID + ")";
        String addLanguage = "insert into " + LANGUAGE_TABLE + " (" + LANGUAGE_NAME + ")" + " values ('English'), ('Hindi'), ('Marathi'), ('Tamil'), ('Punjabi')";
        String addUsers = "insert into " + USER_TABLE + " (" + USERNAME + ")" + " values ('gauri_d14'), ('zoyaaa'), ('nikhil_shetye'), ('bodekar_tanish')";
        String addUserLanguage = "insert into " + USER_LANGUAGE_TABLE + " (" + USERNAME + ", " + LANGUAGE_ID + ")" + " values ('gauri_d14',1),('gauri_d14',2),('gauri_d14',3),('zoyaaa',1),('zoyaaa',2),('zoyaaa',4),('nikhil_shetye',1),('nikhil_shetye',2),('bodekar_tanish',2),('bodekar_tanish',5),('bodekar_tanish',1)";
        String userSongDataTable = "create table "+ USER_SONG_DATA + " (" + USER_SONG_ID + " integer primary key autoincrement, username varchar(255), " + SONG_NAME + " varchar(255), " + LISTEN_COUNT + " integer, " + "foreign key (" + USER + ") references " + USER_TABLE + " (" + USERNAME + ") on update cascade on delete cascade)";
        String userExist = "select 1 in " + user_exist + " from " + USER_TABLE + " where " + USERNAME + " = " + MainActivity.finalUsername + ".getText().toString()";

        try {
            db.execSQL(userTable);
            db.execSQL(languageTable);
            db.execSQL(userLanguageTable);
            db.execSQL(addLanguage);
            db.execSQL(addUsers);
            db.execSQL(addUserLanguage);
            db.execSQL(userSongDataTable);
//            db.execSQL(userLanguageTableConstraint);
        } catch(Exception e){
            System.out.println(e);
        }

//        englishCSV();

    }

    public void addNewUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME,username);

        db.insert(USER_TABLE,null,values);

        db.beginTransactionNonExclusive();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void storeData(String user_name,int language_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user_name);
        values.put(USER_LANGUAGE, language_id);

        db.beginTransactionNonExclusive();
        db.insert(USER_LANGUAGE_TABLE,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void login(){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor c = db.rawQuery("select " + USERNAME + " from " + USER_TABLE,null);
            int unameIndex = c.getColumnIndex(USERNAME);
            c.moveToFirst();
            while(c!=null){
                String savedName = c.getString(unameIndex);
                System.out.println(MainActivity.finalUsername);
                if(savedName.equals(MainActivity.finalUsername)){
                    user_exist=1;
                }
                System.out.println(savedName);
                 c.moveToNext();
            }
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkLanguage(){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor c = db.rawQuery("select *" + " from " + USER_LANGUAGE_TABLE,null);
            int unameIndex = c.getColumnIndex(USER_NAME);
            int lidIndex = c.getColumnIndex(USER_LANGUAGE);
            c.moveToFirst();
            while(c!=null){
                String savedName = c.getString(unameIndex);
                int savedID = c.getInt(lidIndex);
//                System.out.println(MainActivity.finalUsername);
                System.out.println(savedName + " "+savedID);
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void userSongData(String user_name,String song_name,int listen_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user_name);
        values.put(SONG_NAME, song_name);
        values.put(LISTEN_COUNT, listen_count);

        db.beginTransactionNonExclusive();
        db.insert(USER_SONG_DATA,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Map<String, List<String>> checkUserSongData(){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor c = db.rawQuery("select *" + " from " + USER_SONG_DATA,null);
            int unameIndex = c.getColumnIndex(USER);
            int songIndex = c.getColumnIndex(SONG_NAME);
            int listenCount = c.getColumnIndex(LISTEN_COUNT);
            System.out.println("Hello");
            c.moveToFirst();
            while(c!=null){
                String userName = c.getString(unameIndex);
//                System.out.println(userName);
                String song = c.getString(songIndex);
//                System.out.println(song);
                listened_songs.put(userName, new ArrayList<String>());
                Objects.requireNonNull(listened_songs.get(userName)).add(song);
                System.out.println("Listened songs: "+ listened_songs);
                savedSongs = String.valueOf(listened_songs);

                int count = c.getInt(listenCount);
//                System.out.println(userName + " " + song + " " + count);
                c.moveToNext();
            }
            c.close();
            return listened_songs;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists "+USER_TABLE);
        db.execSQL("drop table if exists "+LANGUAGE_TABLE);
        db.execSQL("drop table if exists "+USER_LANGUAGE_TABLE);
//        db.execSQL("drop table if exists "+USER_SONG_DATA);
        onCreate(db);
    }

//    public void englishCSV(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("create table english_songs (title text, artist text, release_year integer, duration integer, genre text)");
//
//        File file = new File("Files/english.csv");
//
//        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] values = line.split(",");
//                String title = values[0];
//                String artist = values[1];
//                String release_year = values[2];
//                String duration = values[3];
//                String genre = values[4];
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("title", title);
//                contentValues.put("artist", artist);
//                contentValues.put("release_year", release_year);
//                contentValues.put("duration", duration);
//                contentValues.put("genre", genre);
//
//                db.beginTransactionNonExclusive();
//                db.insert("english_songs", null, contentValues);
//                db.setTransactionSuccessful();
//                db.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try{
//            Cursor c = db.rawQuery("select *" + " from " + "english_songs",null);
//            int titleIndex = c.getColumnIndex("title");
//            int artistIndex = c.getColumnIndex("artist");
//            int genreIndex = c.getColumnIndex("genre");
//            c.moveToFirst();
//            while(c!=null){
//                String title = c.getString(titleIndex);
//                String artist = c.getString(artistIndex);
//                String genre = c.getString(genreIndex);
////                System.out.println(MainActivity.finalUsername);
//                System.out.println("hwsuiewrfjnrejjrgk");
//                System.out.println(title + " "+artist + " " + genre);
//                c.moveToNext();
//            }
//            c.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
