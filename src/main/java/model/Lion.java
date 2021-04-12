package model;

import java.util.List;

import static model.AnimalType.*;

public class Lion extends Piece {

    public Lion(Color color) {
        super(color, LION, ELEPHANT);
    }

    public Lion(Lion lion) {
        super(lion.getColor(), lion.getAnimalType(), lion.getFear());
    }

    public List<Position> getLegalPositions(Board board) {
        return null;
    }

    public Lion clone() {
        return new Lion(this);
    }
}
