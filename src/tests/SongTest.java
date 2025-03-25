package tests;

import backend.Album;
import backend.Song;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SongTest {
    @Test
    public void testSongCreation() {
        // Create an album to associate with the song
        Album album = new Album("Test Album", "Test Artist", "Rock", 2020);
        Song song = new Song("Test Song", album);

        // Verify that the song's properties are correctly set
        assertEquals("Test Song", song.getTitle(), "Song title should match constructor input");
        assertEquals(album, song.getAlbum(), "Song album should match the provided album");
        assertEquals("Test Artist", song.getArtist(), "Song artist should match album's artist");
    }
    
}
