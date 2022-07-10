package de.automata.neural.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.Pattern;
import de.automata.neural.test.Start;

public class ColoredTestGui extends JPanel {
	
	public ColoredTestGui() {
	    setBackground(Color.white);
	}
	int size = Start.windowSize / ColoredTrainerSettings.imgWidth;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
			float error = ColoredEvolutionaryPatternTrainer.processGenerationLearn();
			System.out.println();
			System.out.println("Generation: " + ColoredEvolutionaryPatternTrainer.generation);
			System.out.println("Error:      " + error );
			System.out.print("Filter:     ");
			printFilter(ColoredEvolutionaryPatternTrainer.population[0]);
			float[][][] filters = ColoredEvolutionaryPatternTrainer.getFiltersFromInputs(ColoredEvolutionaryPatternTrainer.population[0]);
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
			Thread.sleep(200);
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
