
//import graphics.Landscape;
//import landscapegenerator.LandscapeToolbox;
//import worlds.World;

package cellularautomata;

import applications.simpleworld.LandscapeCA;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

public class GenerateLSImageFromCA {

	static public LandscapeCA createImageFromCA( int __dx, int __dy, int __ite)
	{
		int ite = __ite;
		int[][] img = new int[__dx][__dy];
		int dx = __dx, dy = __dy;
		LandscapeCA caLS = new LandscapeCA(dx, dy);

		caLS.init();
		caLS.getCA().getCellState((int)(dx / 2), (int)(dy / 2)).mode = LsCell.CellType.VOLCANO_UP;

		caLS.growUp(ite);

		// test

		/*
		int red  = 0;
		int green = 0;
		int blue = 150;
		*/
		int chg1 = 1, chg2 = 1, chg3 = 1;
		Color myColour/* = new Color(red, green, blue)*/;
		int rgb/* = myColour.getRGB()*/;

		BufferedImage b = new BufferedImage(dx, dy, 3);

		for(int x = 0; x < dx; x++) {
			for(int y = 0; y < dy; y++) {
				/**/
				if (caLS.getCA().getCellState(x, y).mode == LsCell.CellType.VOLCANO_UP)
				chg1 = 0;
				else
				chg1 = 1;

				if (caLS.getCA().getCellState(x, y).mode == LsCell.CellType.VOLCANO_DOWN)
				chg2 = 0;
				else
				chg2 = 1;

				if (caLS.getCA().getCellState(x, y).mode == LsCell.CellType.STOP)
				chg3 = 0;
				else
				chg3 = 1;
				/**/
				myColour = new Color((caLS.getCA().getCellState(x, y).whiteness * chg2), (caLS.getCA().getCellState(x, y).whiteness * chg1), (caLS.getCA().getCellState(x, y).whiteness * chg3));
				rgb = myColour.getRGB();
				b.setRGB(x, y, rgb);
			}
		}
		/*
		myColour = new Color(255, 0, 0);
		rgb = myColour.getRGB();
		b.setRGB(100, 100, rgb);
		/**/
		try {
			ImageIO.write(b, "png", new File("randomLS.png"));
		} catch (Exception e) {
			System.out.println("Oh non une erreur s'est produite :(");
		}
		System.out.println("end");
		return caLS;
	}


}
