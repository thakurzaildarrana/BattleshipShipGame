package com.example.battleship.model;

import com.example.battleship.application.View;


/*
 * Class represents the game model.
 */
public class Model {

    // Constant values for game attributes.
    public static final int boardSize = 7;
    public static final int numShips = 3;
    public static final int shipLength = 3;

    public int shipsSunk;
    private Ship [] ships;
    private View view;

    /*
     * Constructs the model.
     * Creates the ships and their locations on the table.
     * Gets a reference to the GameWindow.
     */
    public Model(View view) {

        ships = new Ship[numShips];

        for (int i = 0; i < numShips; i++) {
            ships[i] = new Ship();
        }

        generateShipLocations();

        this.view = view;
    }

    /*
     * The "fire" action updates the ship attributes with "hit" or "miss" data.
     * Tracks the ships that are sunk.
     * Updates the GameWindow table with a hit or miss.
     * Returns a boolean true if its a hit, else a false for a miss.
     */
    public boolean fire(String guess) {

        for (int i = 0; i < numShips; i++) {

            Ship ship = ships[i];
            int index = getShipLocationIndex(guess, ship.locations);

            if (index >= 0) {

                ship.hits[index] = "hit";
                view.displayMessage("HIT!");
                view.displayHitOrMiss(guess, "HIT");

                if (isSunk(ship)) {

                    view.displayMessage("You sank my battleship!");
                    this.shipsSunk++;
                }

                return true;
            }
        }

        view.displayMessage("You missed.");
        view.displayHitOrMiss(guess, "MISS");
        return false;
    }

    /*
     * Gets the index of the ship's location for an input guess string.
     * Returns the index or a -1 (-1 indicates that there is no ship location
     * for the input guess).
     */
    private int getShipLocationIndex(String guess, String [] shipLocations) {

        for (int i = 0; i < shipLocations.length; i++) {

            if (shipLocations[i].equals(guess)) {
                return i;
            }
        }

        return -1;
    }

    /*
     * Method determines if a input ship is sunk or not -
     * returns a boolean true if sunk else false.
     */
    public boolean isSunk(Ship ship) {

        for (int i = 0; i < shipLength; i++) {

            if (! ship.hits[i].equals("hit")) {
                return false;
            }
        }

        return true;
    }

    /*
     * Method creates the locations for all the ships.
     */
    private void generateShipLocations() {

        String [] locations;

        for (int i = 0; i < numShips; i++) {

            do {
                locations = generateShip();
            } while (collision(locations));

            ships[i].locations = locations;
        }
    }

    /*
     * Method creates locations for a ship.
     */
    private String [] generateShip() {

        int row, col;
        double direction = Math.floor(Math.random() * 2);

        if (direction == 1) {
            row = (int) Math.floor(Math.random() * boardSize);
            col = (int) Math.floor(Math.random() * (boardSize - shipLength));
        }
        else {
            row = (int) Math.floor(Math.random() * (boardSize - shipLength));
            col = (int) Math.floor(Math.random() * boardSize);
        }

        String [] newShipLocations = new String [shipLength];

        for (int i = 0; i < shipLength; i++) {

            if (direction == 1) {
                newShipLocations[i] = row + "" + (col + i);
            }
            else {
                newShipLocations[i] = (row + i) + "" + col;
            }
        }

        return newShipLocations;
    }

    /*
     * Method checks if the locations for a ship are
     * overlapping with other ship's locations.
     * Returns a boolean true if collision occurs, else false.
     */
    private boolean collision(String [] locations) {

        for (int i = 0; i < numShips; i++) {

            Ship ship = ships[i];

            for (int j = 0; j < locations.length; j++) {

                for (int k = 0; k < ship.locations.length; k++) {

                    if (locations[j].equals(ship.locations[k])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

