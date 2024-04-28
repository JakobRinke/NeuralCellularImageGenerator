package de.automata.neural.postprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class PostPostProcessor {

    public static void main(String[] args) {
        // Go trough all images in the folde 256_Test/ships_filtered
        // and apply the post processing to them
        // Save the images in the folder 256_Test/ships_filtered_postprocessed

        File folder = new File("256_Test/ships_filtered");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                postProcessImage(file.getAbsolutePath());
            }
        }
    }


    public static void postProcessImage(String path)
    {
        // Load the image from the path
        // Apply the post processing
        // Save the image to the path
        BufferedImage image = null;

        try {
            image = javax.imageio.ImageIO.read(new File(path));
            float[][] map = greyScaleMap(image);
            image = postProcess(map);
            String p = "./256_Test/ships_filtered_postprocessed/" + path.substring(path.lastIndexOf("/") + 1);
            File f = new File(p);
            javax.imageio.ImageIO.write(image, "png", f);
        } catch (FileNotFoundException e) {
            File f = (new File("./256_Test/ships_filtered_postprocessed"));
            f.mkdirs();
            e.printStackTrace();
            postProcessImage(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float[][] greyScaleMap(BufferedImage image) {
        float[][] map = new float[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++)  {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int b = (rgb & 0xFF);
                map[x][y] =  b / 255.0f;
            }
        }
        return map;
    }

    public static BufferedImage unMap(float[][] map) {
        BufferedImage image = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < map.length; x++)
        {
            for (int y = 0; y < map[0].length; y++)
            {
                int b = (int) (map[x][y] * 255);
                int rgb = (b << 16) | (b << 8) | b;
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }

    public static BufferedImage postProcess(float[][] map) {
        // Apply the post processing to the map
        // Return the image

        for (int i = 4; i <= 6; i++) {
            for (int j = 0; j < 3; j++) {
                map = smoothOutDark(map, i);
            }
        }

        for (int i = 0; i < 30; i++) {
            map = smoothAllDark(map, 0.5f);
        }
        smoothStructure(map, 5);
        smoothStructure(map, 5);
        smoothStructure(map, 5);
        smoothStructure(map, 4);
        lightUp(map, 1.35f);

        return unMap(map);
    }


    public static float lightUp(float[][] map, float value) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == 0) {
                    continue;
                }
                if (map[x][y] > dark) {
                    map[x][y] = Math.min(map[x][y] * value, 1);
                }
            }
        }
        return 0;
    }


    public static float dark = 0.5f;
    public static int countDarkNeigh(float[][] map, int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++)  {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (map[x + i][y + j] < dark) {
                        count++;
                    }
                }
                catch (Exception e)
                {
                    // Do nothing
                }
            }
        }
        return count;
    }

    public static float avgrDarkNeighValue(float[][] map, int x, int y) {
        float sum = 0;
        int count = 0;
        for (int i = -1; i <= 1; i++)  {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (map[x + i][y + j] < dark && map[x + i][y + j] != 0) {
                        sum += map[x + i][y + j];
                        count++;
                    }
                }
                catch (Exception e)
                {
                    // Do nothing
                }
            }
        }
        return sum / count;
    }

    public static float[][] smoothOutDark(float[][] map, int sensitivity)  {
        float[][] newMap = new float[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {

            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == 0) {
                    continue;
                }

                if (countDarkNeigh(map, x, y) >= sensitivity  && map[x][y] > dark)
                {
                    newMap[x][y] = avgrDarkNeighValue(map, x, y);
                } else {
                    newMap[x][y] = map[x][y];
                }
            }
        }

        return newMap;

    }


    public static float[][] smoothAllDark(float[][] map, float smoothingStrength) {
        float[][] newMap = new float[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == 0) {
                    continue;
                }
                if (map[x][y] < dark) {
                    newMap[x][y] = avgrDarkNeighValue(map, x, y) * smoothingStrength + map[x][y]  * (1 - smoothingStrength);
                } else {
                    newMap[x][y] = map[x][y];
                }
            }
        }
        return newMap;
    }

    public static float[][] smoothStructure(float[][] map, int sensitivity) {
        float[][] newMap = new float[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] != 0) {
                    newMap[x][y] = map[x][y];
                }
                if (countDarkNeigh(map, x, y) >= sensitivity) {
                    newMap[x][y] = avgrDarkNeighValue(map, x, y);
                }
            }
        }
        return newMap;
    }

}
