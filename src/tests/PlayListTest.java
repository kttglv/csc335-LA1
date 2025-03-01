package tests;

import backend.Album;
import backend.Song;
import backend.PlayList;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlayListTest {

	@Test
	public void testPlaylistCreation() {
	    PlayList playlist = new PlayList("My Favorites");
	    assertEquals("My Favorites", playlist.getName());
	}
	
	@Test
	public void testAddSongToPlaylist() {
	    PlayList playlist = new PlayList("My Favorites");
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    Song song = new Song("Rolling in the Deep", album);
	    playlist.addSong(song);
	    assertEquals(1, playlist.getSongs().size());
	    assertEquals("Rolling in the Deep", playlist.getSongs().get(0).getTitle());
	}
	
	@Test
	public void testRemoveSongFromPlaylist() {
	    PlayList playlist = new PlayList("My Favorites");
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    Song song1 = new Song("Rolling in the Deep", album);
	    Song song2 = new Song("Someone Like You", album);
	    playlist.addSong(song1);
	    playlist.addSong(song2);
	    playlist.removeSong("Rolling in the Deep", "Adele");
	    assertEquals(1, playlist.getSongs().size());
	    assertEquals("Someone Like You", playlist.getSongs().get(0).getTitle());
	}
	
	@Test
	public void testPlaylistImmutableSongList() {
	    PlayList playlist = new PlayList("My Favorites");
	    List<Song> songs = playlist.getSongs();
	    Album album = new Album("21", "Adele", "Pop", 2011);
	    assertThrows(UnsupportedOperationException.class, () -> {
	        songs.add(new Song("Set Fire to the Rain", album));
	    });
	}
}
