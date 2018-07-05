package com.example.admin.musicplayer;

/**
 * Created by Admin on 4/26/2018.
 */

public class Song {
    private String Title;
    private int File;

    public Song(String title, int file) {
        this.Title =title;
        this.File= file;
    }
    public String getTitle()
    {
        return Title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
