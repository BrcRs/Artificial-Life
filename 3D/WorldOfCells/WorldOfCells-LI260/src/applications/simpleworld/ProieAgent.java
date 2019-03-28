package applications.simpleworld;


import interfaces.*;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public class ProieAgent extends Animal implements Killable
{

	

	

	public static final int RUT = 0;
	public static final int NORMAL = 1;
	public static final int FLIGHT = 2;
	
	
	private Agent threat;



	//private boolean satiete; // La faim


	public ProieAgent ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		//satiete = false;
		slowliness = 15;
		deathAge = 500; // 500
		sightRange = 20;
		threat = null;
		currState = ALIVE;
	}


private void move()
{

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


public void searchForMate()
{
	ProieAgent cible = (ProieAgent) searchForAgentOfType(ProieAgent.class);
	if (cible != this)
	{
		this.mate = cible;
	}
}


public void goToMate()
{
	//System.out.println("---- goToMate ----");
	//System.out.println("Moi : [" + x + ", " + y + "]");

	//System.out.println("Cible : [" + mate.getCoordinate()[0] + ", " + mate.getCoordinate()[1] + "]");

	if (x == mate.getCoordinate()[0] && y == mate.getCoordinate()[1])
	{
		//System.out.println("Mating !!!!");
		mate((ProieAgent)mate);
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

public void mate(ProieAgent pa)
{
	ProieAgent enfant = new ProieAgent(this.x, this.y, this.world);
	world.getAgents().updateAgentInitially(enfant, x, y);
	enfant.move();

}

public void setThreat(Agent threat)
{
	this.threat = threat;
}
public Agent getThreat()
{
	return this.threat;
}

public void onTheAlert()
{
	
	
}

public void escapeFrom(Agent ag)
{

		if (age % slowliness == 0)
		{



			int diffX = x - ag.getCoordinate()[0];
			int diffY = y - ag.getCoordinate()[1];
			//System.out.println("diffX = " + diffX);
			//System.out.println("diffY = " + diffY);

			int newX = this.x;
			int newY = this.y;

			int e = 1; // e : sign



				if (Math.abs(diffX) > world.getWidth()/2)
				{
					e = -1;

				}
				else
				{
					e = 1;
				}
				newX = ( newX + e * Integer.signum(diffX) +  this.world.getWidth() ) % this.world.getWidth() ;


				if (Math.abs(diffY) > world.getHeight()/2)
				{
					e = -1;
				}
				else
				{
					e = 1;
				}
				newY = ( newY + e * Integer.signum(diffY) +  this.world.getHeight() ) % this.world.getHeight() ;
			System.out.println(this.toString() + " fuit " + ag.toString() );
			System.out.println("Nouvelle position : " + newX + ", " + newY);

			world.getAgents().updateAgent(this, newX, newY);
		}
	

	
}


/* public void step() */
public void step()
{

	switch (currState)
	{
	case ALIVE :

		if (health <= 0 || age >= deathAge)
		{
			currState = Animal.DEAD;
			return;
		}
		
		if (threat != null)
		{
			this.behavior = FLIGHT;
		}


		switch (behavior)
		{
		case NORMAL :
			if (Math.random() < 0.00005) // 0.0005
			{
				behavior = RUT;

			}

			move();
			break;
			
		case FLIGHT :
			if (threat == null)
			{
				this.behavior = NORMAL;
			}
			else
			{
				escapeFrom(threat);
				threat = null;
				
			}
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
	//world.getAgents().updateAgent(this, this.x, this.y);

}
@Override
public String toString()
{
	return "ProieAgent " +  ", " + "coord [" + x + ", " + y + "]";
}


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


		switch ( currState )
		{
			case ALIVE:


				gl.glColor3f(1.f,1.f,1.f);
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
			/*Chapeau bleu*/
			gl.glColor3f(0.2f,0.2f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);



			break;
			case DEAD:

			gl.glColor3f(0.f,0.f,0.f);
			/*Cote blanc*/
			gl.glColor3f(0.f,0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(0.f,0.f,0.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(0.f,0.f,0.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			/*Cote blanc*/
			gl.glColor3f(0.f,0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
			/*Chapeau bleu*/
			gl.glColor3f(0.2f,0.2f,1.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
			gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);

			break;
		}


	}
	/**/
	}
