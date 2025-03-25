package backend;

public class Song {
    private String title;
    private Album album;

    public Song(String title, Album album) {
        this.title = title;
        this.album = album;
    }

    public String getTitle() { return title; }
    public Album getAlbum() { return album; }
    public String getArtist() { return album.getArtist(); }

    @Override
    public String toString() {
        return title + " by " + getArtist() + " from " + album.getTitle();
    }
}