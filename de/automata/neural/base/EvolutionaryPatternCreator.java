package de.automata.neural.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvolutionaryPatternCreator {

	
	public float[][] population = new float[TrainerSettings.popSize][6];
	
	public float[][][] sampleImages;
	public float[] sampleBrightness;
	
	public int generation;
	
	
	public int imgSize; 
	protected int iterations;
	protected float mergeVal;
	
	public EvolutionaryPatternCreator(int imgSize,  int iterations, float mergeVal, float[][][] sampleImages)
	{
		this.imgSize = imgSize;
		this.mergeVal = mergeVal;
		this.iterations = iterations;
		this.sampleImages = sampleImages;
		this.sampleBrightness = new float[sampleImages.length];
		int j = 0;
		for (float[][] s : sampleImages)
		{
			this.sampleBrightness[j] = MapCreator.getAvgrBrightness(s);
			j++;
		}	
		setup();
	}
	
	

	
	public float[][] getFilterFromInputs(float[] inputs)
	{
		return new float[][] {{inputs[0], inputs[1], inputs[2]},
							  {inputs[3], inputs[4], inputs[5]},
							  {inputs[0], inputs[1], inputs[2]}};
	}

	
	public void setup()
	{
		generation = 0;
		generatePopulation();
	}
	
	
	
	public float[][] createMap() throws Exception
	{
		return MapCreator.createRandmap(imgSize, true, false, mergeVal);
	}
	
	private float[] mutateInput(float[] input)
	{
		float[] out = input.clone();
		for (int y = 0; y < 6; y++)
		{
			out[y] += TrainerSettings.randomMutation();
		}
		return out;
	}
	
	
	
	private void generatePopulation()
	{
		for (int i = 0; i < population.length; i++)
		{
			for (int j = 0; j < population[0].length; j++)
			{
				population[i][j] = (float) (Math.random() * 2 - 1) * TrainerSettings.startingrange;
			}
		}
	}
	
	private void generatePopulation(int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			for (int j = 0; j < population[0].length; j++)
			{
				population[i][j] = (float) (Math.random() * 2 - 1) * TrainerSettings.startingrange;
			}
		}
	}
	
	public static float[][][] loadSampleImgs(String path) throws IOException
	{
		float [][][] sampleimages;		
 		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		sampleimages = new float[listOfFiles.length][][];		
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  sampleimages[i] = MapCreator.fromImage(listOfFiles[i].getAbsolutePath());
		  } 
		}
		return sampleimages;
	}
	
	private float getErrorOfInput(float[] input) throws Exception
	{
		float error = 0;
		float[][] filter = getFilterFromInputs(input);
		Pattern p = new Pattern(filter);
		for (int i = 0; i < TrainerSettings.checkNum; i++)
		{
			p.setMap(createMap());
			error += getLowestDifference( p.processNetwork(iterations));
		}
		
		return error / TrainerSettings.checkNum;
	}
	
	
	
	private float getLowestDifference(float[][] map)
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
	
	public float[][] generateAPattern()
	{
		Pattern p = new Pattern(getFilterFromInputs(population[0]));
		try {
			p.setMap(createMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p.processNetwork(iterations);
	}
	
	
	private void createNewGeneration(float[] errorlevels)
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
	
	
	public float trainFor(int gerations) throws Exception
	{
		for (int i = 1; i < gerations; i++)
		{
			processGenerationLearn();
		}
		return processGenerationLearn();
	}
	
	
	public float trainUntil(float err) throws Exception
	{
		while (processGenerationLearn() > err){}
		return processGenerationLearn();
	}
	
	
	public float processGenerationLearn() throws Exception
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
	
	private float getMinValue(float[] numbers){
		float minValue = numbers[0];
		for(int i=1;i<numbers.length;i++){
			if(numbers[i] < minValue){
				minValue = numbers[i];
		    }
		 }
		return minValue;
	}
	
}
