class YouTubeChannel_ {
    public void uploadNewVideo(String videoTitle) {
        // Upload the video
        System.out.println("Uploading: " + videoTitle + "\n");

        // Manually notify users
        System.out.println("Sending email to user1@example.com");
        System.out.println("Pushing in-app notification to user3@example.com");
    }
}

public class NoObserverPattern {
    public static void main(String[] args) {
        YouTubeChannel_ channel_ = new YouTubeChannel_();

        channel_.uploadNewVideo("Marc Marquez Wins JerezGP!!!");
    }
}
