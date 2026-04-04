import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ============= TreeType Class ================
// Flyweight object | shared among trees of same type
class TreeType {
    // Constant attributes | stored once per type, not per tree
    private String name;
    private String color;
    private String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    // Uses shared data with unique coordinates
    public void draw(int x, int y) {
        System.out.println("Drawing " + name + " tree at (" + x + ", " + y + ")");
    }
}


// ================ Tree Class =================
class Tree {
    // Unique attributes | small memory per tree
    private int x;
    private int y;

    // Reference to shared Flyweight object | avoids duplicating common data
    private TreeType treeType;

    public Tree(int x, int y, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }

    public void draw() {
        treeType.draw(x, y); // Delegate drawing to shared TreeType
    }
}


// ============ TreeFactory Class ==============
// Ensures reuse of TreeType objects | central flyweight factory
class TreeFactory {
    static Map<String, TreeType> treeTypeMap = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture) {
        // Unique key for combination of attributes
        String key = name + " - " + color + " - " + texture;

        // Create new TreeType only if it doesn't exist | memory-efficient
        if (!treeTypeMap.containsKey(key)) {
            treeTypeMap.put(key, new TreeType(name, color, texture));
        }
        return treeTypeMap.get(key); // Return shared TreeType
    }
}


// ================ Forest Class =================
class Forest {
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        // Reuse TreeType objects via TreeFactory | reduces memory duplication
        Tree tree = new Tree(x, y, TreeFactory.getTreeType(name, color, texture));
        trees.add(tree);
    }

    public void draw() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }
}


public class FlyweightPattern {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // Planting 1 million trees | only 1 TreeType created for repeated attributes
        for(int i = 0; i < 1000000; i++) {
            forest.plantTree(i, i, "Oak", "Green", "Rough");
        }

        System.out.println("Planted 1 million trees efficiently.");
    }
}