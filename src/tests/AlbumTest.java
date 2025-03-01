package tests;

import backend.Album;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import backend.Song;
class AlbumTest {

	@Test
	public void testAlbumCreation() {
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    assertEquals("21", album.getTitle());
	    assertEquals("Adele", album.getArtist());
	    assertEquals("Pop", album.getGenre());
	    assertEquals(2011, album.getYear());
	}
	
	@Test
	public void testAddSong() {
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    Song song = new Song("Rolling in the Deep", album);
	    album.addSong(song);
	    assertEquals(1, album.getSongs().size());
	    assertEquals("Rolling in the Deep", album.getSongs().get(0).getTitle());
	}
	
	@Test
	public void testImmutableSongList() {
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    List<Song> songs = album.getSongs();
	    assertThrows(UnsupportedOperationException.class, () -> {
	        songs.add(new Song("Set Fire to the Rain", album));
	    });
	}

}
