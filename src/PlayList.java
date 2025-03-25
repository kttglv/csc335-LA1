package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayList {

    private String name;
    private List<Song> songs;

    public PlayList(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean removeSong(String title, String artist) {
        return songs.removeIf(s -> s.getTitle().equals(title) && s.getArtist().equals(artist));
    }

    public String getName() {
        return name;
    }

    public void shuffle() {
        Collections.shuffle(songs);
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs); // Create a new ArrayList to return a copy of the songs list
    }
}

