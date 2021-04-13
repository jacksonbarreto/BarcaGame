package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static model.AnimalType.*;

public class Lion extends Piece {

    public Lion(Color color, Location location) {
        super(color, LION, ELEPHANT, location);
    }

    public Lion(Lion lion) {
        super(lion.getColor(), lion.getAnimalType(), lion.getFear(), lion.getLocation());
    }

    public List<Position> getLegalPositions(Board boardGame) {
        List<Position> legalPosition = getDiagonalPositions(boardGame, this);
        removeFearPositions(legalPosition, boardGame, this);

        return legalPosition;
    }


    public Lion clone() {
        return new Lion(this);
    }
}
