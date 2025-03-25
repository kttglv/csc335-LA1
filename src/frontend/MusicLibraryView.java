package frontend;

import backend.MusicStore;
import backend.LibraryModel;
import backend.Song;
import backend.PlayList;
import backend.UserManager;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MusicLibraryView {
    private MusicStore musicStore;
    private UserManager userManager;
    private LibraryModel currentLibrary;
    private Scanner scanner;

    public MusicLibraryView(MusicStore musicStore, UserManager userManager) {
        this.musicStore = musicStore;
        this.userManager = userManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String password = scanner.nextLine().trim();
                    currentLibrary = userManager.login(username, password, musicStore);
                    if (currentLibrary != null) {
                        mainMenu();
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case "2":
                    System.out.print("Username: ");
                    String newUsername = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String newPassword = scanner.nextLine().trim();
                    boolean success = userManager.createUser(newUsername, newPassword);
                    System.out.println(success ? "Account created. Please login." : "Username already exists.");
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void mainMenu() {
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": searchMusicStore(); break;
                case "2": searchLibrary(); break;
                case "3": addToLibrary(); break;
                case "4": removeFromLibrary(); break;
                case "5": playSong(); break;
                case "6": getListsFromLibrary(); break;
                case "7": managePlaylists(); break;
                case "8": markSongAsFavorite(); break;
                case "9": rateSong(); break;
                case "10": viewAutomaticPlaylists(); break;
                case "11": shuffleLibrary(); break;
                case "12": searchByGenre(); break;
                case "13": logout(); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Search Music Store");
        System.out.println("2. Search Library");
        System.out.println("3. Add to Library");
        System.out.println("4. Remove from Library");
        System.out.println("5. Play Song");
        System.out.println("6. Get Lists from Library");
        System.out.println("7. Manage Playlists");
        System.out.println("8. Mark Song as Favorite");
        System.out.println("9. Rate Song");
        System.out.println("10. View Automatic Playlists");
        System.out.println("11. Shuffle Library");
        System.out.println("12. Search by Genre");
        System.out.println("13. Logout");
        System.out.print("Enter choice: ");
    }

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
                displayResultsWithIndex(results, "No songs found with title: " + term);
                if (!results.isEmpty()) {
                    System.out.print("Enter index to view album info (or 0 to skip): ");
                    int index = Integer.parseInt(scanner.nextLine().trim());
                    if (index > 0 && index <= results.size()) {
                        Song song = (Song) results.get(index - 1);
                        String albumInfo = currentLibrary.getAlbumInfo(song);
                        System.out.println(albumInfo);
                    }
                }
                break;
            case "b":
                results = musicStore.searchSongsByArtist(term);
                displayResultsWithIndex(results, "No songs found by artist: " + term);
                if (!results.isEmpty()) {
                    System.out.print("Enter index to view album info (or 0 to skip): ");
                    int index = Integer.parseInt(scanner.nextLine().trim());
                    if (index > 0 && index <= results.size()) {
                        Song song = (Song) results.get(index - 1);
                        String albumInfo = currentLibrary.getAlbumInfo(song);
                        System.out.println(albumInfo);
                    }
                }
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
                results = currentLibrary.searchSongsByTitleInLibrary(term);
                displayResults(results, "No songs found with title: " + term);
                break;
            case "b":
                results = currentLibrary.searchSongsByArtistInLibrary(term);
                displayResults(results, "No songs found by artist: " + term);
                break;
            case "c":
                results = currentLibrary.searchAlbumsByTitleInLibrary(term);
                displayResults(results, "No albums found with title: " + term);
                break;
            case "d":
                results = currentLibrary.searchAlbumsByArtistInLibrary(term);
                displayResults(results, "No albums found by artist: " + term);
                break;
            case "e":
                PlayList playlist = currentLibrary.searchPlaylistByName(term);
                if (playlist == null) {
                    System.out.println("No playlist found with name: " + term);
                } else {
                    System.out.println("Playlist: " + playlist.getName());
                    for (Song song : playlist.getSongs()) {
                        System.out.println(song);
                    }
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

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
            success = currentLibrary.addSong(title, artist);
            System.out.println(success ? "Song added to library." : "Song not found in music store.");
        } else if (choice.equals("2")) {
            success = currentLibrary.addAlbum(title, artist);
            System.out.println(success ? "Album added to library." : "Album not found in music store.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void removeFromLibrary() {
        System.out.println("Remove from Library:");
        System.out.println("1. Remove Song");
        System.out.println("2. Remove Album");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        boolean success;
        if (choice.equals("1")) {
            success = currentLibrary.removeSong(title, artist);
            System.out.println(success ? "Song removed." : "Song not found.");
        } else if (choice.equals("2")) {
            success = currentLibrary.removeAlbum(title, artist);
            System.out.println(success ? "Album removed." : "Album not found.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void playSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        Song song = currentLibrary.findSongInLibrary(title, artist);
        if (song != null) {
            currentLibrary.playSong(song);
            System.out.println("Playing: " + song);
        } else {
            System.out.println("Song not found in library.");
        }
    }

    private void getListsFromLibrary() {
        System.out.println("Get Lists from Library:");
        System.out.println("1. Songs");
        System.out.println("2. Artists");
        System.out.println("3. Albums");
        System.out.println("4. Playlists");
        System.out.println("5. Favorite Songs");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                System.out.println("Sort by: 1. Title  2. Artist  3. Rating");
                String sortChoice = scanner.nextLine().trim();
                String criterion;
                switch (sortChoice) {
                    case "1": criterion = "title"; break;
                    case "2": criterion = "artist"; break;
                    case "3": criterion = "rating"; break;
                    default: System.out.println("Invalid sort choice."); return;
                }
                List<Song> songs = currentLibrary.getSongsSorted(criterion);
                displayResults(songs, "No songs in library.");
                break;
            case "2":
                List<String> artists = currentLibrary.getArtists();
                displayResults(artists, "No artists in library.");
                break;
            case "3":
                List<String> albums = currentLibrary.getAlbums();
                displayResults(albums, "No albums in library.");
                break;
            case "4":
                List<String> playlists = currentLibrary.getPlaylists();
                displayResults(playlists, "No playlists in library.");
                break;
            case "5":
                List<Song> favorites = currentLibrary.getFavorites();
                displayResults(favorites, "No favorite songs.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void managePlaylists() {
        System.out.println("Manage Playlists:");
        System.out.println("1. Create Playlist");
        System.out.println("2. Add Song to Playlist");
        System.out.println("3. Remove Song from Playlist");
        System.out.println("4. Shuffle Playlist");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            System.out.print("Enter playlist name: ");
            String name = scanner.nextLine().trim();
            currentLibrary.createPlaylist(name);
            System.out.println("Playlist created.");
        } else if (choice.equals("2")) {
            System.out.print("Enter playlist name: ");
            String playlistName = scanner.nextLine().trim();
            System.out.print("Enter song title: ");
            String songTitle = scanner.nextLine().trim();
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine().trim();
            boolean success = currentLibrary.addSongToPlaylist(playlistName, songTitle, artist);
            System.out.println(success ? "Song added to playlist." : "Failed to add song.");
        } else if (choice.equals("3")) {
            System.out.print("Enter playlist name: ");
            String playlistName = scanner.nextLine().trim();
            System.out.print("Enter song title: ");
            String songTitle = scanner.nextLine().trim();
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine().trim();
            boolean success = currentLibrary.removeSongFromPlaylist(playlistName, songTitle, artist);
            System.out.println(success ? "Song removed from playlist." : "Failed to remove song.");
        } else if (choice.equals("4")) {
            System.out.print("Enter playlist name: ");
            String name = scanner.nextLine().trim();
            PlayList playlist = currentLibrary.searchPlaylistByName(name);
            if (playlist != null) {
                Iterator<Song> iterator = playlist.getShuffledIterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            } else {
                System.out.println("Playlist not found.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void markSongAsFavorite() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        currentLibrary.toggleFavorite(title, artist);
        System.out.println("Favorite status toggled.");
    }

    private void rateSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        System.out.print("Enter rating (1-5): ");
        String ratingStr = scanner.nextLine().trim();
        try {
            int rating = Integer.parseInt(ratingStr);
            currentLibrary.rateSong(title, artist, rating);
            System.out.println("Song rated.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating. Enter a number between 1 and 5.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewAutomaticPlaylists() {
        System.out.println("Automatic Playlists:");
        System.out.println("1. Recent Plays");
        System.out.println("2. Frequent Plays");
        System.out.println("3. Favorites");
        System.out.println("4. Top Rated");
        System.out.println("5. Genre Playlist");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        PlayList playlist = null;
        switch (choice) {
            case "1": playlist = currentLibrary.getRecentPlays(); break;
            case "2": playlist = currentLibrary.getFrequentPlays(); break;
            case "3": playlist = currentLibrary.getFavoritesPlaylist(); break;
            case "4": playlist = currentLibrary.getTopRatedPlaylist(); break;
            case "5":
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine().trim();
                playlist = currentLibrary.getGenrePlaylist(genre);
                break;
            default: System.out.println("Invalid choice."); return;
        }
        if (playlist != null) {
            System.out.println("Playlist: " + playlist.getName());
            for (Song song : playlist.getSongs()) {
                System.out.println(song);
            }
        } else if (choice.equals("5")) {
            System.out.println("No playlist for that genre.");
        }
    }

    private void shuffleLibrary() {
        System.out.println("Shuffling library...");
        Iterator<Song> iterator = currentLibrary.getShuffledSongIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private void searchByGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();
        List<Song> songs = currentLibrary.searchSongsByGenre(genre);
        displayResults(songs, "No songs found for genre: " + genre);
    }

    private void logout() {
        userManager.saveCurrentLibrary();
        currentLibrary = null;
        System.out.println("Logged out.");
    }

    private void displayResultsWithIndex(List<?> results, String noResultsMessage) {
        if (results.isEmpty()) {
            System.out.println(noResultsMessage);
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

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