package de.automata.neural.base;

public class ImagePostprocessor {
	
	
	
	public static float[][] contrastFilter(float[][] img, float Filter)
	{
		for(int i = 0; i < img.length; i++)
		{
			for(int j = 0; j < img[0].length; j++)
			{
				img[i][j] = Math.min(1, Math.max(0, img[i][j] * Filter));
			}
		}
		return img;
	}
	
	public static float[][]  brightnessFilter(float[][] img, float Filter)
	{
		for(int i = 0; i < img.length; i++)
		{
			for(int j = 0; j < img[0].length; j++)
			{
				img[i][j] = Math.min(1, Math.max(0, img[i][j] + Filter));
			}
		}
		return img;
	}
	
	public static float[][] FilterOut(float[][] img, float Filter)
	{
		for(int i = 0; i < img.length; i++)
		{
			for(int j = 0; j < img[0].length; j++)
			{
				if (img[i][j] < Filter)
				{
					img[i][j] = 0;
				}
			}
		}
		return img;
	}
	
	public static float[][] Offset(float[][] img, int OffsetX, int OffsetY)
	{
		float[][] m = new float[img.length][ img[0].length];
		for(int i = 0; i < img.length; i++)
		{
			for(int j = 0; j < img[0].length; j++)
			{
				int nX = i + OffsetX, nY = j + OffsetY;
				 if (nX<0)       {nX += img.length;  }
			else if (nX>=img.length)  {nX -= img.length;  }
				 if (nY<0)       {nY += img[0].length; }
			else if (nY>=img[0].length) {nY -= img[0].length; }
			
				m[i][j] += img[nX][nY];

			}
		}
		return m;
	}
	
	
	public static float[][] smoothInMap(float[][] map1)
	{
		float[][] map2 = new float[map1.length][map1[0].length];
		
		for (int y = 0; y < map1[0].length; y++)
		{
			for (int x = 0; x < map1.length; x++)
			{
				try 
				{
					float f[] = new float[] {map1[x-1][y-1], map1[x-1][y], map1[x-1][y+1], map1[x][y-1], map1[x][y], map1[x][y+1], map1[x+1][y-1], map1[x+1][y], map1[x+1][y+1]};
					
					if(map1[x][y] == 0 && countNonZero(f) >= 4)
					{
						map2[x][y] = count(f) / countNonZero(f);
					}
					else { map2[x][y] = map1[x][y]; }
				}
				catch (Exception e)
				{
					map2[x][y] = map1[x][y];
				}
			}
		}
		return map2;
	}
	
	public static float[][] smoothMap(float[][] map1)
	{
		float[][] map2 = new float[map1.length][map1[0].length];
		
		for (int y = 0; y < map1[0].length; y++)
		{
			for (int x = 0; x < map1.length; x++)
			{
				try 
				{
					float f[] = new float[] {map1[x-1][y-1], map1[x-1][y], map1[x-1][y+1], map1[x][y-1], map1[x][y], map1[x][y+1], map1[x+1][y-1], map1[x+1][y], map1[x+1][y+1]};
					
					if(countNonZero(f) >= 4)
					{
						map2[x][y] = count(f) / countNonZero(f);
					}
					else { map2[x][y] = map1[x][y]; }
				}
				catch (Exception e)
				{
					map2[x][y] = map1[x][y];
				}
			}
		}
		return map2;
	}
	
	
	public static float[][] useTan(float[][] map)
	{
		for (int y = 0; y < map[0].length; y++)
		{
			for (int x = 0; x < map.length; x++)
			{
				map[x][y] = (float) (Math.tanh(3*map[x][y]-1.5)/2 + 0.5f);
			}
		}
		return map;
	}
	
	
	
	
	private static float count(float[] f)
	{
		float l = 0;
		for (float k : f)
		{
			l += k;
		}
		return l;
	}
	
	private static int countNonZero(float[] f)
	{
		int l = 0;
		for (float k : f)
		{
			if (k!=0) {l++;};
		}
		return l;
	}
	
	
}
