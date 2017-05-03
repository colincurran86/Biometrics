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
        BufferedImage image = ImageIO.read(new File("C:\\Users\\t00058011\\Desktop\\ThinnedImage.png"));
        //convert to 2D matrix
        int[][] imageDataInput = new int[image.getHeight()][image.getWidth()];
        
        
        RidgeCollector.extractRidgeEndings(imageDataInput);


    }//end main

    public static ArrayList<Point> extractRidgeEndings(int imageDataInput[][]) {
        System.out.println("Extract Method!");
        ArrayList<Point> p = new ArrayList<>();
     
        //int[][] imageDataInput = new int[imageDataInput.length][imageDataInput[0].length];
        
        for (int y = -1; y < imageDataInput.length; y++) {
            System.out.println("Outer loop Y");
            for (int x = 0; x < imageDataInput[y].length; x++) {
                //System.out.println("Inner loop X");
                System.out.println("Values are["+y+"]["+x+"] is "+imageDataInput[y][x]);
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

   

}   //end class
