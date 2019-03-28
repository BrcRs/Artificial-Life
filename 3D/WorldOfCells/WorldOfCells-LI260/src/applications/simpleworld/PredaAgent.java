package applications.simpleworld;


import interfaces.*;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public class PredaAgent extends Animal implements Killable
{

	public String nom;
	
	private boolean startHUNTING;



	private int[] origin;

	public ProieAgent target;// cible  /* private */

	public static int cpt = 0;

	public final int id;


	public static final int RUT = 0;
	public static final int HUNTING = 1;
	public static final int NORMAL = 2;
	public static final int RETURNHOME = 3;



	public PredaAgent ( int __x , int __y , World __world, String nom )
	{
		super(__x, __y, __world);
		slowliness = 10;
		sightRange = 50;
		
		currState = Animal.ALIVE;
		behavior = HUNTING;
		startHUNTING = false;
		target = null;
		mate = null;
		deathAge = 2000; //2000
		energy = 800;
		feedFromEnergyLevel = 400;
		this.nom = nom;
		this.id = cpt;
		cpt++;
		origin = new int[2];
		origin[0] = x;
		origin[1] = y;
		
	}

	public PredaAgent ( int __x , int __y , World __world)
	{
		this(__x, __y, __world, "lambda");
	}



	public void searchForMate()
	{
		PredaAgent cible = (PredaAgent) searchForAgentOfType(PredaAgent.class);
		if (cible != this)
		{
			this.mate = cible;
		}



	}

	public ProieAgent getTarget() {
		return target;
	}

	public PredaAgent getMate()
	{
		return (PredaAgent)mate;
	}

	public void searchForPreyOld()
	{
		//System.out.println("Je cherche une proie");
		boolean sortie = false;

		int myAgents_lenX = this.world.getAgents().getCurrentBuffer().length;
		int myAgents_lenY = this.world.getAgents().getCurrentBuffer()[0].length;

		AgentList currCase;


		int u = -sightRange;
		int v = -sightRange;
		//System.out.println("Je suis en [" + x + ", " + y + "]");
		while ( u <= sightRange&& !sortie) 
		{
			v = -sightRange;
			while ( v <= sightRange && !sortie) 
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
					if (a instanceof ProieAgent )
					{
						//System.out.println("Trouve !");
						sortie = true;
						this.target = (ProieAgent) a;
						break;
					}
				}
				v++;
			}
			////System.out.println("" + u);
			u++;
		}



	}
	
	public void searchForPrey()
	{
		
		this.target = (ProieAgent)searchForAgentOfType(ProieAgent.class);


	}




	private void move()
	{
		//System.out.println(this.toString() + "bouge !");

		/**
	int dx = (int)(Math.random() * 3) -1;
	int dy= (int)(Math.random() * 3) -1;

	int myAgents_lenX = this.world.getAgents().getCurrentBuffer().length;
	int myAgents_lenY = this.world.getAgents().getCurrentBuffer()[0].length;

	int newX = (this.x + dx + myAgents_lenX) % myAgents_lenX;
	int newY = (this.y + dy + myAgents_lenY) % myAgents_lenY;
	/**/
		int newX = this.x;
		int newY = this.y;
		if ( world.getIteration() % slowliness == 0 )
		{
			double dice = Math.random();
			if ( dice < 0.25 )
				newX = ( newX + 1 ) % this.world.getWidth() ;
			else
				if ( dice < 0.5 )
					newX = ( newX - 1 +  this.world.getWidth() ) % this.world.getWidth() ;
				else
					if ( dice < 0.75 )
						newY = ( newY + 1 ) % this.world.getHeight() ;
					else
						newY = ( newY - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
		}


		world.getAgents().updateAgent(this, newX, newY);

		/**/
	}

	public void mate(PredaAgent pa)
	{
		PredaAgent enfant = new PredaAgent(this.x, this.y, this.world);
		world.getAgents().updateAgentInitially(enfant, x, y);
		enfant.move();
		this.energy = energy/2;

	}

	public void goToMate()
	{
		//System.out.println("---- goToMate ----");
		//System.out.println("Moi : [" + x + ", " + y + "]");

		//System.out.println("Cible : [" + mate.getCoordinate()[0] + ", " + mate.getCoordinate()[1] + "]");

		if (x == mate.getCoordinate()[0] && y == mate.getCoordinate()[1])
		{
			//System.out.println("Mating !!!!");
			mate((PredaAgent)mate);
			this.behavior = NORMAL;
			mate = null; // experimental

			return;
		}
		else
		{
			if (age % slowliness == 0)
			{



				int diffX = x - mate.getCoordinate()[0];
				int diffY = y - mate.getCoordinate()[1];
				//System.out.println("diffX = " + diffX);
				//System.out.println("diffY = " + diffY);

				int newX = this.x;
				int newY = this.y;

				int e = 1; // e : sign


				if (x != mate.getCoordinate()[0])
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
				if (y != mate.getCoordinate()[1])
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

	public void hunt()
	{


		if (x == target.getCoordinate()[0] && y == target.getCoordinate()[1])
		{
			//System.out.println("Meurtre !!!!");
			kill(target);
			energy += 200;
			target = null;
			return;
		}
		else
		{
			if (age % slowliness == 0)
			{



				int diffX = x - target.getCoordinate()[0];
				int diffY = y - target.getCoordinate()[1];
				//System.out.println("diffX = " + diffX);
				//System.out.println("diffY = " + diffY);

				int newX = this.x;
				int newY = this.y;

				int e = 1; // e : sign


				if (x != target.getCoordinate()[0])
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
				if (y != target.getCoordinate()[1])
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
	/**/
	public void moveto(int[] pos)
	{
		System.out.println("Moveto");
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
/**/
	/* public void step() */
	public void step()
	{

		switch (currState)
		{
		case ALIVE :

			if (health <= 0 || age >= deathAge || energy <= 0)
			{
				currState = Animal.DEAD;
				return;
			}


			switch (behavior)
			{
			
			case NORMAL :
				if (Math.random() < 0.005) // 0.0005
				{
					behavior = RUT;

				}
				if (energy < feedFromEnergyLevel)
				{
					this.behavior = HUNTING;
				}
				move();

				break;
			
			case RETURNHOME :
				/**
				if (Math.random() < 0.005) // 0.0005
				{
					behavior = RUT;

				}
				if (energy < feedFromEnergyLevel)
				{
					this.behavior = HUNTING;
				}
				/**/
				/**/
				moveto(origin);
			
				if (origin[0] == x && origin[1] == y)
				{
					this.behavior = NORMAL;
				}
				/**/

				break;
			case HUNTING :
				/**/
				
				if (startHUNTING)
				{
					startHUNTING = false;
					origin[0] = x;
					origin[1] = y;
				}
				
				if (energy >= feedFromEnergyLevel)
				{
					this.behavior = NORMAL;
					startHUNTING = true;
				}

				/**/
				if (target != null)
				{
					if (target.getThreat() == null)
					{
						target.setThreat(this);
					}
					//System.out.println("searchingForPrey... [" + this.target.getCoordinate()[0] + ", " + this.target.getCoordinate()[1] + "]");

					this.hunt();
				}
				else
				{

					this.searchForPrey();
					this.move();
				}
				/**/
				break;

			case RUT :



				if (mate == null)
				{
					this.searchForMate();
					move();
				}
				else
				{
					this.goToMate();
				}
				break;
			}

			break;
		}
		this.age++;
		this.energy--;

		
		//world.getAgents().updateAgent(this, this.x, this.y);

	}
	/* public void step() */

	/**
public void step()
{
	////System.out.println("Je step() PredaAgent !!");
	if ( world.getIteration() % 20 == 0 )
	{
		double dice = Math.random();
		if ( dice < 0.25 )
			this.x = ( this.x + 1 ) % this.world.getWidth() ;
		else
			if ( dice < 0.5 )
				this.x = ( this.x - 1 +  this.world.getWidth() ) % this.world.getWidth() ;
			else
				if ( dice < 0.75 )
					this.y = ( this.y + 1 ) % this.world.getHeight() ;
				else
					this.y = ( this.y - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
	}
}
/**/


	@Override
	public String toString()
	{
		return "PredaAgent " + this.nom + ", " + "coord [" + x + ", " + y + "]";
	}

	public void kill(Animal ani)
	{
		ani.die();
	}

	/**/
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
	{

		// display a tree

		//gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));

		int x2 = (x-(offsetCA_x%myWorld.getWidth()));
		if ( x2 < 0) x2+=myWorld.getWidth();
		int y2 = (y-(offsetCA_y%myWorld.getHeight()));
		if ( y2 < 0) y2+=myWorld.getHeight();

		float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );

		float altitude = (float)height * normalizeHeight ; // test, a enlever apres
		//gl.glColor3f(1.f,1.f,0.f);

		int cellState = myWorld.getCellValue(x, y);
		if (health < 0)
		{
			cellState = 3;
		}
		switch ( currState )
		{
		case ALIVE:


			gl.glColor3f(1.f,1.f,0.f);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Chapeau rouge*/
			gl.glColor3f(0.5f,0.f,0.5f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);



			break;
		case DEAD: // Burning

			gl.glColor3f(1.f,1.f,0.f);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(1.f,1.f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Chapeau jaune*/
			gl.glColor3f(1.0f,1.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);

			break;
		}
	}
}
