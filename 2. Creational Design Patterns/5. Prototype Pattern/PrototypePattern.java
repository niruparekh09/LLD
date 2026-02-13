import java.util.HashMap;
import java.util.Map;

interface EnemyPrototype {
    EnemyPrototype clone();
}

class Enemy implements EnemyPrototype {
    private String type;
    private int health;
    private double speed;
    private boolean armored;
    private String weapon;

    public Enemy(String type, int health, double speed, boolean armored, String weapon) {
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.armored = armored;
        this.weapon = weapon;
    }

    @Override
    public Enemy clone() {
        return new Enemy(type, health, speed, armored, weapon);
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

class EnemyRegistry {
    private Map<String, Enemy> prototypes = new HashMap<>();

    public void register(String key, Enemy prototype) {
        prototypes.put(key, prototype);
    }

    public Enemy get(String key) {
        Enemy prototype = prototypes.get(key);
        if (prototype != null) {
            return prototype.clone();
        }
        throw new IllegalArgumentException("No prototype registered for: " + key);
    }
}

public class PrototypePattern {
    public static void main(String[] args) {
        EnemyRegistry registry = new EnemyRegistry();

        // Register prototype enemies
        registry.register("flying", new Enemy("FlyingEnemy", 100, 12.0, false, "Laser"));
        registry.register("armored", new Enemy("ArmoredEnemy", 300, 6.0, true, "Cannon"));

        // Clone from registry
        Enemy e1 = registry.get("flying");
        Enemy e2 = registry.get("flying");
        e2.setHealth(80); // maybe this one was spawned with less HP

        Enemy e3 = registry.get("armored");

        // Print stats to verify
        e1.printStats();
        e2.printStats();
        e3.printStats();
    }
}