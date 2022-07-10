package de.automata.neural.test;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.automata.neural.base.EvolutionaryPatternCreator;
import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;
import de.automata.neural.test.gui.StackFastTest;

public class StackFilterTest {
	
	
	public static int windowSize = 700;
	
	public static int ImgSize = 64;
	
	
	public static int iterations1 = 10;
	public static int iterations2 = 8;
	
	public static float mergeVal = 0.6f;
	
	public static boolean postProcessOnDisplay = true;
	
	public static float[] filter1 = new float[] {-0.12502679f, -0.041955378f, 0.10660202f, 0.3658565f, 0.574643f, -0.019185163f  };
	public static float[] filter2 = new float[] {-0.020921621f, 0.38974252f, 0.08021445f, 0.35899952f, -0.30708167f, -0.06778304f};
	

//	public static float[] filter1 = new float[] {0.027768875f, 0.16801517f, 0.24921675f, 0.3069113f, 0.10190631f, -0.54471344f};
//	public static float[] filter2 = new float[] {-0.14025025f, 0.2759504f, -0.20799229f, 0.63247293f, -0.006818312f, 0.3299658f };
	
//	public static float[] filter1 = new float[] {0.23469786f, 0.215788f, -0.10102302f, -0.049599886f, -0.095998146f, 0.28180242f};
//	public static float[] filter2 = new float[] {-0.114222445f, 0.2570161f, 0.06391705f, 0.39748684f, -0.6950824f, 0.67140156f };
	
	public static float[] color1 = new float[] {0.3f, 0.3f, 1};
	public static float[] color2 = new float[] {0.3f, 1f, 1f};

	
	
	
	public static void main(String[] args) 
	{
		JFrame frame1 = new JFrame("2D Text");
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame1.getContentPane().add("Center", new StackFastTest());
	    frame1.pack();
	    frame1.setSize(new Dimension(windowSize , windowSize));
	    frame1.setVisible(true);
	}
	
	
	
	public static float[][] createMap() throws Exception
	{
		Pattern p1 = new Pattern(EvolutionaryPatternCreator.getFilterFromInputs(filter1));
		Pattern p2 = new Pattern(EvolutionaryPatternCreator.getFilterFromInputs(filter2));
		p1.setMap(MapCreator.createRandmap(ImgSize/4, true, false, mergeVal));
		p2.setMap(MapCreator.scaleMap(p1.processNetwork(iterations1), 4));
		return p2.processNetwork(iterations2);
	}
	
	
	
}
