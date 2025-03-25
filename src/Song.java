package backend;

import java.util.*;

public class Song {
    private String title;
    private String artist;
    private Album album;
    private int rating;

    public Song(String title, String artist, int rating) {
        this.title = title;
        this.artist = artist;
        this.rating = rating;
    }

    public Song(String songTitle, Album album) {
    	this.title = songTitle;
        this.album = album;
	}

	public String getTitle() {
        return title;
    }
	
	public Album getAlbum() {
		return album;
	}

    public String getArtist() {
        return artist;
    }

    public int getRating() {
        return rating;
    }

    public static List<Song> sortSongs(List<Song> songs, Comparator<Song> comparator) {
        songs.sort(comparator);
        return songs;
    }
}
