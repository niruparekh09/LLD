import java.util.ArrayList;
import java.util.List;

// A simple Video class with title
class Video_ {
    String title;

    public Video_(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// YouTubePlaylist class holds a list of Video objects
class YouTubePlaylist_ {
    private List<Video_> videos = new ArrayList<>();

    // Add a video to the playlist
    public void addVideo(Video_ video) {
        videos.add(video);
    }

    // Expose the video list
    public List<Video_> getVideos() {
        return videos;
    }
}

public class NoIteratorPattern {
    public static void main(String[] args) {
        YouTubePlaylist_ playlist = new YouTubePlaylist_();
        playlist.addVideo(new Video_("LLD Tutorial"));
        playlist.addVideo(new Video_("System Design Basics"));

        // Loop through videos and print titles
        for (Video_ v : playlist.getVideos()) {
            System.out.println(v.getTitle());
        }
    }
}
