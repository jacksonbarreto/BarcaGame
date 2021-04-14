package model.factories;

import model.*;

public class PieceFactory {


    public static Piece createLion(Color color, Location location) {
        return new Lion(color, location);
    }

    public static Piece createElephant(Color color, Location location) {
        return new Elephant(color, location);
    }

    public static Piece createMouse(Color color, Location location) {
        return new Mouse(color, location);
    }

}
