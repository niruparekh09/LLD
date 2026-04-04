class RealVideoDownloader_ {
    public String downloadVideo(String videoUrl) {
        // caching logic missing
        // filtering logic missing
        // access logic missing
        System.out.println("Downloading video from URL: " + videoUrl);
        String content = "Video content from " + videoUrl;
        System.out.println("Downloaded Content: " + content);
        return content;
    }
}

public class NoProxyPattern {
    public static void main(String[] args) {
        System.out.println("User 1 tries to download the video.");
        RealVideoDownloader_ downloader1 = new RealVideoDownloader_();
        String content1 = downloader1.downloadVideo("https://video.com/proxy-pattern");
        System.out.println(content1);

        System.out.println();

        System.out.println("User 2 tries to download the same video again.");
        RealVideoDownloader_ downloader2 = new RealVideoDownloader_();
        String content2 = downloader2.downloadVideo("https://video.com/proxy-pattern");
        System.out.println(content2);
    }
}
