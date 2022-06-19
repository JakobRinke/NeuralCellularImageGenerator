package de.automata.neural.test;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternCreator;
import de.automata.neural.base.StackedPatternCreator;
import de.automata.neural.test.gui.TestGui;

public class StackTest {
	
	
	public static int windowSize = 700;
	
	public static String imgPath = "./Stacktest/";
			
			
	public static EvolutionaryPatternCreator baseLayer;
	public static StackedPatternCreator mainLayer;
	
	
	public static void main(String[] args)
	{
		
		
		
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new TestGui());
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
	    
	    
	    
	}
	
	
	
}
