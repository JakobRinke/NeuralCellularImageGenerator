package de.automata.neural.base;

public class TrainerSettings {
	
	
	public static float startingrange = 0.7f;
	
	public static int popSize = 100;
	
	public static float mutationStrength = 0.005f;
	
	
	/* Absolut wichtig */
	public static float mergeVal = 0.6f;
	
	public static int iterations = 10;
	
	
	
	public static int checkNum = 50;
	
	public static boolean syncHorizontal = true;
	public static boolean syncVertical = false;

	
	
	public static int imgWidth = 16;
	public static int imgHeight = 16;
	
	
	public static String imgPath = "./input_imgs";
	
	
	public static float randomMutation()
	{
		return TrainerSettings.mutationStrength * TrainerSettings.mutationActivationFunction((float) (Math.random() * 2 - 1) );
	}
	
	public static float mutationActivationFunction(float x)
	{
		return (float) Math.tan(x);
	}
	

	
	
	
}
