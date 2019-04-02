package applications.simpleworld;

import java.util.ArrayList;

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

	protected int birthAge;

	ArrayList<Agent> voisins;

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


	/* Implementer une barre de vie en 3d au dessus des animaux */

	/**
	private enum behavior{



}
/**/

	protected int currState;

	public Animal( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 200;
		age = 0;
		mate = null;
		sightRange = 5;
		deathAge = 1000;
		energy = 500;
		feedFromEnergyLevel = 300;
		voisins = new ArrayList<Agent>();

		birthAge = deathAge/2;

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



	public void moveto(int[] pos)
	{
		//System.out.println("Moveto");
		if (x == pos[0] && y == pos[1])
		{

			return;
		}
		else
		{
			if (age % slowliness == 0)
			{



				int diffX = x - pos[0];
				int diffY = y - pos[1];
				//System.out.println("diffX = " + diffX);
				//System.out.println("diffY = " + diffY);

				int newX = this.x;
				int newY = this.y;

				int e = 1; // e : sign


				if (x != pos[0])
				{
					if (Math.abs(diffX) > world.getWidth()/2)
					{
						e = -1;

					}
					else
					{
						e = 1;
					}
					newX = ( newX - e * diffX/Math.abs(diffX) +  this.world.getWidth() ) % this.world.getWidth() ;

				}
				if (y != pos[1])
				{
					if (Math.abs(diffY) > world.getHeight()/2)
					{
						e = -1;
					}
					else
					{
						e = 1;
					}
					newY = ( newY - e * diffY/Math.abs(diffY) +  this.world.getHeight() ) % this.world.getHeight() ;
				}

				world.getAgents().updateAgent(this, newX, newY);

			}
		}

	}
	public Agent searchForAgentOfType(Class type)
	{
		// Mettre voisins à vide
		voisins.clear();
		//System.out.println("Je cherche une proie");

		int myAgents_lenX = this.world.getAgents().getCurrentBuffer().length;
		int myAgents_lenY = this.world.getAgents().getCurrentBuffer()[0].length;

		AgentList currCase;


		int u = -sightRange;
		int v = -sightRange;
		//System.out.println("Je suis en [" + x + ", " + y + "]");
		while ( u <= sightRange) 
		{
			v = -sightRange;
			while ( v <= sightRange) 
			{
				if (v == 0 && u == 0)
				{
					v++;
					continue;
				}
				////System.out.println("scanning : [" + ((this.x + u + myAgents_lenX) % myAgents_lenX) + ", " + ((this.y + v + myAgents_lenY) % myAgents_lenY) + "]");
				currCase = this.world.getAgents().getCurrentBuffer()[(this.x + u + myAgents_lenX) % myAgents_lenX][(this.y + v + myAgents_lenY) % myAgents_lenY];

				for (Agent a : currCase)
				{
					////System.out.println("Oh un autre agent !");
					if (a.getClass() == type )
					{
						//System.out.println("Trouve !");
						//return (Agent)a;// Ajouter l'agent aux voisins
						voisins.add(a);
					}
				}
				v++;
			}
			////System.out.println("" + u);
			u++;
		}

		Agent proche;
		// Faire le calcul du voisin le plus proche
		if (!voisins.isEmpty())
		{
			proche=voisins.get(0);

			for( Agent a: voisins) {

				int distance1=(int)Math.sqrt((Math.pow(Math.subtractExact(a.getCoordinate()[0] , x),2)+ Math.pow(Math.subtractExact(a.getCoordinate()[1] , y),2)));
				int distance2=(int)Math.sqrt((Math.pow(Math.subtractExact(proche.getCoordinate()[0] , x),2)+ Math.pow(Math.subtractExact(proche.getCoordinate()[1] , y),2)));
				//System.out.println("distance1 : " + distance1 + " distance2 : " + distance2);

				if(distance1 < distance2) {
					proche=a;
				}



			}
		}
		else
		{
			return null;
		}



		// Renvoyer ce voisin
		return proche;



	}


	/* pourrait etre plus exhaustive avec une inspiration de la méthode radar */
	public Agent searchForAgentOfTypeOld(Class type)
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
