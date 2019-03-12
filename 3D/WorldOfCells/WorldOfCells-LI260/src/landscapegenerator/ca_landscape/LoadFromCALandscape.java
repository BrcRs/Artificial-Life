package landscapegenerator.ca_landscape;
import graphics.Landscape;
import landscapegenerator.LandscapeToolbox;
import worlds.World;
import applications.simpleworld.LandscapeCA;

public class LoadFromCALandscape {
	
	static public double[][] createLS(World __world, int __dx, int __dy, int __ite)
	{
		int ite = __ite;
		double[][] newLandscape = new double[__dx][__dy];
		
		LandscapeCA caLS = new LandscapeCA( __world,  __dx,  __dy);
		
		caLS.init();
		
		for (int i = 0 ; i < ite ; i++)
		{
			caLS.step();
		}
		
		for (int x = 0 ; x < __dx ; x++)
			for (int y = 0; y < __dy ; y++)
			{
				newLandscape[x][y] = caLS.getCellState(x, y).height;
			}
		newLandscape = LandscapeToolbox.smoothLandscape(newLandscape);
		
		return newLandscape;
		
	}
}
