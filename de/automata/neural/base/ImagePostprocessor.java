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
	
}
