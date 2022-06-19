package de.automata.neural.color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.automata.neural.base.MapCreator;
import de.automata.neural.base.Pattern;

public class ColoredEvolutionaryPatternTrainer {
	
	
	
	public static float[][] population = new float[ColoredTrainerSettings.popSize][18];
	
	public static float[][][][] sampleImages;
	public static float[][] sampleBrightness;
	
	public static int generation;
	
	public static float randomMutation()
	{
		return ColoredTrainerSettings.mutationStrength * ColoredTrainerSettings.mutationActivationFunction((float) (Math.random() * 2 - 1) );
	}

	
	
	public static float[][][] getFiltersFromInputs(float[] inputs)
	{
		return new float[][][]	{  {{inputs[0], inputs[1], inputs[2]},
							  		{inputs[3], inputs[3], inputs[3]},
							  		{inputs[0], inputs[1], inputs[2]}},
								   {{inputs[6], inputs[7], inputs[8]},
								  	{inputs[9], inputs[10], inputs[11]},
								  	{inputs[6], inputs[7], inputs[8]}},
								   {{inputs[12], inputs[13], inputs[14]},
									{inputs[15], inputs[16], inputs[17]},
									{inputs[12], inputs[13], inputs[14]}},
								};
	}

	
	public static void setup()
	{
		generation = 0;
		generatePopulation();
		try {
			loadInSampleImgs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static float[] mutateInput(float[] input)
	{
		float[] out = input.clone();
		for (int y = 0; y < 6; y++)
		{
			out[y] += randomMutation();
		}
		return out;
	}
	
	
	
	private static void generatePopulation()
	{
		for (int i = 0; i < population.length; i++)
		{
			for (int j = 0; j < population[0].length; j++)
			{
				population[i][j] = (float) (Math.random() * 2 - 1) * ColoredTrainerSettings.startingrange;
			}
		}
	}
	
	private static void generatePopulation(int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			for (int j = 0; j < population[0].length; j++)
			{
				
				population[i][j] = (float) (Math.random() * 2 - 1) * ColoredTrainerSettings.startingrange;
				
			}
		}
	}
	
	private static void loadInSampleImgs() throws IOException
	{
		File folder = new File("./input_imgs_rgb/");
		File[] listOfFiles = folder.listFiles();
		
		sampleImages = new float[listOfFiles.length][][][];		
		sampleBrightness = new float[listOfFiles.length][];
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    System.out.println("Input File found:  " + listOfFiles[i].getName());
		    sampleImages[i] = MapCreator.rgbFromImage(listOfFiles[i].getAbsolutePath());
		    sampleBrightness[i] = MapCreator.getAvgrColors(sampleImages[i]);
		  } 
		}
	}
	
	
	public static float[][] createRandmap() throws Exception
	{
		return MapCreator.mergeMaps(MapCreator.circleMap(ColoredTrainerSettings.imgWidth, ColoredTrainerSettings.imgHeight), 
				 MapCreator.randomMap(ColoredTrainerSettings.imgWidth, ColoredTrainerSettings.imgHeight, 
						 ColoredTrainerSettings.syncHorizontal, ColoredTrainerSettings.syncVertical),
				 ColoredTrainerSettings.mergeVal);
	}
	
	
	
	private static float getErrorOfInput(float[] input) throws Exception
	{
		float error = 0;
		float[][] filterR = getFiltersFromInputs(input)[0];
		float[][] filterG = getFiltersFromInputs(input)[1];
		float[][] filterB = getFiltersFromInputs(input)[2];
		
		for (int i = 0; i < ColoredTrainerSettings.checkNum; i++)
		{
			Pattern pR = new Pattern(filterR);
			Pattern pG = new Pattern(filterG);
			Pattern pB = new Pattern(filterB);
			float[][] map = createRandmap();		
			pR.setMap(map);
			pG.setMap(map);
			pB.setMap(map);	
			error += getLowestDifference(pR.processNetwork(ColoredTrainerSettings.iterations), 0);
			error += getLowestDifference(pG.processNetwork(ColoredTrainerSettings.iterations), 1);
			error += getLowestDifference(pB.processNetwork(ColoredTrainerSettings.iterations), 2);
			
		}
		return error / ColoredTrainerSettings.checkNum;
	}
	
	
	
	private static float getLowestDifference(float[][] map, int color)
	{
		float dif = Float.MAX_VALUE;
		for (int i = 0; i < sampleImages.length; i++)
		{
			float newDif = (float) (Math.pow(MapCreator.compareMaps(map, sampleImages[i], color), 3) * (0.1f + Math.abs(sampleBrightness[i][color]-MapCreator.getAvgrBrightness(map))));
			if (newDif<dif)
			{
				dif = newDif;
			}
		}	
		return dif;
	}
	
	
	private static void createNewGeneration(float[] errorlevels)
	{
		  final List<float[]> stringListCopy = Arrays.asList(population);
		    ArrayList<float[]> sortedList = new ArrayList(stringListCopy);
		    Collections.sort(sortedList, Comparator.comparing(s -> errorlevels[stringListCopy.indexOf(s)]));
		  for (int i = 0; i < population.length/4; i++)
		  {
			  population[i] = sortedList.get(i);
		  }
		  for (int i = population.length/4; i < population.length/2; i++)
		  {
			  population[i] = mutateInput(sortedList.get(i - population.length/4));
		  }
		  generatePopulation(population.length/2, population.length);
	}
	
	
	public static float processGenerationLearn() throws Exception
	{
		generation++;
		float[] errors = new float[population.length];
		for (int i = 0; i < population.length; i++)
		{
			errors[i] = getErrorOfInput(population[i]);
		}
		createNewGeneration(errors);
		return getMinValue(errors);
	}
	
	private static float getMinValue(float[] numbers){
		float minValue = numbers[0];
		  for(int i=1;i<numbers.length;i++){
		    if(numbers[i] < minValue){
		      minValue = numbers[i];
		    }
		  }
		  return minValue;
		}
	
	
}
