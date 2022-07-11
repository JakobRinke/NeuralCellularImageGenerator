package de.automata.neural.test;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternCreator;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.StackedPatternCreator;
import de.automata.neural.test.gui.Gui256;
import de.automata.neural.test.gui.StackTestGui;

public class Test256 {
	
	
	public static int windowSize = 512;
	

	public static int ImgSize = 256;
	
	public static String imgPath = "./256_Test/";
			
			
	public static EvolutionaryPatternCreator layer16;
	public static StackedPatternCreator layer64;
	public static StackedPatternCreator layer256;
	
	public static int iterations1 = 10;
	public static int iterations2 = 8;
	public static int iterations3 = 8;
	
	
	public static float mergeVal = 0.6f;
	public static int baselayerTrainGens = 100;
	public static int baselayerTrainGens2 = 100;
	
	
	public static float[] filter1 = new float[] {-0.12502679f, -0.041955378f, 0.10660202f, 0.3658565f, 0.574643f, -0.019185163f  };
	public static float[] filter2 = new float[] {-0.020921621f, 0.38974252f, 0.08021445f, 0.35899952f, -0.30708167f, -0.06778304f};
	
	public static void main(String[] args) {
		
		float[][][] images256 = new float[0][][];
		try {
			images256 = EvolutionaryPatternCreator.loadSampleImgs(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		float[][][] images64 = new float[0][][];
		images64 = MapCreator.scaleMaps(images256, 0.25f);
		
		float[][][] images16 = new float[0][][];
		images16 = MapCreator.scaleMaps(images64, 0.25f);
		
		layer16 = new EvolutionaryPatternCreator(ImgSize/16, iterations1, mergeVal,images16);
		
		layer64 = new StackedPatternCreator(layer16, ImgSize/4, iterations2, images64);
		
		layer256 = new StackedPatternCreator(layer64, ImgSize, iterations2, images256);
		
		layer16.population[0] = filter1;
		layer64.population[0] = filter2;
		
//		System.out.println("Training Layer16...");
//		try {
//			float err = layer16.trainFor(baselayerTrainGens);
//			System.out.println("Layer16 Trained, Error: " + err);
//			StackTestGui.printFilter(layer16.population[0]);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("\nTraining Layer64...");
//		try {
//			float err = layer64.trainFor(baselayerTrainGens);
//			System.out.println("Layer64 Trained, Error: " + err);
//			StackTestGui.printFilter(layer64.population[0]);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("\n");
		
		
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new Gui256());
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
		
	}
	
	
}
