package org.example.wildcards;

public class Building {
    protected int numberOfRooms;

    public Building(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String toString() {
        return ("Building");
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }
}
