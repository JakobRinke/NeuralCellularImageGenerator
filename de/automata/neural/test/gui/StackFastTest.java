package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.ImagePostprocessor;
import de.automata.neural.base.MapCreator;
import de.automata.neural.test.StackFilterTest;
import de.automata.neural.test.Start;

public class StackFastTest extends JPanel{
	
	
	public StackFastTest() {
	    setBackground(Color.white);
	}
	
	
	int size = StackFilterTest.windowSize / StackFilterTest.ImgSize;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
			float[][] m = StackFilterTest.createMap();
			if (StackFilterTest.postProcessOnDisplay)
			{
				m = ImagePostprocessor.brightnessFilter(m, 0.0f);
				m = ImagePostprocessor.contrastFilter(m, 1.25f);
//				m = ImagePostprocessor.useTan(m);
				m = ImagePostprocessor.FilterOut(m, 0.35f);
				m = ImagePostprocessor.smoothInMap(m, 5);
				m = ImagePostprocessor.smoothMap(m, 1.0f);
//				m = ImagePostprocessor.Offset(m, 0, 2);
			}
			
			float[][][] k = MapCreator.colorInMap(m, StackFilterTest.color1, StackFilterTest.color2);
		    for (int x = 0; x < StackFilterTest.ImgSize; x++)
		    {
		    	for (int y = 0; y <  StackFilterTest.ImgSize; y++)
		    	{
		    		g2D.setColor(new Color((int) (k[x][y][0] * 255),(int) (k[x][y][1] * 255),(int) (k[x][y][2] * 255)));
		    		g2D.fillRect(x * size, y * size, size, size);
		    	}
		    }
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
	  
}
