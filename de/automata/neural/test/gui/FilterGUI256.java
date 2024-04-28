package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.automata.neural.base.ImagePostprocessor;
import de.automata.neural.base.MapCreator;
import de.automata.neural.test.FilterTest256;

public class FilterGUI256 extends JPanel{

	public FilterGUI256() {
	    setBackground(Color.white);
	}
	
	
	int size = FilterTest256.windowSize / FilterTest256.ImgSize;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
		g.clearRect(0, 0, FilterTest256.windowSize, FilterTest256.windowSize);
	    
	    try {
			float[][] m = FilterTest256.createMap();
			if (FilterTest256.postProcessOnDisplay)
			{
				m = ImagePostprocessor.brightnessFilter(m, -0.15f);
				m = ImagePostprocessor.contrastFilter(m, 1.2f);
				m = ImagePostprocessor.FilterOut(m, 0.5f);

				m = ImagePostprocessor.useTan(m, 0.858f);
				m = ImagePostprocessor.smoothInMap(m, 3);
				m = ImagePostprocessor.smoothInMap(m, 3);

				m = ImagePostprocessor.smoothMap(m, 0.6f);
				m = ImagePostprocessor.Offset(m, 0, 40);
			}
			
			float[][][] k = MapCreator.colorInMap(m, FilterTest256.color1, FilterTest256.color2);
		    for (int x = 0; x < FilterTest256.ImgSize; x++)
		    {
		    	for (int y = 0; y <  FilterTest256.ImgSize; y++)
		    	{
					if (k[x][y][0] == 0 && k[x][y][1] == 0 && k[x][y][2] == 0) {
						g.setColor(new Color(0,0,0,0));
						continue;
					}
		    		g2D.setColor(new Color((int) (k[x][y][0] * 255),(int) (k[x][y][1] * 255),(int) (k[x][y][2] * 255)));
		    		g2D.fillRect(x * size, y * size, size, size);
		    	}
		    }
			saveImage(k);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
	    
	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
	    repaint();
	    
	  }


	  public void saveImage(float[][][] k) {

		// Safe the image in the folder ./256_Test/ships/Math.random()/ship.png
		BufferedImage image = new BufferedImage(FilterTest256.windowSize, FilterTest256.windowSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		for (int x = 0; x < FilterTest256.ImgSize; x++) {
			for (int y = 0; y < FilterTest256.ImgSize; y++) {
				g2.setColor(new Color((int) (k[x][y][0] * 255), (int) (k[x][y][1] * 255), (int) (k[x][y][2] * 255)));
				g2.fillRect(x * size, y * size, size, size);
			}
		}




		try {
			// create file if it does not exist

			ImageIO.write(image, "png", new java.io.File("./256_Test/ships/ship"+ (int) (Math.random() * 10000)+ ".png"));
		} catch (IOException e) {
			// Create folder if it does not exist
			File file = new File("./256_Test/ships/");
			if (!file.exists()) {
				if (file.mkdirs()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			return;
		}

	  }





	  
}
