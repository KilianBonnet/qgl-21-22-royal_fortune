package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import java.util.List;

import fr.unice.polytech.si3.qgl.royal_fortune.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class NextRoundDAO {
	Ship ship;
	List<Entities> visibleEntities;
	Wind wind;


	public NextRoundDAO() {}
	public NextRoundDAO(Ship ship, List<Entities> visibleEntities, Wind wind) {
		super();
		this.ship = ship;
		this.visibleEntities = visibleEntities;
		this.wind = wind;
	}
	
	public Ship getShip() {
		return ship;
	}

	public Wind getWind() { return wind; }

	public List<Entities> getVisibleEntities() {
		return visibleEntities;
	}
	
}
