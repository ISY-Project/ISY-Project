package Telnet;

public class Place {
    private String command;

    Place(String game, int start_index, int end_index, String direction) {
        this.command = "place " + start_index + " " + end_index;
    }

    Place(String game, int x, int y, Direction direction, int size) {
        int start_index = 0;
        int end_index = 0;
        if (direction == Direction.NORTH) {
            start_index = x * (y - size);
            end_index = x * y;
        } else if (direction == Direction.EAST) {
            start_index = (x - size) * y;
            end_index = x * y;
        } else if (direction == Direction.SOUTH) {
            start_index = x * y;
            end_index = x * (y + size);
        } else if (direction == Direction.WEST) {
            start_index = x * y;
            end_index = (x + size) * y;
        }
        this.command = "place " + start_index + " " + end_index;
    }

    public String get() {
        return this.command;
    }
}
