package model.factories;

import model.*;

public class PieceFactory {


    public static Piece createLion(Color color) {
        return new Lion(color);
    }

    public static Piece createElephant(Color color) {
        return new Elephant(color);
    }

    public static Piece createMouse(Color color) {
        return new Mouse(color);
    }

}
