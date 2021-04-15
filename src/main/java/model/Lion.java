package model;

import java.util.List;

import static model.EAnimalType.*;
import static model.IPiece.getDiagonalPositions;

public class Lion extends Piece {

    public Lion(Color color, Location location) {
        super(color, LION, ELEPHANT, location);
    }

    public Lion(Lion lion) {
        super(lion.getColor(), lion.getAnimalType(), lion.getFear(), lion.getLocation());
    }

    public List<Position> getLegalPositions(Board boardGame) {
        return getDiagonalPositions(boardGame, this);
    }


    public Lion clone() {
        return new Lion(this);
    }
}
