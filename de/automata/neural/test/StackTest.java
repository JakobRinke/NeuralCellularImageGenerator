package de.automata.neural.test;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternCreator;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.StackedPatternCreator;
import de.automata.neural.test.gui.StackTestGui;
import de.automata.neural.test.gui.TestGui;

public class StackTest {
	
	
	public static int windowSize = 700;
	
	public static int ImgSize = 64;
	
	public static String imgPath = "./stacktest/";
			
			
	public static EvolutionaryPatternCreator baseLayer;
	public static StackedPatternCreator mainLayer;
	
	public static int iterations1 = 10;
	public static int iterations2 = 8;
	
	public static float mergeVal = 0.6f;
	public static int baselayerTrainGens = 40;
	
	public static void main(String[] args)
	{
		float[][][] main_images = new float[0][][];
		try {
			main_images = EvolutionaryPatternCreator.loadSampleImgs(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		float[][][] base_images = new float[0][][];
		try {
			base_images = EvolutionaryPatternCreator.loadSampleImgs("./stacktest_base/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		baseLayer = new EvolutionaryPatternCreator(ImgSize/4, iterations1, mergeVal,base_images);
		
		mainLayer = new StackedPatternCreator(baseLayer, ImgSize, iterations2, main_images);
		
		System.out.println("Training Baselayer...");
		try {
			float err = baseLayer.trainFor(baselayerTrainGens);
			System.out.println("Baselayer Trained, Error: " + err);
			StackTestGui.printFilter(baseLayer.population[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new StackTestGui());
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
	    
	    
	    
	}
	
	
	
}
