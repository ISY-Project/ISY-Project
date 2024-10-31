package Telnet;

public class Place {
    private final String command;

    public Place(String game, int start_index, int end_index, String direction) {
        this.command = "place " + start_index + " " + end_index;
    }

    public Place(String game, int x, int y, Direction direction, int size) {
        int start_index = 0;
        int end_index = 0;
        switch (direction) {
            case NORTH -> {
                start_index = x * (y - size);
                end_index = x * y;
            }
            case EAST -> {
                start_index = (x - size) * y;
                end_index = x * y;
            }
            case SOUTH -> {
                start_index = x * y;
                end_index = x * (y + size);
            }
            case WEST -> {
                start_index = x * y;
                end_index = (x + size) * y;
            }
            default -> {
            }
        }
        this.command = "place " + start_index + " " + end_index;
    }

    public String get() {
        return this.command;
    }
}
