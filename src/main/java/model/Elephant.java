package model;

import java.util.List;

import static model.EAnimalType.*;
import static model.IPiece.*;

public class Elephant extends Piece {

    public Elephant(Color color, Location location) {
        super(color, ELEPHANT, MOUSE, location);
    }

    public Elephant(Elephant elephant) {
        super(elephant.getColor(), elephant.getAnimalType(), elephant.getFear(), elephant.getLocation());
    }

    public List<Position> getLegalPositions(Board boardGame) {
        List<Position> legalPosition = getLinearPositions(boardGame, this);
        legalPosition.addAll(getDiagonalPositions(boardGame, this));

        return legalPosition;
    }

    public Elephant clone() {
        return new Elephant(this);
    }
}
