package tests;

import backend.MusicStore;
import backend.LibraryModel;
import backend.Song;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryModelTest {
	private MusicStore store;
	private LibraryModel model;

	@BeforeEach
    public void setUp() {
		store = new MusicStore(); // Assumes MusicStore loads data from files
        model = new LibraryModel(store);

    }
	
	@Test
	public void testAddSongToLibrary() {
	    model.addSong("Rolling in the Deep", "Adele");
	    List<Song> songs = model.searchSongsByTitleInLibrary("Rolling in the Deep");
	    assertEquals(1, songs.size());
	    assertEquals("Adele", songs.get(0).getArtist());
	}
	
	@Test
	public void testAddAlbumToLibrary() {
	    model.addAlbum("21", "Adele");
	    List<Song> songs = model.searchSongsByTitleInLibrary("Rolling in the Deep");
	    assertFalse(songs.isEmpty());
	}
	
	@Test
	public void testPlaylistManagement() {
	    model.addSong("Rolling in the Deep", "Adele");
	    model.toggleFavorite("Rolling in the Deep", "Adele");
	    assertEquals(1, model.getFavorites().size());
	    model.toggleFavorite("Rolling in the Deep", "Adele");
	    assertEquals(0, model.getFavorites().size());
	}
	
	@Test
	public void testAddToFavorites() {
	    model.addSong("Rolling in the Deep", "Adele");
	    model.toggleFavorite("Rolling in the Deep", "Adele");
	    List<Song> favorites = model.getFavorites();
	    assertEquals(1, favorites.size());
	    assertEquals("Rolling in the Deep", favorites.get(0).getTitle());
	}
	
	@Test
	public void testRateSong() {
	    model.addSong("Rolling in the Deep", "Adele");
	    model.rateSong("Rolling in the Deep", "Adele", 5);
	    List<Song> favorites = model.getFavorites();
	    assertTrue(favorites.stream().anyMatch(s -> s.getTitle().equals("Rolling in the Deep")));
	}
	

}
