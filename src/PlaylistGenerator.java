package backend;

import java.util.ArrayList;
import java.util.List;

public class PlaylistGenerator {
    private SongTracker songTracker;

    public PlaylistGenerator(SongTracker songTracker) {
        this.songTracker = songTracker;
    }

    public List<String> generateFavoriteSongsPlaylist() {
        List<String> favoriteSongs = new ArrayList<>();
        for (String song : songTracker.getSongPlayCount().keySet()) {
            if (songTracker.getSongPlayCount().get(song) == 5) { // Example of favorite song criteria
                favoriteSongs.add(song);
            }
        }
        return favoriteSongs;
    }

    public List<String> generateTopRatedPlaylist() {
        // Placeholder, assuming songs have a rating value
        return new ArrayList<>(songTracker.getSongPlayCount().keySet());
    }
}
