package de.automata.neural.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class EvolutionaryPatternTrainer {
	
	
	
	public static float[][] population = new float[TrainerSettings.popSize][6];
	
	public static float[][][] sampleImages;
	public static float[] sampleBrightness;
	
	public static int generation;
	
	public static float randomMutation()
	{
		return TrainerSettings.mutationStrength * TrainerSettings.mutationActivationFunction((float) (Math.random() * 2 - 1) );
	}

	
	
	public static float[][] getFilterFromInputs(float[] inputs)
	{
		return new float[][] {{inputs[0], inputs[1], inputs[2]},
							  {inputs[3], inputs[4], inputs[5]},
							  {inputs[0], inputs[1], inputs[2]}};
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
				population[i][j] = (float) (Math.random() * 2 - 1) * TrainerSettings.startingrange;
			}
		}
	}
	
	private static void generatePopulation(int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			for (int j = 0; j < population[0].length; j++)
			{
				population[i][j] = (float) (Math.random() * 2 - 1) * TrainerSettings.startingrange;
			}
		}
	}
	
	private static void loadInSampleImgs() throws IOException
	{
		File folder = new File("./input_imgs/");
		File[] listOfFiles = folder.listFiles();
		
		sampleImages = new float[listOfFiles.length][][];		
		sampleBrightness = new float[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    sampleImages[i] = MapCreator.fromImage(listOfFiles[i].getAbsolutePath());
		    sampleBrightness[i] = MapCreator.getAvgrBrightness(sampleImages[i]);
		  } 
		}
	}

	
	
	private static float getErrorOfInput(float[] input) throws Exception
	{
		float error = 0;
		float[][] filter = getFilterFromInputs(input);
		Pattern p = new Pattern(filter);
		for (int i = 0; i < TrainerSettings.checkNum; i++)
		{
			p.setMap(MapCreator.createRandmap(TrainerSettings.imgWidth, TrainerSettings.syncHorizontal, TrainerSettings.syncVertical, TrainerSettings.mergeVal));
			error += getLowestDifference( p.processNetwork(TrainerSettings.iterations));
		}
		
		return error / TrainerSettings.checkNum;
	}
	
	
	
	private static float getLowestDifference(float[][] map)
	{
		float dif = Float.MAX_VALUE;
		for (int i = 0; i < sampleImages.length; i++)
		{
			float newDif = (float) (Math.pow(MapCreator.compareMaps(map, sampleImages[i]), 3) * (0.1f + Math.abs(sampleBrightness[i]-MapCreator.getAvgrBrightness(map))));
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
