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
		float[][] newmap = map.clone();
		int width = newmap.length,  height = newmap[0].length;
		for (int i = 0; i < number; i++)
		{
			float[][] m = new float[width][height];
			for (int x = 0; x < map.length; x++)
			{
				for (int y = 0; y < map[0].length; y++)
				{
					for (int k = -1; k< 2; k++)
					{
						for (int j = -1; j < 2; j++)
						{
							
							int nX = x + k, nY = y + j;
								 if (nX<0)       {nX += width;  }
							else if (nX>=width)  {nX -= width;  }
								 if (nY<0)       {nY += height; }
							else if (nY>=height) {nY -= height; }
							
							m[x][y] += newmap[nX][nY] * filter[k+1][j+1];	
						}
					}
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
		return  (float) Math.exp(x*0.66f)-1f;	
	}
	
	
	
	public float[][] getMap()
	{
		return map;
	}
	
	
	
	
}
