package model;

import java.util.List;

import static model.AnimalType.*;

public class Mouse extends Piece {


    public Mouse(Color color) {
        super(color, MOUSE, LION);
    }

    public Mouse(Mouse mouse) {
        super(mouse.getColor(), mouse.getAnimalType(), mouse.getFear());
    }

    public List<Position> getLegalPositions(Board board) {
        return null;
    }

    public Mouse clone() {
        return new Mouse(this);
    }
}
