package applications.simpleworld;


import interfaces.*;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public class GrassAgent extends Plant implements Killable
{

	public GrassAgent ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 200;
		energy = 200;

	}

	public void step()
	{
		if (health <= 0)
		{
			this.currState = DEAD;
		}
		switch (currState)
		{
		case ALIVE :
			
			if ( Math.random() < 0.00000 || world.getmyLava().isLava(x, y)) // spontaneously take fire ? // 0.000005
			{
				this.currState = BURNING;
				return;
			}
			
			if (Math.random() < 0.000001) // spontaneously become a tree ?
			{
				this.currState = DEAD;
				this.world.getAgents().updateAgentInitially(new TreeAgent(x, y,this.world)
						,x
						,y);

			}

			boolean isPlantHere = false;
			//boolean sortie = false;
			for ( int i = -1 ; i <= 1 ; i++ )
			{
				for ( int j = -1 ; j <= 1 ; j++ )
				{
					isPlantHere = false;
					for (Agent a : world.getAgents().getCurrentBuffer()[(x + i + world.getWidth())%(world.getWidth())][(y + j + world.getHeight())%(world.getHeight())])
					{
						if (a instanceof Plant)
						{
							isPlantHere = true;
							//System.out.println(a.toString());
							if (((Plant) a).getState() == BURNING)
							{
								if (Math.random() < 0.0025)
								{
									//System.out.println(this.toString() + "brule a cause de" + a.toString());
									this.currState = BURNING;
									break;
								}
							}


						}
						

					}
					if (isPlantHere == false && Math.random() < 0.00015)
					{
						// Ajouter une herbe
						this.world.getAgents().updateAgentInitially(new GrassAgent((x + i + world.getWidth())%(world.getWidth()), (y + j + world.getHeight())%(world.getHeight()),this.world)
								,(x + i + world.getWidth())%(world.getWidth())
								,(y + j + world.getHeight())%(world.getHeight()));
					}


				}


			}
			break; // case ALIVE

		case BURNING :
			/**/

			/**/
			for ( int i = -1 ; i <= 1 ; i++ )
			{
				for ( int j = -1 ; j <= 1 ; j++ )
				{
					isPlantHere = false;
					for (Agent a : world.getAgents().getCurrentBuffer()[(x + i + world.getWidth())%(world.getWidth())][(y + j + world.getHeight())%(world.getHeight())])
					{
						if (a instanceof Animal)
						{
							// Mettre l'animal en feu
							((Animal) a).setState(BURNING);
						}
						

					}

				}


			}
	/**/
			
			health--;
			if (health <= 0)
			{
				health = 1;
				currState = BURNT;	
			}
			break;

		case BURNT :
			/**/
			if (Math.random() < 0.001)
			{
				currState = DEAD;
			}
			/**/
			break;

		} // switch (currState)
	}



	public int die()
	{
		health = 0;
		//this.currState = DEAD;
		return health;
	}

	@Override
	public String toString()
	{
		return "GrassAgent [" + x + ", " + y + "]"; 
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

		int cellState = myWorld.getCellValue(x, y);
		if (health < 0)
		{
			cellState = 3;
		}
		/**
    	else
    	{
    	if (world.getmyLava().isLava(x, y))
    	{
    		myWorld.setCellValue(x, y, 2);
    		cellState = 2;
    	}
    	}
    	/**/
		switch ( currState )
		{
		case ALIVE:
			/* V ORIGINAL V *
        		gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
            	gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/2.f, altitude + 4.f );//2
        		gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//1

                gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/2.f, altitude + 4.f );//4
                gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//3


                gl.glVertex3f( offset+x2*stepX-lenY/2.f, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
        		gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//5

                gl.glVertex3f( offset+x2*stepX+lenY/2.f, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
                gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//7



                gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
            	gl.glVertex3f( offset+x2*stepX-lenY/16.f - 2, offset+y2*stepY+lenY/2.f, altitude + 4.f );//2
        		gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//1

                gl.glVertex3f( offset+x2*stepX+lenY/16.f - 2, offset+y2*stepY-lenY/2.f, altitude + 4.f );//4
                gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//3


                gl.glVertex3f( offset+x2*stepX-lenY/2.f - 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
        		gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//5

                gl.glVertex3f( offset+x2*stepX+lenY/2.f- 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
                gl.glVertex3f( offset+x2*stepX- 2, offset+y2*stepY, altitude );//7
                /* ^ ORIGINAL ^ */
			gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//7



			gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f - 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f - 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f - 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f- 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX- 2, offset+y2*stepY, altitude );//7




			gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f+ 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f+ 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f+ 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f+ 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//7



			gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f-2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f-2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f-2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f-2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//7



			gl.glColor3f(0.2f,0.5f-(float)(0.2*Math.random()),0.2f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f+2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f+2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f+2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f+2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//7

			break;
		case BURNING : // Burning
			/**
        		gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
            	gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/2.f, altitude + 4.f );//2
        		gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//1

                gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/2.f, altitude + 4.f );//4
                gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//3


                gl.glVertex3f( offset+x2*stepX-lenY/2.f, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
        		gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//5

                gl.glVertex3f( offset+x2*stepX+lenY/2.f, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
                gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//7
                /**/





			gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//7



			gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f - 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f - 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f - 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f- 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX- 2, offset+y2*stepY, altitude );//7




			gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f+ 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f+ 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f+ 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f+ 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//7



			gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f-2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f-2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f-2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f-2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//7



			gl.glColor3f(1.f-(float)(0.2*Math.random()),0.f,0.f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f+2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f+2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f+2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f+2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//7
			break;
		case BURNT : // Burnt

			gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY, altitude );//7



			gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f - 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f - 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f - 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX - 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f- 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX- 2, offset+y2*stepY, altitude );//7




			gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f+ 2, offset+y2*stepY+lenY/3.f, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f+ 2, offset+y2*stepY-lenY/3.f, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f+ 2, offset+y2*stepY+lenY/16.f, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f+ 2, offset+y2*stepY-lenY/16.f, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX+ 2, offset+y2*stepY, altitude );//7



			gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f-2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f-2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f-2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f-2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY-2, altitude );//7



			gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
			gl.glVertex3f( offset+x2*stepX-lenY/16.f, offset+y2*stepY+lenY/3.f+2, altitude + 4.f );//2
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//1

			gl.glVertex3f( offset+x2*stepX+lenY/16.f, offset+y2*stepY-lenY/3.f+2, altitude + 4.f );//4
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//3


			gl.glVertex3f( offset+x2*stepX-lenY/3.f, offset+y2*stepY+lenY/16.f+2, altitude + 4.f );//6
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//5

			gl.glVertex3f( offset+x2*stepX+lenY/3.f, offset+y2*stepY-lenY/16.f+2, altitude + 4.f );//8
			gl.glVertex3f( offset+x2*stepX, offset+y2*stepY+2, altitude );//7
			break;

		case DEAD :

			break;
		}


	}
}