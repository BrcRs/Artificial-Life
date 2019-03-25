package applications.simpleworld;


import interfaces.*;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public class GrassAgent extends Agent implements Killable
{
	
	int health;
	public GrassAgent ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 4048;
	}
	
	public void step()
	{
	
	}
	public int die()
	{
		health = 0;
		return health;
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
		switch ( cellState )
        {
        	case 1:
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
        	case 2: // Burning
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
        	case 3: // Burnt
        		gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f+(float)(0.2*Math.random()),0.f+(float)(0.2*Math.random()));
        		break;
        }
    	
    	
    }
}