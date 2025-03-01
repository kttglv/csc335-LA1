package frontend;

import backend.MusicStore;
import backend.LibraryModel;

public class MusicLibraryApp {
    public static void main(String[] args) {
        MusicStore store = new MusicStore();
        LibraryModel model = new LibraryModel(store);
        MusicLibraryView view = new MusicLibraryView(store, model);
        view.start();
    }
}