// ### WORLD OF CELLS ###
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;
import worlds.World;
import landscapegenerator.ca_landscape.LoadFromCALandscape;

import java.util.ArrayList;

import cellularautomata.GenerateLSImageFromCA;

public class MyEcosystem {





	public static void main(String[] args) {



		WorldOfTrees myWorld = new WorldOfTrees();


		ArrayList<String> landsList = new ArrayList<String>();
		landsList.add("landscape_paris-200.png");

		landsList.add("landscape_canyon-128.png");
		//landsList.add("landscape_default-128.png");
		landsList.add("landscape_default-200.png");

		landsList.add("landscape_volcan-200 (copie).png");

		landsList.add("Perlin1_200.png");
		landsList.add("Perlin2_200.png");
		landsList.add("Perlin3_200.png");
		landsList.add("Fractal1_200.png");
		landsList.add("Fractal2_200.png");






		// parametres:
		// 1: le "monde" (ou sont definis vos automates cellulaires et agents
		// 2: (ca depend de la methode : generation aleatoire ou chargement d'image)
		// 3: l'amplitude de l'altitude (plus la valeur est elevee, plus hautes sont les montagnes)
		// 4: la quantite d'eau
		//Landscape myLandscape = new Landscape(myWorld, 128, 128, 0.1, 0.1);
		int i=(int)(Math.random()*landsList.size());

		//Landscape myLandscape = new Landscape(myWorld,landsList.get(i), 0.4, 0.5);
		GenerateLSImageFromCA.createImageFromCA(100, 100, (int)(Math.random()*30) + 20);
		Landscape myLandscape = new Landscape(myWorld,"randomLS.png", 0.4, 0.1);


		Landscape.run(myLandscape);
    }

}
