import java.util.ArrayList;
import java.util.List;

// ==============================
// Observer Interface
// ==============================
interface Subscriber {
    void update(String videoTitle);
}

// ==============================
// Subject Interface
// ==============================
interface Channel {
    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifySubscribers(String videoTitle);
}

// Concrete Observer: Email
class EmailSubscriber implements Subscriber {
    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Email sent to: " + email + " About: \"" + videoTitle+"\"");
    }
}

// Concrete Observer: Mobile
class MobileSubscriber implements Subscriber {
    private String username;

    public MobileSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("In App Notification sent to: " + username + " About: \"" + videoTitle+"\"");
    }
}

// Concrete Subject Class: YouTube Channel
class YoutubeChannel implements Channel {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String channelName;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String videoTitle) {
        for (Subscriber sub : subscribers) {
            sub.update(videoTitle);
        }
    }

    // Simulates video upload and triggers notifications
    public void uploadVideo(String videoTitle) {
        System.out.println("Video: \"" + videoTitle + "\" uploaded to channel: " + channelName);
        notifySubscribers(videoTitle);
    }
}


public class ObserverPattern {
    public static void main(String[] args) {
        YoutubeChannel nrv = new YoutubeChannel("NP19");

        nrv.subscribe(new EmailSubscriber("nrv19@test.com"));
        nrv.subscribe(new MobileSubscriber("nrv19"));

        nrv.uploadVideo("Nirav Wins JerezGP");
    }
}
