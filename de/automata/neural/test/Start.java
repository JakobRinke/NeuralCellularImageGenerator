package de.automata.neural.test;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.color.ColoredEvolutionaryPatternTrainer;
import de.automata.neural.color.ColoredFastTest;
import de.automata.neural.color.ColoredTestGui;
import de.automata.neural.test.gui.FastTest;
import de.automata.neural.test.gui.TestGui;

public class Start {
	
	
	public static int windowSize = 700;
	
	public static boolean postProcessOnDisplay = true;

	
	public static void main(String[] args)
	{
		
		
		EvolutionaryPatternTrainer.setup();
		ColoredEvolutionaryPatternTrainer.setup();
		
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new TestGui());
//	    frame1.getContentPane().add("Center", new FastTest());
	    
//	    frame1.getContentPane().add("Center", new ColoredTestGui());
//	    frame1.getContentPane().add("Center", new ColoredFastTest());
	    
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
	}
	
	
	public static void printMap(float[][] Map)
	{
		for (float[] fs : Map) {
			for (float fs2 : fs) {
				System.out.print(fs2 + "     ");
			}
			System.out.println();
		}
	}
	
	
}
