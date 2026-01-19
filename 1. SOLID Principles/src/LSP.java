// Liskov Substitution Principle (LSP)
// Subtypes must be substitutable for their base types
// without breaking the expected behavior of the program.


// BAD EXAMPLE — Violates LSP
// Base class assumes all notifications can be scheduled
class BadNotification {

    public void send() {
        System.out.println("Sending notification");
    }

    public void schedule() {
        System.out.println("Scheduling notification");
    }
}

// Email supports scheduling
class EmailNotificationBad extends BadNotification {
    @Override
    public void send() {
        System.out.println("Sending Email Notification");
    }
}

// WhatsApp does NOT support scheduling
// But it is forced to implement it → LSP violation
class WhatsappNotificationBad extends BadNotification {

    @Override
    public void send() {
        System.out.println("Sending WhatsApp Notification");
    }

    @Override
    public void schedule() {
        throw new UnsupportedOperationException(
                "WhatsApp notifications cannot be scheduled");
    }
}

// Client code expecting BadNotification breaks at runtime
// when a WhatsappNotificationBad is substituted


// GOOD EXAMPLE — Follows LSP
// Base abstraction defines only common behavior
class Notification {

    public void sendNotification() {
        System.out.println("Sending Notification");
    }
}

// Subclasses can safely replace base class
class EmailNotification extends Notification {
    @Override
    public void sendNotification() {
        System.out.println("Email Notification");
    }
}

class TextNotification extends Notification {
    @Override
    public void sendNotification() {
        System.out.println("Text Notification");
    }
}

class WhatsappNotification extends Notification {
    @Override
    public void sendNotification() {
        System.out.println("WhatsApp Notification");
    }
}

// All subclasses honor the contract of Notification
// No unexpected behavior or exceptions

// Client / Demo
public class LSP {

    public static void main(String[] args) {

        // LSP-compliant usage:
        // Any subtype can replace the base type safely

        Notification notification;

        notification = new EmailNotification();
        notification.sendNotification();

        notification = new TextNotification();
        notification.sendNotification();

        notification = new WhatsappNotification();
        notification.sendNotification();

        // Behavior remains correct regardless of subtype
    }
}
