package applications.simpleworld;

import applications.simpleworld.Agent;

import interfaces.Killable;

import worlds.World;

public class Animal extends Agent implements Killable {
	
	protected int age;
	
	protected int deathAge;
	
	protected int health;
	
	protected Animal mate;
	
	protected double energy;
	
	protected double feedFromEnergyLevel;
	
	protected int sightRange;
	
	protected int behavior;
	
	/**
	public enum State{

		ALIVE, DEAD; // Il y a un probleme avec les enums

	}
	/**/
	/* En attente de trouver mieux */
	public static final int ALIVE = 0;
	public static final int DEAD = 1;
	
	
	/**
	private enum behavior{



}
/**/

	protected int currState;
	
	public Animal( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 4048;
		age = 0;
		mate = null;
		sightRange = 5;
		deathAge = 1000;
		energy = 500;
		feedFromEnergyLevel = 300;

	}

    public int die()
    {
    	health = 0;
    	return health;
    }
    
    public int getState()
    {
    	return currState;
    }
    
    public void setState(int newState)
    {
    	this.currState = newState;
    }
	
}
