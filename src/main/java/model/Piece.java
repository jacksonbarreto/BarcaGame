package model;

public abstract class Piece {
    private final Color color;
    private final AnimalType animalType;
    private final AnimalType fear;

    public Piece(Color color, AnimalType animalType, AnimalType fear) {
        this.color = color;
        this.animalType = animalType;
        this.fear = fear;
    }


    public Color getColor() {
        return color;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public AnimalType getFear() {
        return fear;
    }

    public abstract Piece clone();

}
