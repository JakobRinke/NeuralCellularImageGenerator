package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.base.TrainerSettings;
import de.automata.neural.test.Start;

public class TestGui extends JPanel {
	
	public TestGui() {
	    setBackground(Color.white);
	}
	int size = Start.windowSize / TrainerSettings.imgWidth;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
			float error = EvolutionaryPatternTrainer.processGenerationLearn();
			System.out.println();
			System.out.println("Generation: " + EvolutionaryPatternTrainer.generation);
			System.out.println("Error:      " + error );
			System.out.print("Filter:     ");
			printFilter(EvolutionaryPatternTrainer.population[0]);
			
			Pattern p = new Pattern(EvolutionaryPatternTrainer.getFilterFromInputs(EvolutionaryPatternTrainer.population[0]));
			p.setMap(MapCreator.createRandmap(TrainerSettings.imgWidth, TrainerSettings.syncHorizontal, TrainerSettings.syncVertical, TrainerSettings.mergeVal));
			float[][] m = p.processNetwork(TrainerSettings.iterations);
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
