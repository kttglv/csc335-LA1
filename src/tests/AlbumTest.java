package tests;

import backend.Album;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import backend.Song;

public class AlbumTest {
    @Test
    public void testAlbumCreation() {
        Album album = new Album("Test Album", "Test Artist", "Rock", 2020);

        // Verify that the album's properties are correctly set
        assertEquals("Test Album", album.getTitle(), "Album title should match constructor input");
        assertEquals("Test Artist", album.getArtist(), "Album artist should match constructor input");
        assertEquals("Rock", album.getGenre(), "Album genre should match constructor input");
        assertEquals(2020, album.getYear(), "Album year should match constructor input");
        assertTrue(album.getSongs().isEmpty(), "New album should have an empty song list");
    }

    @Test
    public void testAddSong() {
        Album album = new Album("Test Album", "Test Artist", "Rock", 2020);
        Song song = new Song("Test Song", album);
        album.addSong(song);

        // Verify that the song was added to the album
        List<Song> songs = album.getSongs();
        assertEquals(1, songs.size(), "Album should contain one song after adding");
        assertEquals(song, songs.get(0), "Added song should match the provided song");
    }
}