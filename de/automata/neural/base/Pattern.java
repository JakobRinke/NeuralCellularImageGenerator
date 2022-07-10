package de.automata.neural.base;

public class Pattern {
	
	
	private float[][] filter;
	
	private float[][] map;
	
	
	public Pattern(float[][] filter)
	{
		this.filter = filter;
	}
	 
	
	public void setMap(float[][] map)
	{
		this.map = map;
	}
	
	
	public void generateMap(int width, int height)
	{
		float[][] newmap = new float[width][height];
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				newmap[x][y] = (float) Math.random();
			}
		}
		this.map = newmap;
	}
	
	
	/**
	 * Lässt das gesamte Netzwerk mit der aktuellen Map und mit n Glättungen laufen.
	 * @param number Anzahl der Glättungen
	 * @return neue map
	 */
	public float[][] processNetwork(int number)
	{
		int width = map.length,  height = map[0].length;
		float[][] newmap = map.clone();
		for (int i = 0; i < number; i++)
		{
			float[][] m = new float[width][height];
			for (int x = 0; x < width; x++)
			{
				int nX1 = (width + x-1)%width;
				int nX2 =  (x+1)%width;
				for (int y = 0; y <	newmap.length; y++)
				{			
					int nY1 = (height + y-1)%height;
					int nY2 =  (y+1)%height;
					m[x][y] += newmap[nX1][nY1] * filter[0][0];	
					m[x][y] += newmap[nX1][y] * filter[0][1];	
					m[x][y] += newmap[nX1][nY2] * filter[0][2];	
					m[x][y] += newmap[x][nY1] * filter[1][0];	
					m[x][y] += newmap[x][y] * filter[1][1];	
					m[x][y] += newmap[x][nY2] * filter[1][2];	
					m[x][y] += newmap[nX2][nY1] * filter[2][0];	
					m[x][y] += newmap[nX2][y] * filter[2][1];	
					m[x][y] += newmap[nX2][nY2] * filter[2][2];

					m[x][y] = activate(m[x][y]); 
				}
			}
			newmap = m;
		}
		
		return newmap;
	}
	
	
	
	private float activate(float x)
	{
		return Math.min(Math.max(0, activation(x)), 1);
	}
	
	private float activation(float x)
	{
//		return 4 * Math.abs(x*x*x*x*x - x*x*x*x + x*x*x- x*x);
		return Math.abs(x*x -1);
//		return  x*x;
//		return (float) (Math.exp(x*0.66) - 0.5f);
//		return (float) Math.cos(12*x)*0.5f+0.5f;
	}
	
	
	
	public float[][] getMap()
	{
		return map;
	}
	
	
	
	
}
