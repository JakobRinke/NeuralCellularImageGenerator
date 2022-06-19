package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.ImagePostprocessor;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.base.TrainerSettings;
import de.automata.neural.test.Start;

public class FastTest extends JPanel {
	
	
	public static float[] input = {0.15847003f, -0.1437898f, -0.021752862f, 0.38593325f, 0.38593325f, 0.38593325f};
	public static int iterations = 10;
	public static float mergeVal = 0.6f;
	
	
	public FastTest() {
	    setBackground(Color.white);
	    TrainerSettings.mergeVal = mergeVal;
	}
	
	int size = Start.windowSize / TrainerSettings.imgWidth;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {

			
			Pattern p = new Pattern(EvolutionaryPatternTrainer.getFilterFromInputs(input));
			p.setMap(MapCreator.createRandmap(TrainerSettings.imgWidth, TrainerSettings.syncHorizontal, TrainerSettings.syncVertical, TrainerSettings.mergeVal));
			float[][] m = p.processNetwork(iterations);
			if (Start.postProcessOnDisplay)
			{
				m = ImagePostprocessor.brightnessFilter(m, 0.15f);
				m = ImagePostprocessor.contrastFilter(m, 1.25f);
				m = ImagePostprocessor.FilterOut(m, 0.35f);
				m = ImagePostprocessor.Offset(m, 0, 2);
			}
		    for (int x = 0; x < TrainerSettings.imgWidth; x++)
		    {
		    	for (int y = 0; y < TrainerSettings.imgHeight; y++)
		    	{
		    		int rgbNum = (int) (m[x][y] * 255);
		    		g2D.setColor(new Color(rgbNum,rgbNum,rgbNum));
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
	  
	  
	  private static void printFilter(float[] filter)
	  {
		  System.out.print("[");
		  for (int i = 0; i < filter.length; i++)
		  {
			  System.out.print(filter[i] + ", ");
		  }
		  System.out.println("]");
	  }
	  
}
