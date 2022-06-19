package de.automata.neural.color;

public class ColoredTrainerSettings {
	
	
	public static float startingrange = 0.8f;
	
	public static int popSize = 100;
	
	public static float mutationStrength = 0.005f;
	
	public static float mergeVal = 0.6f;
	
	public static int iterations = 10;
	
	public static int checkNum = 100;
	
	public static boolean syncHorizontal = true;
	public static boolean syncVertical = false;

	
	
	public static int imgWidth = 16;
	public static int imgHeight = 16;
	
	
	public static String imgPath = "./input_imgs";
	
	
	
	
	public static float mutationActivationFunction(float x)
	{
		return (float) Math.tan(x);
	}
	

	
	
	
}
