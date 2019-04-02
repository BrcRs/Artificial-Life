package applications.simpleworld;

import applications.simpleworld.Agent;

import interfaces.Killable;

import worlds.World;

public class Plant extends Agent implements Killable {
	
			
	protected int health;
		
	protected double energy;
			
	protected int currState;
	
	//strength
	
	/**
	public enum State{

		ALIVE, DEAD; // Il y a un probleme avec les enums

	}
	/**/
	/* En attente de trouver mieux */
	public static final int ALIVE = 0;
	public static final int DEAD = 1;
	public static final int BURNING = 2;
	public static final int BURNT = 3;
	
	
	
	/**
	private enum behavior{



}
/**/

	
	public Plant( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 4048;
		energy = 500;

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
