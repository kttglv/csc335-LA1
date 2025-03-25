package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    public Iterator<Song> getShuffledIterator() {
        List<Song> shuffled = new ArrayList<>(songs);
        Collections.shuffle(shuffled);
        return shuffled.iterator();
    }

    public boolean removeSong(String title, String artist) {
        return songs.removeIf(s -> s.getTitle().equals(title) && s.getArtist().equals(artist));
    }

    public String getName() { return name; }
    public List<Song> getSongs() { return List.copyOf(songs); }
}