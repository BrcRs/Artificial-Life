import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.Color;

public class TestImage
{
  public static void main(String[] args) {

    /*
    int red  = 0;
    int green = 0;
    int blue = 150;
    */
    Color myColour/* = new Color(red, green, blue)*/;
    int rgb/* = myColour.getRGB()*/;

    double[][] arr = new double[255][255];

    int xLength = arr.length;
    int yLength = arr[0].length;
    BufferedImage b = new BufferedImage(xLength, yLength, 3);

    for(int x = 0; x < xLength; x++) {
      for(int y = 0; y < yLength; y++) {
        myColour = new Color(( x + 255) % 255, ( y + 255) % 255, (x * y + 255) % 255);
        rgb = myColour.getRGB();
        b.setRGB(x, y, rgb);
      }
    }
    try {
      ImageIO.write(b, "png", new File("Doublearray.png"));
    } catch (Exception e) {
      System.out.println("Oh non une erreur s'est produite :(");
    }
    System.out.println("end");
  }
}
