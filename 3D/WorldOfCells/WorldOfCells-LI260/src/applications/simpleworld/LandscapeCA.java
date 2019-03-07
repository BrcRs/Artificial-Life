// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;
import cellularautomata.CellularAutomataInteger;
import worlds.World;

import cellularautomata.LsCell;

import cellularautomata.CellularAutomataLS;

public class LandscapeCA extends CellularAutomataLS {

	CellularAutomataLS _cellsLSCA;
	
	World world;
	
	public LandscapeCA ( World __world, int __dx , int __dy)
	{
		super(__dx,__dy,true ); // buffering must be true.
		
		_cellsLSCA = new CellularAutomataLS(__dx, __dy, true);
		
		this.world = __world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			this.setCellState(x, y, new LsCell());
    		}
    	this.swapBuffer();

	}

	public void step()
	{
		double stp = 0.;
    	for ( int i = 0 ; i != _dx ; i++ )
    		for ( int j = 0 ; j != _dy ; j++ )
    		{
    			this.getCellState(i, j).height = Math.random();
    			/*
    			if (this.getCellState(i, j).mode == LsCell.CellType.NORMAL)
    			{
    				/*
    				this.getCellState(i, j).height += 0.01;
    				this.getCellState(i, j).height = this.getCellState(i, j).height + this.world.getMinEverHeight();
					this.getCellState(i, j).height = this.getCellState(i, j).height % this.world.getMinEverHeight();
					/*
    				if (Math.random() < 1.) // chance of spontaneously be higher or lower
    				{
    					if (Math.random() < 0.) // chance of lower
    					{
    						/*
    						this.getCellState(i, j).height = (this.getCellState(i, j).height - (Math.random() * this.world.getMinEverHeight()/20.0));
    						this.getCellState(i, j).height = this.getCellState(i, j).height + this.world.getMinEverHeight();
    						this.getCellState(i, j).height = this.getCellState(i, j).height % this.world.getMinEverHeight();
    						/*
    						//System.out.println("");
    						
    					}
    					else // chance of higher
    					{
    						this.getCellState(i, j).height = (this.getCellState(i, j).height + (Math.random() * this.world.getMaxEverHeight()/20.0));
    						this.getCellState(i, j).height = this.getCellState(i, j).height + this.world.getMaxEverHeight();
    						this.getCellState(i, j).height = this.getCellState(i, j).height % this.world.getMaxEverHeight();
    					}
    					
    					
    				}
    				
    					
    				else // Be mean of neighbours
    				{
    					// [ TO BE COMPLETED ]
    					//this.getCellState(i, j).height = this.getCellState(i, j).height; 
    				}
    			}
    			/**/
    		}
    	this.swapBuffer();
	}

	
}
