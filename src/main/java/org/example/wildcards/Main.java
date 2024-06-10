package org.example.wildcards;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // List of buildings
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(8));
        buildings.add(new Building(7));
        printBuildings_2(buildings);
        // List of offices
        List<Office> offices = new ArrayList<>();
        offices.add(new Office(3));
        offices.add(new Office(2));
        //printBuildings(offices);
        printBuildings_2(offices);
        // List of houses
        List<House> houses = new ArrayList<>();
        houses.add(new House(4));
        houses.add(new House(7));
        //printBuildings(houses);
        printBuildings_2(houses);
    }

    private static void printBuildings(List<Building> buildings) {
        for (int i = 0; i < buildings.size(); i++) {
            System.out.println(buildings.get(i).toString() + " " + (i+1) + " with " +
                    buildings.get(i).getNumberOfRooms() + " rooms");
        }
        System.out.println();
    }

    private static void printBuildings_2(List<? extends Building> buildings) {
        for (int i = 0; i < buildings.size(); i++) {
            System.out.println(buildings.get(i).toString() + " " + (i+1) + " with " +
                    buildings.get(i).getNumberOfRooms() + " rooms");
        }
        System.out.println();
    }
}
