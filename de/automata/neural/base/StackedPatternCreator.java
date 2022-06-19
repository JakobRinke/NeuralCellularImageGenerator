package de.automata.neural.base;

public class StackedPatternCreator extends EvolutionaryPatternCreator {

	
	
	private float factor;
	
	private EvolutionaryPatternCreator base;
	
	public StackedPatternCreator(EvolutionaryPatternCreator base, int imgsize,int iterations, float[][][] images)
	{
		super(imgsize,iterations, 0.0f, images);
		this.base = base;
		this.factor = (float) imgsize / base.imgSize;
	}
	
	
	@Override
	public float[][] createMap() throws Exception
	{
		return MapCreator.scaleMap(base.generateAPattern(), factor);
	}
	
	
	
}
