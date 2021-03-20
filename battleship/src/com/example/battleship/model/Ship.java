package com.example.battleship.model;

/*
 * Class represents a ship.
 */
public class Ship {

	String [] locations;
	String [] hits;
	
	/*
	 * Constructs a ship.
	 * Initializes the ship with empty locations and empty hits.
	 */
	public Ship() {
	
		locations = new String[Model.shipLength];
		hits = new String[Model.shipLength];
		
		for (int i = 0; i < Model.shipLength; i++) {
			locations[i] = "";
			hits[i] = "";
		}
	}
}
