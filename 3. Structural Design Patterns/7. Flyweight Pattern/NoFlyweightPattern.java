import java.util.ArrayList;
import java.util.List;

// ================ Tree Class =================
class Tree_ {
    // Changing data (different for each tree) | consumes memory per object
    private int x;
    private int y;

    // Constant data (same for many trees) | duplicated unnecessarily
    private String name;
    private String color;
    private String texture;

    public Tree_(int x, int y, String name, String color, String texture) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw() {
        System.out.println("Drawing tree at (" + x + ", " + y + ") with type " + name);
    }
}

// ================ Forest Class =================
class Forest_ {

    // Holds all tree objects | memory grows linearly with number of trees
    private List<Tree_> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        // Creates new Tree_ object every time | duplicates shared attributes
        Tree_ tree = new Tree_(x, y, name, color, texture);
        trees.add(tree);
    }

    public void draw() {
        for (Tree_ tree : trees) {
            tree.draw();
        }
    }
}


public class NoFlyweightPattern {
    public static void main(String[] args) {
        Forest_ forest = new Forest_();

        // Planting 1 million trees | extreme memory usage due to duplicated attributes
        for (int i = 0; i < 1000000; i++) {
            forest.plantTree(i, i, "Oak", "Green", "Rough");
        }

        System.out.println("Planted 1 million trees.");
    }
}