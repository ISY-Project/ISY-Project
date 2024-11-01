package src.Hanze;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    int length;
    List<Integer> placed;
    List<Integer> hits;
    List<Integer> savePlaced = new ArrayList();

    public Boat(int length, List<Integer> placed) {
        this.length = length;
        this.hits = new ArrayList();
        this.placed = placed;
        this.savePlaced.addAll(placed);
    }

    public int getLength() {
        return this.length;
    }

    public List<Integer> getPlaced() {
        return this.placed;
    }

    public List<Integer> getHits() {
        return this.hits;
    }

    public boolean hit(int index) {
        if (this.placed.contains(index)) {
            this.placed.remove(this.placed.indexOf(index));
            this.hits.add(index);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasSunk() {
        return this.placed.isEmpty();
    }
}
