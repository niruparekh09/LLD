import java.util.ArrayList;
import java.util.List;

// ========== Iterator interface ==========
interface PlaylistIterator__ {
    boolean hasNext();

    Video__ next();
}

// ================ Playlist interface ================
// (acts as a contract for collections that are iterable)
interface Playlist__ {
    // Method to return an iterator for the collection
    PlaylistIterator__ createIterator();
}

// ========== Video class representing a single video ==========
class Video__ {
    private String title;

    public Video__(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// ========== YouTubePlaylist class (Aggregate) ==========
class YouTubePlaylist__ implements Playlist__ {
    private List<Video__> videos = new ArrayList<>();

    // Method to add video to playlist
    public void addVideo(Video__ video) {
        videos.add(video);
    }

    // Method to expose internal video list
    public List<Video__> getVideos() {
        return videos;
    }

    @Override
    public PlaylistIterator__ createIterator() {
        return new YouTubePlaylistIterator__(videos);
    }
}

// ========== Concrete Iterator class ==========
class YouTubePlaylistIterator__ implements PlaylistIterator__ {
    private List<Video__> videos;
    private int position;

    // Constructor takes the list to iterate on
    public YouTubePlaylistIterator__(List<Video__> videos) {
        this.videos = videos;
        this.position = 0;
    }

    // Check if more videos are left to iterate
    @Override
    public boolean hasNext() {
        return position < videos.size();
    }

    // Return the next video in sequence
    @Override
    public Video__ next() {
        return hasNext() ? videos.get(position++) : null;
    }
}

public class RefinedIteratorPattern {
    public static void main(String[] args) {
        // Create a playlist and add videos
        YouTubePlaylist__ playlist = new YouTubePlaylist__();
        playlist.addVideo(new Video__("LLD Tutorial"));
        playlist.addVideo(new Video__("System Design Basics"));

        PlaylistIterator__ iterator__ = playlist.createIterator();

        while (iterator__.hasNext()) {
            System.out.println(iterator__.next().getTitle());
        }
    }
}
