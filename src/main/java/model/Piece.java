package model;

import java.util.List;

public abstract class Piece implements IPiece {
    protected final Color color;
    protected final EAnimalType EAnimalType;
    protected final EAnimalType fear;
    protected Location location;

    public Piece(Color color, EAnimalType EAnimalType, EAnimalType fear, Location location) {
        this.color = color;
        this.EAnimalType = EAnimalType;
        this.fear = fear;
        this.location = location;
    }


    public Color getColor() {
        return color;
    }

    public EAnimalType getAnimalType() {
        return EAnimalType;
    }

    public EAnimalType getFear() {
        return fear;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public abstract List<Position> getLegalPositions(Board boardGame);

    public abstract IPiece clone();

}
