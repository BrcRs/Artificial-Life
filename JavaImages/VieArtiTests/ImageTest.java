import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;


public class ImageTest
{
	public static void main(String[] args)
	{
		GenerateLSImageFromCA.createImageFromCA(100, 100, Integer.parseInt(args[0]));

	}

}
