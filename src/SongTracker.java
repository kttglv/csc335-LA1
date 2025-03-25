package backend;

import java.util.*;

public class SongTracker {
    private LinkedList<String> recentlyPlayed = new LinkedList<>();
    private Map<String, Integer> songPlayCount = new HashMap<>();
    private static final int MAX_RECENTLY_PLAYED = 10;

    public void addToRecentlyPlayed(String song) {
        if (recentlyPlayed.contains(song)) {
            recentlyPlayed.remove(song);
        }
        recentlyPlayed.addFirst(song);
        if (recentlyPlayed.size() > MAX_RECENTLY_PLAYED) {
            recentlyPlayed.removeLast();
        }
    }

    public void trackSongPlay(String song) {
        songPlayCount.put(song, songPlayCount.getOrDefault(song, 0) + 1);
    }

    public List<String> getRecentlyPlayed() {
        return new ArrayList<>(recentlyPlayed);
    }

    public Map<String, Integer> getSongPlayCount() {
        return songPlayCount;
    }
}
