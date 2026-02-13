class BadEnemy {
    private String type;
    private int health;
    private double speed;
    private boolean armored;
    private String weapon;

    public BadEnemy(String type, int health, double speed, boolean armored, String weapon) {
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.armored = armored;
        this.weapon = weapon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void printStats() {
        System.out.println(type + " [Health: " + health +
                ", Speed: " + speed +
                ", Armored: " + armored +
                ", Weapon: " + weapon + "]");
    }
}

public class BadPrototypePattern {
    public static void main(String[] args) {

        // Manually creating flying enemies (repeating configuration)
        BadEnemy e1 = new BadEnemy("FlyingEnemy", 100, 12.0, false, "Laser");
        BadEnemy e2 = new BadEnemy("FlyingEnemy", 100, 12.0, false, "Laser");
        e2.setHealth(80); // slightly different

        // Manually creating armored enemy (again repeating setup)
        BadEnemy e3 = new BadEnemy("ArmoredEnemy", 300, 6.0, true, "Cannon");

        // Print stats
        e1.printStats();
        e2.printStats();
        e3.printStats();
    }
}
