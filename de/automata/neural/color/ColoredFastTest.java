package de.automata.neural.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.ImagePostprocessor;
import de.automata.neural.base.Pattern;
import de.automata.neural.base.TrainerSettings;
import de.automata.neural.test.Start;

public class ColoredFastTest extends JPanel {
	
	
	public static double[] inputD = {0.094125085, -0.045424677, -0.013423152, 0.3642465, -0.7685398, 0.35895503, 0.34982193, -0.66012275, -0.7480261, -0.25375018, 0.09784674, 0.5807371, -0.55533046, -0.50027996, 0.51067775, -0.7174592, -0.66667384, 0.7786421};
	public static float[] input;
	public static int iterations = 10;
	public static float mergeVal = 0.5f;
	
	
	public ColoredFastTest() {
	    setBackground(Color.white);
	    TrainerSettings.mergeVal = mergeVal;
	    input = new float[inputD.length];
	    for (int x = 0; x < inputD.length; x++)
	    	input[x] = (float) inputD[x];
	}
	int size = Start.windowSize / TrainerSettings.imgWidth;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
	    	float[][][] filters = ColoredEvolutionaryPatternTrainer.getFiltersFromInputs(input);
	    	Pattern pR = new Pattern(filters[0]);
			Pattern pG = new Pattern(filters[1]);
			Pattern pB = new Pattern(filters[2]);
			float map[][] = ColoredEvolutionaryPatternTrainer.createRandmap();
			pR.setMap(map);
			pG.setMap(map);
			pB.setMap(map);
			
			float[][] mR = pR.processNetwork(ColoredTrainerSettings.iterations);
			float[][] mG = pG.processNetwork(ColoredTrainerSettings.iterations);
			float[][] mB = pB.processNetwork(ColoredTrainerSettings.iterations);
			
			if (Start.postProcessOnDisplay)
			{
				mR = ImagePostprocessor.brightnessFilter(mR, 0.15f);
				mR = ImagePostprocessor.contrastFilter(mR, 1.2f);
				mR = ImagePostprocessor.FilterOut(mR, 0.4f);
				mR = ImagePostprocessor.Offset(mR, 0, 1);
				
				mB = ImagePostprocessor.brightnessFilter(mB, 0.15f);
				mB = ImagePostprocessor.contrastFilter(mB, 1.25f);
				mB = ImagePostprocessor.FilterOut(mB, 0.4f);
				mB = ImagePostprocessor.Offset(mB, 0, 1);
			}
			
			
		    for (int x = 0; x < ColoredTrainerSettings.imgWidth; x++)
		    {
		    	for (int y = 0; y < ColoredTrainerSettings.imgHeight; y++)
		    	{
		    		g2D.setColor(new Color( (int) (mR[x][y] * 255),
		    								(int) (mG[x][y] * 255),
		    								(int) (mB[x][y] * 255)));
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
