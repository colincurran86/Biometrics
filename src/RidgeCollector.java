/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author t00058011
 */
public class RidgeCollector {

    public static void main(String[] args) throws IOException {

        //load pre-processed skeleton image & convert to BufferedImage 
        BufferedImage image = ImageIO.read(new File("C:\\Users\\colin\\Desktop\\ThinnedImage2.png"));
        int[][] imageDataInput = new int[image.getHeight()][image.getWidth()];
        
        
       // File file = new File("C:/Users/t00058011/Desktop/ThinnedImage.png");
        //BufferedImage originalImage = ImageIO.read(file);
        //System.out.println("Width " + originalImage.getWidth());
        //System.out.println("Height " + originalImage.getHeight());
       
        //then convert to byte array
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //ImageIO.write(originalImage, "jpg", baos);
        //byte[] imageInByte = baos.toByteArray();

        
        RidgeCollector.extractRidgeEndings(imageDataInput);

        

    }//end main

    public static ArrayList<Point> extractRidgeEndings(int imageDataInput[][]) {
        System.out.println("Made it here");
        ArrayList<Point> p = new ArrayList<>();
        int i = 0;
        int j = 0;

        //int[][] imageDataInput = new int[imageDataInput.length][imageDataInput[0].length];
        
        for (int y = -1; y <= imageDataInput.length -1; y++) {
            System.out.println("Outer loop");
            for (int x = 1; x <= imageDataInput[y].length -1; x++) {
                System.out.println("Inner loop");
                if (imageDataInput[y - 1][x] == 0 && imageDataInput[y - 1][x + 1] == 1) {
                    p.add(new Point(y - 1, x + 1));
                }
                if (imageDataInput[y - 1][x + 1] == 0 && imageDataInput[y][x + 1] == 1) {
                    p.add(new Point(y, x + 1));
                }
                if (imageDataInput[y][x + 1] == 0 && imageDataInput[y + 1][x + 1] == 1) {
                    p.add(new Point(y + 1, x + 1));
                }
                if (imageDataInput[y + 1][x + 1] == 0 && imageDataInput[y + 1][x] == 1) {
                    p.add(new Point(y + 1, x));
                }
                if (imageDataInput[y + 1][x] == 0 && imageDataInput[y + 1][x - 1] == 1) {
                    p.add(new Point(y + 1, x - 1));
                }
                if (imageDataInput[y + 1][x - 1] == 0 && imageDataInput[y][x - 1] == 1) {
                    p.add(new Point(y, x - 1));
                }
                if (imageDataInput[y][x - 1] == 0 && imageDataInput[y - 1][x - 1] == 1) {
                    p.add(new Point(y - 1, x - 1));
                }
                if (imageDataInput[y - 1][x - 1] == 0 && imageDataInput[y - 1][x] == 1) {
                    p.add(new Point(y - 1, x));
                }
            }//end width loop
        }//end height loop

        System.out.println("Points found \n" + p);
        return p;
    }

    /*
    private static int[][] convertTo2DArray(BufferedImage original) {

        final byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        final int width = original.getWidth();
        final int height = original.getHeight();
       // System.out.println("Width" + width);
        // System.out.println("Height " + height);

        int[][] result = new int[height][width];

        System.out.println("Conversion Method " + Arrays.toString(result));

        return result;
    }*/

}   //end class

/*
for (int x = 0; x < originalImage.getHeight(); x++) {
            for (int y = 0; y < originalImage.getWidth(); y++) {
                int pixel = originalImage.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                int outColor = (red << 16) | (green << 8) | blue;
                originalImage.setRGB(x, y, outColor); 
                
            }
        }
*/