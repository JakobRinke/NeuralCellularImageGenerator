package de.automata.neural.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapCreator {
	
	
	public static float[][] randomMap(int width, int height, boolean symetricVertical, boolean symetricHorizontal)
	{
		float[][] newmap = new float[width][height];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				float val = (float) Math.random();
				int newX = x;
				int newY = y;
				if (symetricVertical && x > width/2 - 1)
				{
					newX = width - x - 1;
				}
				if (symetricHorizontal && y > height/2 - 1)
				{
					newY = height - y - 1;
				}
				if (newX != x || newY != y)
				{
					val = newmap[newX][newY];
				}
				newmap[x][y] = val;
				
			}
		}
		return newmap;
	}
	
	
	
	public static float[][] circleMap(int width, int height) 
	{
		float[][] newmap = new float[width][height];
		float nVal = (float) Math.sqrt(width*width + height*height) / 2;
		float wVal = width/2-0.5f;
		float hVal = height/2-0.5f;
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				newmap[x][y] = 1 - (float) Math.sqrt((x-wVal)*(x-wVal) + (y-hVal)*(y-hVal)) / nVal;
			}
		}
		return newmap;
	}

	
	public static float[][] mergeMaps(float[][] map1, float[][] map2, float mergeVal) throws Exception
	{
		if (map1.length != map2.length || map1[0].length != map2[0].length)
		{
			throw new Exception("Map Merge Failed: Maps are not the same Dimension");
		}
		float[][] newmap = new float[map1.length][map1[0].length];
		float mergeVal2 = 1 - mergeVal;
		for (int y = 0; y < map1[0].length; y++)
		{
			for (int x = 0; x < map1.length; x++)
			{
				newmap[x][y] = map1[x][y] * mergeVal + map2[x][y] * mergeVal2;
			}
		}
		return newmap;	
	}
	

	public static float[][] createRandmap(int imgSize, boolean syncHor,boolean syncVer, float mergeVal) throws Exception
	{
		return MapCreator.mergeMaps(MapCreator.circleMap(imgSize, imgSize), 
				 MapCreator.randomMap(imgSize, imgSize, 
			 			  syncHor, syncVer),
				 mergeVal);
	}
	
	
	public static float[][] fromImage(String path) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(path));
		float[][] f = new float[img.getWidth()][img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				f[x][y] = (float) (img.getRGB(x, y)&0xff) / 255f ;
			}
		}
		return f;
	}
	
	public static float[][][] rgbFromImage(String path) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(path));
		float[][][] f = new float[img.getWidth()][img.getHeight()][];
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				int RGB  = img.getRGB(x, y);
				f[x][y] = new float[]{((RGB >> 16) & 255) / 255f,((RGB >> 8) & 255) / 255f, (RGB & 255) / 255f} ;
			}
		}
		return f;
	}
	
	public static float[][] scaleMap(float[][] map, float factor)
	{
		float[][] newmap = new float[map.length][map[0].length];
		for (int i = 0; i < newmap.length; i++)
		{
			for (int j = 0; j < newmap[0].length; j++)
			{
				newmap[i][j] = map[(int) (i*factor)][(int) (j*factor)];
			}
		}
		return newmap;
	}
	
	public static float[][][] scaleMaps(float[][][] map, float factor)
	{
		float[][][] newmaps = new float[map.length][map[0].length][map[0][0].length];
		for (int h = 0; h < newmaps.length; h++)
		{
			for (int i = 0; i < newmaps[0].length; i++)
			{
				for (int j = 0; j < newmaps[0][0].length; j++)
				{
					newmaps[h][i][j] = map[h][(int) (i*factor)][(int) (j*factor)];
				}
			}
		}
		
		return newmaps;
	}
	
	
	
	public static float compareMaps(float[][] map1, float[][] map2)
	{
		float out = 0;
		for (int y = 0; y < map1[0].length; y++)
		{
			for (int x = 0; x < map1.length; x++)
			{
				out += Math.abs(map1[x][y] - map2[x][y]);
			}
		}
		
		return out;
	}
	
	public static float compareMaps(float[][] map1, float[][][] map2, int k)
	{
		float out = 0;
		for (int y = 0; y < map1[0].length; y++)
		{
			for (int x = 0; x < map1.length; x++)
			{
				out += Math.abs(map1[x][y] - map2[x][y][k]);
			}
		}
		
		return out;
	}
	
	
	public static float getAvgrBrightness(float[][] map)
	{
		float out = 0;
		for (int y = 0; y < map[0].length; y++)
		{
			for (int x = 0; x < map.length; x++)
			{
				out += map[x][y];
			}
		}
		
		return out / (map.length * map[0].length);
	}
	
	public static float[] getAvgrColors(float[][][] map)
	{
		float[] out = new float[3];
		for (int y = 0; y < map[0].length; y++)
		{
			for (int x = 0; x < map.length; x++)
			{
				out[0] += map[x][y][0];
				out[1] += map[x][y][1];
				out[2] += map[x][y][2];
			}
		}
		out[0] =  out[0] / (map.length * map[0].length);
		out[1] =  out[1] / (map.length * map[0].length);
		out[1] =  out[2] / (map.length * map[0].length);
		return out;
	}
	
	
	
}
