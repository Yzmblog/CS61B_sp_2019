package byow.Core;

public class Room {
    int height;
    int width;
    Position pos;

    public Room(Position pos, int height, int width) {
        this.pos = pos;
        this.height = height;
        this.width = width;
    }
}
