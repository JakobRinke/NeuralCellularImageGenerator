package de.automata.neural.test.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.automata.neural.test.StackTest;
import de.automata.neural.test.Test256;

public class Gui256 extends JPanel {
	

	public Gui256() {
	    setBackground(Color.white);
	}
	
	int size = Test256.windowSize / Test256.ImgSize;
	
	public void paint(Graphics g) {
	    Graphics2D g2D;
	    g2D = (Graphics2D) g;
	    
	    try {
			float error = Test256.layer256.processGenerationLearn();
			
			System.out.println("Generation: " + Test256.layer256.generation);
			System.out.println("Error:      " + error );
//			System.out.print("Filter1:     ");		
//			printFilter(StackTest.baseLayer.population[0]);
			System.out.print("Filter2:     ");		
			printFilter(Test256.layer256.population[0]);
			System.out.println();
			
			
			float[][] m = Test256.layer256.generateAPattern();
		    for (int x = 0; x < Test256.ImgSize; x++)
		    {
		    	for (int y = 0; y < Test256.ImgSize; y++)
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
