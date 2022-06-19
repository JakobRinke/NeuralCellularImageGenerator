package de.automata.neural.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.automata.neural.base.EvolutionaryPatternTrainer;
import de.automata.neural.base.TrainerSettings;

public class GenerationsResearch {
	
	
	public static float mergeValConst = 1.0f;
	
	public static String OutputFilename = "_research/generation_research.csv";
	
	public static int endGen = 31;
	
	
	
	public static void main(String[] args)
	{
		
		TrainerSettings.mergeVal = mergeValConst;
		
		
		 BufferedWriter bw = loadWriter();
		
		for (int k = 0; k < 30; k++)
		{
			System.out.println("Test " + k + " starting...");
				for (int i = 1; i < endGen; i++)
				{
					TrainerSettings.iterations = i;
					EvolutionaryPatternTrainer.setup();
					float error = 0;
					for (int j = 0; j < i; j++)
					{
						try {
							error = EvolutionaryPatternTrainer.processGenerationLearn();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println(Float.toString(error).replace(".", ","));
					try {
						bw.write(Float.toString(error).replace(".", ",") + "\n");
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		}
		
		
		
		
	}
	
	
	public static BufferedWriter loadWriter()
	{
		try {
			return new BufferedWriter(new FileWriter(new File(OutputFilename)));		
		} catch (IOException e) {
			try {
				new File(OutputFilename).createNewFile();
			} catch (IOException e1) {
				return null;
			}
			return loadWriter();
		}
	}
	
	
	
	
	
}
