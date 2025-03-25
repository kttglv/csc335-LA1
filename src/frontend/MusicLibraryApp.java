package frontend;

import backend.MusicStore;
import backend.UserManager;

public class MusicLibraryApp {
    public static void main(String[] args) {
        MusicStore store = new MusicStore();
        UserManager userManager = new UserManager();
        MusicLibraryView view = new MusicLibraryView(store, userManager);
        view.start();
    }
}