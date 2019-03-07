// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;
import cellularautomata.CellularAutomataInteger;
import worlds.World;

import cellularautomata.CellularAutomataLS;

public class LandscapeCA extends CellularAutomataLS {

	CellularAutomataLS _cellsHeightValuesCA;
	
	World world;
	
	public LandscapeCA ( World __world, int __dx , int __dy)
	{
		super(__dx,__dy,true ); // buffering must be true.
		
		_cellsHeightValuesCA = new CellularAutomataLS(__dx, __dy, true);
		
		this.world = __world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			// [A REMPLIR]
    		}
    	this.swapBuffer();

	}

	public void step()
	{
    	for ( int i = 0 ; i != _dx ; i++ )
    		for ( int j = 0 ; j != _dy ; j++ )
    		{
    			// [A REMPLIR]
    		}
    	this.swapBuffer();
	}

	
}
