package model;

import java.util.Objects;

public class Movement {
    private final Location origin;
    private final Location destination;

    public Movement(Location origin, Location destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return origin.equals(movement.origin) && destination.equals(movement.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
