package com.example.musicapplication.homepage;

public class Song {
    private int coverImageId;
    private String songName;
    private String song;

    public Song(String songName, int coverImageId, String song){
        this.coverImageId = coverImageId;
        this.songName = songName;
        this.song = song;
    }

    public int getCoverImageId(){
        return coverImageId;
    }

    public String getSongName(){
        return songName;
    }

    public String getSong(){
        return song;
    }
}
