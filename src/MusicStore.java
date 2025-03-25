package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicStore {
	
    private Map<String, List<Album>> albumsByTitle;
    private Map<String, List<Album>> albumsByArtist;
    private Map<String, List<Song>> songsByTitle;
    private Map<String, List<Song>> songsByArtist;

    public MusicStore() {
    	
        albumsByTitle = new HashMap<>();
        albumsByArtist = new HashMap<>();
        songsByTitle = new HashMap<>();
        songsByArtist = new HashMap<>();
        loadData();
        
    }

    private void loadData() {
    	
        try (BufferedReader reader = new BufferedReader(new FileReader("albums.txt"))) {
        	
            String line;
            
            while ((line = reader.readLine()) != null) {
            	
                String[] parts = line.split(",\\s*");
                
                if (parts.length == 2) {
                	
                    String title = parts[0].trim();
                    String artist = parts[1].trim();
                    String filename = title + "_" + artist + ".txt";
                    
                    loadAlbum(filename, title, artist);
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading albums.txt: " + e.getMessage());
        }
    }

    private void loadAlbum(String filename, String title, String artist) {
    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        	
            String firstLine = reader.readLine();
            
            if (firstLine != null) {
            	
                String[] parts = firstLine.split(",\\s*");
                
                if (parts.length == 4) {
                	
                    String albumTitle = parts[0].trim();
                    String albumArtist = parts[1].trim();
                    String genre = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    
                    Album album = new Album(albumTitle, albumArtist, genre, year);

                    albumsByTitle.computeIfAbsent(albumTitle, k -> new ArrayList<>()).add(album);
                    albumsByArtist.computeIfAbsent(albumArtist, k -> new ArrayList<>()).add(album);

                    String songLine;
                    
                    while ((songLine = reader.readLine()) != null) {
                    	
                        String songTitle = songLine.trim();
                        
                        if (!songTitle.isEmpty()) {
                        	
                            Song song = new Song(songTitle, album);
                            album.addSong(song);
                            
                            songsByTitle.computeIfAbsent(songTitle, k -> new ArrayList<>()).add(song);
                            songsByArtist.computeIfAbsent(albumArtist, k -> new ArrayList<>()).add(song);
                            
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + filename + ": " + e.getMessage());
        }
    }

    public List<Song> searchSongsByTitle(String title) {
        return songsByTitle.getOrDefault(title, Collections.emptyList());
    }

    public List<Song> searchSongsByArtist(String artist) {
        return songsByArtist.getOrDefault(artist, Collections.emptyList());
    }

    public List<Album> searchAlbumsByTitle(String title) {
        return albumsByTitle.getOrDefault(title, Collections.emptyList());
    }

    public List<Album> searchAlbumsByArtist(String artist) {
        return albumsByArtist.getOrDefault(artist, Collections.emptyList());
    }
}