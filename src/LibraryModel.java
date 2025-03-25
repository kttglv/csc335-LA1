package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryModel {
	
    private MusicStore musicStore;
    private Set<Song> individualSongs;
    private Set<Album> albums;
    private List<PlayList> playlists;
    private Map<Song, Integer> ratings;
    private Set<Song> favorites;

    public LibraryModel(MusicStore musicStore) {
    	
        this.musicStore = musicStore;
        individualSongs = new HashSet<>();
        albums = new HashSet<>();
        playlists = new ArrayList<>();
        ratings = new HashMap<>();
        favorites = new HashSet<>();
        
    }

    public boolean addSong(String title, String artist) {
    	
        List<Song> songs = musicStore.searchSongsByTitle(title);
        Song song = songs.stream().filter(s -> s.getArtist().equals(artist)).findFirst().orElse(null);
        
        if (song != null) {
            individualSongs.add(song);
            return true;
        }
        return false;
        
    }

    public boolean addAlbum(String title, String artist) {
    	
        List<Album> albumList = musicStore.searchAlbumsByTitle(title);
        Album album = albumList.stream().filter(a -> a.getArtist().equals(artist)).findFirst().orElse(null);
        
        if (album != null) {
            albums.add(album);
            return true;
        }
        return false;
    }

    public void createPlaylist(String name) {
        playlists.add(new PlayList(name));
    }

    public boolean addSongToPlaylist(String playlistName, String songTitle, String artist) {
    	
        PlayList playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        
        if (playlist == null) return false;
        
        Song song = getSongFromLibrary(songTitle, artist);
        
        if (song != null) {
            playlist.addSong(song);
            return true;
        }
        return false;
    }

    public boolean removeSongFromPlaylist(String playlistName, String songTitle, String artist) {
    	
        PlayList playlist = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
        return playlist != null && playlist.removeSong(songTitle, artist);
        
    }

    public void rateSong(String title, String artist, int rating) {
    	
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1-5");
        Song song = getSongFromLibrary(title, artist);
        
        if (song != null) {
            ratings.put(song, rating);
            if (rating == 5) favorites.add(song);
        }
    }

    public void toggleFavorite(String title, String artist) {
    	
    	
        Song song = getSongFromLibrary(title, artist);
        
        if (song != null & !favorites.contains(song)) favorites.add(song);
        
        else if (song != null & favorites.contains(song)) favorites.remove(song);
        
    }

    private Song getSongFromLibrary(String title, String artist) {
    	
        for (Song s : individualSongs) {
            if (s.getTitle().equals(title) && s.getArtist().equals(artist)) return s;
        }
        
        for (Album a : albums) {
            if (a.getArtist().equals(artist)) {
                for (Song s : a.getSongs()) {
                    if (s.getTitle().equals(title)) return s;
                }
            }
        }
        
        return null;
    }

    public List<Song> searchSongsByTitleInLibrary(String title) {
    	
        List<Song> result = new ArrayList<>();
        
        for (Song s : individualSongs) {
            if (s.getTitle().equals(title)) result.add(s);
        }
        
        for (Album a : albums) {
            for (Song s : a.getSongs()) {
                if (s.getTitle().equals(title)) result.add(s);
            }
        }
        
        return result;
    }

    public List<Song> searchSongsByArtistInLibrary(String artist) {
    	
        List<Song> result = new ArrayList<>();
        
        for (Song s : individualSongs) {
            if (s.getArtist().equals(artist)) result.add(s);
        }
        
        for (Album a : albums) {
            if (a.getArtist().equals(artist)) result.addAll(a.getSongs());
        }
        
        return result;
    }

    public List<Album> searchAlbumsByTitleInLibrary(String title) {
    	
        List<Album> result = new ArrayList<>();
        
        for (Album a : albums) {
            if (a.getTitle().equals(title)) result.add(a);
        }
        
        return result;
    }

    public List<Album> searchAlbumsByArtistInLibrary(String artist) {
    	
        List<Album> result = new ArrayList<>();
        
        for (Album a : albums) {
            if (a.getArtist().equals(artist)) result.add(a);
        }
        
        return result;
    }

    public PlayList searchPlaylistByName(String name) {
        return playlists.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    public List<String> getSongTitles() {
    	
        Set<String> titles = new HashSet<>();
        
        for (Song s : individualSongs) titles.add(s.getTitle());
        
        for (Album a : albums) {
            for (Song s : a.getSongs()) titles.add(s.getTitle());
        }
        
        return new ArrayList<>(titles);
    }

    public List<String> getArtists() {
    	
        Set<String> artists = new HashSet<>();
        
        for (Song s : individualSongs) artists.add(s.getArtist());
        
        for (Album a : albums) artists.add(a.getArtist());
        
        return new ArrayList<>(artists);
    }

    public List<String> getAlbums() {
    	
        List<String> albumTitles = new ArrayList<>();
        
        for (Album a : albums) albumTitles.add(a.getTitle());
        
        return albumTitles;
    }

    public List<String> getPlaylists() {
    	
        List<String> names = new ArrayList<>();
        
        for (PlayList p : playlists) names.add(p.getName());
        
        return names;
    }

    public List<Song> getFavorites() {
        return new ArrayList<>(favorites);
    }
}