package backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryModel implements Serializable {
    private transient MusicStore musicStore;
    private Set<Song> individualSongs;
    private Set<Album> albums;
    private List<PlayList> playlists;
    private Map<Song, Integer> ratings;
    private Set<Song> favorites;
    private List<Song> recentPlays;
    private Map<Song, Integer> playCounts;
    private static final int MAX_RECENT_PLAYS = 10;
    private String username;

    public LibraryModel(MusicStore musicStore) {
        this.musicStore = musicStore;
        individualSongs = new HashSet<>();
        albums = new HashSet<>();
        playlists = new ArrayList<>();
        ratings = new HashMap<>();
        favorites = new HashSet<>();
        recentPlays = new ArrayList<>();
        playCounts = new HashMap<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
        if (song != null) {
            if (favorites.contains(song)) {
                favorites.remove(song);
            } else {
                favorites.add(song);
            }
        }
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

    public boolean removeSong(String title, String artist) {
        Song song = getSongFromLibrary(title, artist);
        if (song != null && individualSongs.contains(song)) {
            individualSongs.remove(song);
            for (PlayList playlist : playlists) {
                playlist.removeSong(title, artist);
            }
            return true;
        }
        return false;
    }

    public boolean removeAlbum(String title, String artist) {
        Album album = musicStore.searchAlbumsByTitle(title).stream()
                .filter(a -> a.getArtist().equals(artist)).findFirst().orElse(null);
        if (album != null && albums.contains(album)) {
            albums.remove(album);
            for (Song song : album.getSongs()) {
                if (!isSongInOtherAlbums(song, album)) {
                    individualSongs.remove(song);
                }
            }
            for (PlayList playlist : playlists) {
                for (Song song : album.getSongs()) {
                    playlist.removeSong(song.getTitle(), song.getArtist());
                }
            }
            return true;
        }
        return false;
    }

    private boolean isSongInOtherAlbums(Song song, Album excludeAlbum) {
        for (Album a : albums) {
            if (a != excludeAlbum && a.getSongs().contains(song)) {
                return true;
            }
        }
        return false;
    }

    public Song findSongInLibrary(String title, String artist) {
        return getSongFromLibrary(title, artist);
    }

    public void playSong(Song song) {
        if (song != null && (individualSongs.contains(song) || isSongInAlbums(song))) {
            playCounts.put(song, playCounts.getOrDefault(song, 0) + 1);
            recentPlays.add(0, song);
            if (recentPlays.size() > MAX_RECENT_PLAYS) {
                recentPlays.remove(recentPlays.size() - 1);
            }
            System.out.println("Simulating play of: " + song.getTitle());
        }
    }

    private boolean isSongInAlbums(Song song) {
        for (Album a : albums) {
            if (a.getSongs().contains(song)) {
                return true;
            }
        }
        return false;
    }

    private List<Song> getAllSongs() {
        Set<Song> allSongs = new HashSet<>(individualSongs);
        for (Album a : albums) {
            allSongs.addAll(a.getSongs());
        }
        return new ArrayList<>(allSongs);
    }

    public List<Song> getSongsSorted(String criterion) {
        List<Song> songs = getAllSongs();
        switch (criterion.toLowerCase()) {
            case "title":
                songs.sort((s1, s2) -> s1.getTitle().compareTo(s2.getTitle()));
                break;
            case "artist":
                songs.sort((s1, s2) -> s1.getArtist().compareTo(s2.getArtist()));
                break;
            case "rating":
                songs.sort((s1, s2) -> Integer.compare(ratings.getOrDefault(s2, 0), ratings.getOrDefault(s1, 0)));
                break;
            default:
                throw new IllegalArgumentException("Invalid sort criterion");
        }
        return songs;
    }

    public PlayList getRecentPlays() {
        PlayList playlist = new PlayList("Recent Plays");
        for (Song song : recentPlays) {
            playlist.addSong(song);
        }
        return playlist;
    }

    public PlayList getFrequentPlays() {
        List<Map.Entry<Song, Integer>> list = new ArrayList<>(playCounts.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        PlayList playlist = new PlayList("Frequent Plays");
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            playlist.addSong(list.get(i).getKey());
        }
        return playlist;
    }

    public PlayList getFavoritesPlaylist() {
        PlayList playlist = new PlayList("Favorites");
        for (Song song : favorites) {
            playlist.addSong(song);
        }
        return playlist;
    }

    public PlayList getTopRatedPlaylist() {
        PlayList playlist = new PlayList("Top Rated");
        for (Map.Entry<Song, Integer> entry : ratings.entrySet()) {
            if (entry.getValue() >= 4) {
                playlist.addSong(entry.getKey());
            }
        }
        return playlist;
    }

    public PlayList getGenrePlaylist(String genre) {
        List<Song> genreSongs = new ArrayList<>();
        for (Song s : individualSongs) {
            if (s.getAlbum().getGenre().equalsIgnoreCase(genre)) {
                genreSongs.add(s);
            }
        }
        for (Album a : albums) {
            if (a.getGenre().equalsIgnoreCase(genre)) {
                genreSongs.addAll(a.getSongs());
            }
        }
        if (genreSongs.size() >= 10) {
            PlayList playlist = new PlayList(genre + " Playlist");
            for (Song song : genreSongs) {
                playlist.addSong(song);
            }
            return playlist;
        }
        return null;
    }

    public Iterator<Song> getShuffledSongIterator() {
        List<Song> songs = getAllSongs();
        Collections.shuffle(songs);
        return songs.iterator();
    }

    public List<Song> searchSongsByGenre(String genre) {
        List<Song> result = new ArrayList<>();
        for (Song s : individualSongs) {
            if (s.getAlbum().getGenre().equalsIgnoreCase(genre)) {
                result.add(s);
            }
        }
        for (Album a : albums) {
            if (a.getGenre().equalsIgnoreCase(genre)) {
                result.addAll(a.getSongs());
            }
        }
        return result;
    }

    public String getAlbumInfo(Song song) {
        Album album = song.getAlbum();
        StringBuilder sb = new StringBuilder();
        sb.append("Album: ").append(album.getTitle()).append(" by ").append(album.getArtist()).append("\n");
        sb.append("Genre: ").append(album.getGenre()).append(", Year: ").append(album.getYear()).append("\n");
        sb.append("Songs:\n");
        for (Song s : album.getSongs()) {
            sb.append("- ").append(s.getTitle());
            if (individualSongs.contains(s) || isSongInAlbums(s)) {
                sb.append(" (In Library)");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error saving library to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename, MusicStore musicStore) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            LibraryModel loaded = (LibraryModel) ois.readObject();
            this.individualSongs = loaded.individualSongs;
            this.albums = loaded.albums;
            this.playlists = loaded.playlists;
            this.ratings = loaded.ratings;
            this.favorites = loaded.favorites;
            this.recentPlays = loaded.recentPlays;
            this.playCounts = loaded.playCounts;
            this.username = loaded.username;
            this.musicStore = musicStore;
        } catch (IOException e) {
            System.err.println("Error loading library from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found during deserialization: " + e.getMessage());
        }
    }
}