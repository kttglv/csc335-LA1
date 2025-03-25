package tests;

import backend.Album;
import backend.Song;
import backend.PlayList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PlayListTest {
    @Test
    public void testAddAndRemoveSong() {
        PlayList playlist = new PlayList("Test Playlist");
        Album album = new Album("Test Album", "Test Artist", "Rock", 2020);
        Song song1 = new Song("Song1", album);
        Song song2 = new Song("Song2", album);

        // Add songs to the playlist
        playlist.addSong(song1);
        playlist.addSong(song2);
        assertEquals(2, playlist.getSongs().size(), "Playlist should contain two songs");

        // Remove a song and verify
        assertTrue(playlist.removeSong("Song1", "Test Artist"), "Removing existing song should return true");
        assertEquals(1, playlist.getSongs().size(), "Playlist should contain one song after removal");

        // Attempt to remove a non-existent song
        assertFalse(playlist.removeSong("NonExistent", "Test Artist"), "Removing non-existent song should return false");
    }

    @Test
    public void testShuffledIterator() {
        PlayList playlist = new PlayList("Test Playlist");
        Album album = new Album("Test Album", "Test Artist", "Rock", 2020);
        Song song1 = new Song("Song1", album);
        Song song2 = new Song("Song2", album);
        playlist.addSong(song1);
        playlist.addSong(song2);

        // Get the shuffled iterator and collect songs
        Iterator<Song> iterator = playlist.getShuffledIterator();
        List<Song> shuffledSongs = new ArrayList<>();
        while (iterator.hasNext()) {
            shuffledSongs.add(iterator.next());
        }

        // Verify that all songs are present (order may vary due to shuffling)
        assertEquals(2, shuffledSongs.size(), "Shuffled iterator should return all songs");
        assertTrue(shuffledSongs.contains(song1), "Shuffled songs should include Song1");
        assertTrue(shuffledSongs.contains(song2), "Shuffled songs should include Song2");
    }
}