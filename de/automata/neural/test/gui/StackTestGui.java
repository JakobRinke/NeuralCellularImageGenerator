package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.base.TrainerSettings;
import de.automata.neural.test.StackTest;
import de.automata.neural.test.Start;

public class StackTestGui extends JPanel{
	
	
	public StackTestGui() {
	    setBackground(Color.white);
	}
	
	int size = StackTest.windowSize / 64;
	
	  public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
			float error = StackTest.mainLayer.processGenerationLearn();
			
			System.out.println("Generation: " + StackTest.mainLayer.generation);
			System.out.println("Error:      " + error );
			System.out.print("Filter:     ");		
			System.out.println();
			
			printFilter(StackTest.mainLayer.population[0]);
			
			float[][] m = StackTest.mainLayer.generateAPattern();
		    for (int x = 0; x < StackTest.ImgSize; x++)
		    {
		    	for (int y = 0; y < StackTest.ImgSize; y++)
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
	  
	  
	  public static void printFilter(float[] filter)
	  {
		  System.out.print("[");
		  for (int i = 0; i < filter.length; i++)
		  {
			  System.out.print(filter[i] + ", ");
		  }
		  System.out.println("]");
	  }
	  
}
