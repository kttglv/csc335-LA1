package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SongTest {

	@Test
	public void testSongCreation() {
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    Song song = new Song("Rolling in the Deep", album);
	    assertEquals("Rolling in the Deep", song.getTitle());
	    assertEquals("Adele", song.getArtist());
	    assertEquals(album, song.getAlbum());
	}
	
	@Test
	public void testSongToString() {
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    Song song = new Song("Rolling in the Deep", album);
	    assertEquals("Rolling in the Deep by Adele from 21", song.toString());
	}

}
