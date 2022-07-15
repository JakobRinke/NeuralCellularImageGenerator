package de.automata.neural.test;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternCreator;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.test.gui.FilterGUI256;
import de.automata.neural.test.gui.StackFastTest;

public class FilterTest256 {
	
	
	public static int windowSize = 512;
	
	public static int ImgSize = 256;
	
	
	public static int iterations1 = 10;
	public static int iterations2 = 8;
	public static int iterations3 = 8;
	
	public static float mergeVal = 0.6f;
	
	public static boolean postProcessOnDisplay = true;
	
	public static float[] filter1 = new float[] {-0.12502679f, -0.041955378f, 0.10660202f, 0.3658565f, 0.574643f, -0.019185163f  };
	public static float[] filter2 = new float[] {-0.020921621f, 0.38974252f, 0.08021445f, 0.35899952f, -0.30708167f, -0.06778304f};
	public static float[] filter3 = new float[] {0.31258392f, 0.22501153f, 0.11254549f, 0.24186954f, -0.40773484f, -0.21241178f};

	
//	public static float[] filter1 = new float[] {-0.14813802f, 0.075161316f, -0.06946134f, -0.38275325f, -0.15235305f, -0.1805578f };
//	public static float[] filter2 = new float[] {0.0056081545f, -0.38131356f, -0.3657626f, -0.34835002f, 0.2985774f, 0.38770685f};
//	public static float[] filter3 = new float[] {-0.1461729f, 0.31471065f, 0.3175825f, 0.6659986f, 0.08012462f, -0.5713932f};
//	
	public static float[] color1 = new float[] {0.3f, 0.3f, 1};
	public static float[] color2 = new float[] {0.3f, 1f, 1f};

	
	
	
	public static void main(String[] args) 
	{
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new FilterGUI256());
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
	}
	
	
	
	public static float[][] createMap() throws Exception
	{
		Pattern p1 = new Pattern(EvolutionaryPatternCreator.getFilterFromInputs(filter1));
		Pattern p2 = new Pattern(EvolutionaryPatternCreator.getFilterFromInputs(filter2));
		Pattern p3 = new Pattern(EvolutionaryPatternCreator.getFilterFromInputs(filter3));
		p1.setMap(MapCreator.createRandmap(ImgSize/16, true, false, mergeVal));
		p2.setMap(MapCreator.scaleMap(p1.processNetwork(iterations1), 4));
		p3.setMap(MapCreator.scaleMap(p2.processNetwork(iterations2), 4));
		return p3.processNetwork(iterations3);
	}
	
	
	
}
