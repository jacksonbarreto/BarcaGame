package model;

import java.util.List;

import static model.AnimalType.*;

public class Elephant extends Piece {

    public Elephant(Color color) {
        super(color, ELEPHANT, MOUSE);
    }

    public Elephant(Elephant elephant) {
        super(elephant.getColor(), elephant.getAnimalType(), elephant.getFear());
    }

    public List<Position> getLegalPositions(Board board) {
        return null;
    }

    public Elephant clone() {
        return new Elephant(this);
    }
}
