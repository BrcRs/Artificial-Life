package applications.simpleworld;

import applications.simpleworld.Agent;

import interfaces.Killable;

import worlds.World;

public class Animal extends Agent implements Killable {
	
	protected int age;
	
	protected int slowliness;
	
	protected int deathAge;
	
	protected int health;
	
	protected Animal mate;
	
	protected double energy;
	
	protected double feedFromEnergyLevel;
	
	protected int sightRange;
	
	protected int behavior;
	
	//strength
	
	/**
	public enum State{

		ALIVE, DEAD; // Il y a un probleme avec les enums

	}
	/**/
	/* En attente de trouver mieux */
	public static final int ALIVE = 0;
	public static final int DEAD = 1;
	
	
	/* Implementer une barre de vie en 3d au dessus des animaux */
	
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
    
    
    /* pourrait etre plus exhaustive avec une inspiration de la méthode radar */
    public Agent searchForAgentOfType(Class type)
    {
		boolean sortie = false;

		int myAgents_lenX = this.world.getAgents().getCurrentBuffer().length;
		int myAgents_lenY = this.world.getAgents().getCurrentBuffer()[0].length;
		
		int diffX ;
		int diffY ;
		
		int meilleur_diffXplusY = myAgents_lenX + myAgents_lenY;

		AgentList currCase;

		Agent meilleurCandidat = null;
		
		int u = 0;
		int v = 0;
		while (u < sightRange && !sortie)
		{
			currCase = this.world.getAgents().getCurrentBuffer()[(this.x + u + myAgents_lenX) % myAgents_lenX][(this.y + v + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
			

			currCase = this.world.getAgents().getCurrentBuffer()[(this.x + u + myAgents_lenX) % myAgents_lenX][(this.y + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
			currCase = this.world.getAgents().getCurrentBuffer()[(this.x + myAgents_lenX) % myAgents_lenX][(this.y + v + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
			currCase = this.world.getAgents().getCurrentBuffer()[(this.x - u + myAgents_lenX) % myAgents_lenX][(this.y - v + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
			currCase = this.world.getAgents().getCurrentBuffer()[(this.x - u + myAgents_lenX) % myAgents_lenX][(this.y + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
			currCase = this.world.getAgents().getCurrentBuffer()[(this.x + myAgents_lenX) % myAgents_lenX][(this.y - v + myAgents_lenY) % myAgents_lenY];
			for (Agent a : currCase)
			{
				if (a.getClass() == type )
				{
					sortie = true;
					diffX = Math.abs(x - a.getCoordinate()[0]);
					diffY = Math.abs(y - a.getCoordinate()[1]);
					if (diffX + diffY < meilleur_diffXplusY)
					{
						meilleur_diffXplusY = diffX + diffY;
						meilleurCandidat = a;
					}
					break;
				}
			}
		v++;
		u++;
		}

		return meilleurCandidat;



    }
	
}
