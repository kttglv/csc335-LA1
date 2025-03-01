package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class MusicStoreTest {
    private MusicStore store;

    @BeforeEach
    public void setUp() {
        store = new MusicStore(); // Assumes MusicStore loads data from files
    }

    @Test
    public void testMusicStoreLoading() {
        List<Song> songs = store.searchSongsByTitle("Lullaby");
        assertFalse(songs.isEmpty());
        assertEquals("Lullaby", songs.get(0).getTitle());
    }
    
    @Test
    public void testSearchSongsByTitle() {
        List<Song> songs = store.searchSongsByTitle("NonExistentSong");
        assertTrue(songs.isEmpty());
        songs = store.searchSongsByTitle("Rolling in the Deep");
        assertFalse(songs.isEmpty());
        assertEquals("Rolling in the Deep", songs.get(0).getTitle());
    }

    @Test
    public void testSearchSongsByArtist() {
        List<Song> songs = store.searchSongsByArtist("Adele");
        assertTrue(songs.size() > 1);
        assertTrue(songs.stream().allMatch(s -> s.getArtist().equals("Adele")));
    }

    @Test
    public void testSearchAlbumsByTitle() {
        List<Album> albums = store.searchAlbumsByTitle("21");
        assertFalse(albums.isEmpty());
        assertEquals("21", albums.get(0).getTitle());
    }
}