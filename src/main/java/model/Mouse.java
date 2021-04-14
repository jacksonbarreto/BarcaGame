package model;

import java.util.List;

import static model.EAnimalType.*;

public class Mouse extends Piece {


    public Mouse(Color color, Location location) {
        super(color, MOUSE, LION, location);
    }

    public Mouse(Mouse mouse) {
        super(mouse.getColor(), mouse.getAnimalType(), mouse.getFear(), mouse.getLocation());
    }

    public List<Position> getLegalPositions(Board boardGame) {
        return getLinearPositions(boardGame, this);
    }

    public Mouse clone() {
        return new Mouse(this);
    }
}
