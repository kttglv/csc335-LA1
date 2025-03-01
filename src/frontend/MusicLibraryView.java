package frontend;

import backend.MusicStore; // To reference the MusicStore class
import backend.LibraryModel;
import backend.Song;
import backend.PlayList;
import java.util.List;
import java.util.Scanner;

public class MusicLibraryView {
	
    private MusicStore musicStore;
    private LibraryModel libraryModel;
    private Scanner scanner;

    /**
     * Constructor to initialize the view with references to the music store and library model.
     * @param musicStore The MusicStore instance to search for songs and albums.
     * @param libraryModel The LibraryModel instance to manage the user's library.
     */
    public MusicLibraryView(MusicStore musicStore, LibraryModel libraryModel) {
    	
        this.musicStore = musicStore;
        this.libraryModel = libraryModel;
        this.scanner = new Scanner(System.in);
        
    }

    /**
     * Starts the main loop of the application, displaying the menu and handling user input.
     */
    public void start() {
    	
        while (true) {
        	
            displayMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": searchMusicStore(); break;
                case "2": searchLibrary(); break;
                case "3": addToLibrary(); break;
                case "4": getListsFromLibrary(); break;
                case "5": managePlaylists(); break;
                case "6": markSongAsFavorite(); break;
                case "7": rateSong(); break;
                case "8": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMainMenu() {
        System.out.println("\nMusic Library Menu:");
        System.out.println("1. Search Music Store");
        System.out.println("2. Search Library");
        System.out.println("3. Add to Library");
        System.out.println("4. Get Lists from Library");
        System.out.println("5. Manage Playlists");
        System.out.println("6. Mark Song as Favorite");
        System.out.println("7. Rate Song");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    /**
     * Handles searching the music store for songs or albums by title or artist.
     */
    private void searchMusicStore() {
        System.out.println("Search Music Store:");
        System.out.println("a. Song by Title");
        System.out.println("b. Song by Artist");
        System.out.println("c. Album by Title");
        System.out.println("d. Album by Artist");
        System.out.print("Enter choice: ");
        
        String option = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter search term: ");
        
        String term = scanner.nextLine().trim();
        List<?> results;
        
        switch (option) {
            case "a":
                results = musicStore.searchSongsByTitle(term);
                displayResults(results, "No songs found with title: " + term);
                break;
            case "b":
                results = musicStore.searchSongsByArtist(term);
                displayResults(results, "No songs found by artist: " + term);
                break;
            case "c":
                results = musicStore.searchAlbumsByTitle(term);
                displayResults(results, "No albums found with title: " + term);
                break;
            case "d":
                results = musicStore.searchAlbumsByArtist(term);
                displayResults(results, "No albums found by artist: " + term);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Handles searching the user's library for songs, albums, or playlists.
     */
    private void searchLibrary() {
    	
        System.out.println("Search Library:");
        System.out.println("a. Song by Title");
        System.out.println("b. Song by Artist");
        System.out.println("c. Album by Title");
        System.out.println("d. Album by Artist");
        System.out.println("e. Playlist by Name");
        System.out.print("Enter choice: ");
        String option = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter search term: ");
        String term = scanner.nextLine().trim();
        List<?> results;
        
        switch (option) {
            case "a":
                results = libraryModel.searchSongsByTitleInLibrary(term);
                displayResults(results, "No songs found with title: " + term);
                break;
            case "b":
                results = libraryModel.searchSongsByArtistInLibrary(term);
                displayResults(results, "No songs found by artist: " + term);
                break;
            case "c":
                results = libraryModel.searchAlbumsByTitleInLibrary(term);
                displayResults(results, "No albums found with title: " + term);
                break;
            case "d":
                results = libraryModel.searchAlbumsByArtistInLibrary(term);
                displayResults(results, "No albums found by artist: " + term);
                break;
            case "e":
                PlayList playlist = libraryModel.searchPlaylistByName(term);
                if (playlist == null) {
                    System.out.println("No playlist found with name: " + term);
                } else {
                    System.out.println("Playlist: " + playlist.getName());
                    for (Song song : playlist.getSongs()) {
                        System.out.println(song.getTitle() + " by " + song.getArtist());
                    }
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Adds a song or album from the music store to the user's library.
     */
    private void addToLibrary() {
    	
        System.out.println("Add to Library:");
        System.out.println("1. Add Song");
        System.out.println("2. Add Album");
        
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        
        boolean success;
        
        if (choice.equals("1")) {
        	
            success = libraryModel.addSong(title, artist);
            System.out.println(success ? "Song added to library." : "Song not found in music store.");
        } else if (choice.equals("2")) {
        	
            success = libraryModel.addAlbum(title, artist);
            System.out.println(success ? "Album added to library." : "Album not found in music store.");
        } else {
        	
            System.out.println("Invalid choice.");
        }
    }

    /**
     * Retrieves and displays various lists from the user's library.
     */
    private void getListsFromLibrary() {
    	
        System.out.println("Get Lists from Library:");
        System.out.println("1. Song Titles");
        System.out.println("2. Artists");
        System.out.println("3. Albums");
        System.out.println("4. Playlists");
        System.out.println("5. Favorite Songs");
        
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        
        List<String> list;
        
        switch (choice) {
            case "1":
                list = libraryModel.getSongTitles();
                System.out.println("Song Titles:");
                for (String title : list) System.out.println(title);
                break;
            case "2":
                list = libraryModel.getArtists();
                System.out.println("Artists:");
                for (String artist : list) System.out.println(artist);
                break;
            case "3":
                list = libraryModel.getAlbums();
                System.out.println("Albums:");
                for (String album : list) System.out.println(album);
                break;
            case "4":
                list = libraryModel.getPlaylists();
                System.out.println("Playlists:");
                for (String playlist : list) System.out.println(playlist);
                break;
            case "5":
                List<Song> favorites = libraryModel.getFavorites();
                System.out.println("Favorite Songs:");
                for (Song song : favorites) System.out.println(song);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Manages playlists by creating new ones or modifying existing ones.
     */
    private void managePlaylists() {
    	
        System.out.println("Manage Playlists:");
        System.out.println("1. Create Playlist");
        System.out.println("2. Add Song to Playlist");
        System.out.println("3. Remove Song from Playlist");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
        	
            System.out.print("Enter playlist name: ");
            String name = scanner.nextLine().trim();
            libraryModel.createPlaylist(name);
            System.out.println("Playlist created.");
        } else if (choice.equals("2")) {
        	
            System.out.print("Enter playlist name: ");
            String playlistName = scanner.nextLine().trim();
            System.out.print("Enter song title: ");
            String songTitle = scanner.nextLine().trim();
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine().trim();
            boolean success = libraryModel.addSongToPlaylist(playlistName, songTitle, artist);
            System.out.println(success ? "Song added to playlist." : "Failed to add song. Playlist or song not found.");
        } else if (choice.equals("3")) {
        	
            System.out.print("Enter playlist name: ");
            String playlistName = scanner.nextLine().trim();
            System.out.print("Enter song title: ");
            String songTitle = scanner.nextLine().trim();
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine().trim();
            boolean success = libraryModel.removeSongFromPlaylist(playlistName, songTitle, artist);
            System.out.println(success ? "Song removed from playlist." : "Failed to remove song. Playlist or song not found.");
        } else {
        	
            System.out.println("Invalid choice.");
        }
    }

    /**
     * Marks a song as a favorite in the user's library.
     */
    private void markSongAsFavorite() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        
        libraryModel.toggleFavorite(title, artist);
        System.out.println("Song marked as favorite.");
    }

    /**
     * Rates a song in the user's library with a value between 1 and 5.
     */
    private void rateSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        
        System.out.print("Enter rating (1-5): ");
        String ratingStr = scanner.nextLine().trim();
        
        try {
        	
            int rating = Integer.parseInt(ratingStr);
            libraryModel.rateSong(title, artist, rating);
            System.out.println("Song rated.");
        } catch (NumberFormatException e) {
        	
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
        } catch (IllegalArgumentException e) {
        	
            System.out.println(e.getMessage());
        }
    }

    /**
     * Helper method to display search results or a message if no results are found.
     * @param results The list of results to display.
     * @param noResultsMessage The message to display if the results list is empty.
     */
    private void displayResults(List<?> results, String noResultsMessage) {
    	
        if (results.isEmpty()) {
            System.out.println(noResultsMessage);
        } else {
            for (Object item : results) {
                System.out.println(item);
            }
        }
    }
}