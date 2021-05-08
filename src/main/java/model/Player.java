package model;

public class Player implements IPlayer{
    private final String name;
    private final Color color;

    public Player(String name, Color color) {
        if (name == null || color == null)
            throw new IllegalArgumentException();

        this.name = name;
        this.color = color;
    }

    public Movement getMove(Board board){
        //chama a UI para escolher o movmente
        return null;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int compareTo(IPlayer o) {
        return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }

    @Override
    public String toString() {
        return "Player -> " +
                "name= " + name ;
    }
}
