import java.util.HashMap;
import java.util.Map;

interface VideoDownloader {
    String downloadVideo(String videoUrl);
}

// Class for first time download
class RealVideoDownloader implements VideoDownloader {
    @Override
    public String downloadVideo(String videoUrl) {
        System.out.println("Downloading video from URL: " + videoUrl);
        return "Video content from " + videoUrl;
    }
}

// Proxy class with cache support for cheap data retrieval
class CachedVideoDownloader implements VideoDownloader {

    private final RealVideoDownloader realVideoDownloader;
    private final Map<String, String> cache;

    public CachedVideoDownloader() {
        this.realVideoDownloader = new RealVideoDownloader();
        this.cache = new HashMap<>();
    }

    @Override
    public String downloadVideo(String videoUrl) {
        if (cache.containsKey(videoUrl)) {
            System.out.println("Downloading video from cache for " + videoUrl);
            return cache.get(videoUrl);
        }

        // The video is not cached
        System.out.println("The video is not cached. Downloading!!!");
        String video = realVideoDownloader.downloadVideo(videoUrl);

        // Caching the video before returning
        cache.put(videoUrl, video);

        return video;
    }
}

public class ProxyPattern {
    public static void main(String[] args) {
        VideoDownloader cacheVideoDownloader = new CachedVideoDownloader();
        System.out.println("User 1 tries to download the video.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");

        System.out.println();

        System.out.println("User 2 tries to download the same video again.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");
    }
}
