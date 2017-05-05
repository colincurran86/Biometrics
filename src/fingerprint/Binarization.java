
/**
 * Image binarization - Otsu algorithm and Histogram
 *
 * Code adopted from: Bostjan Cigan (http://zerocool.is-a-geek.net) &
 * http://www.walrusvision.com/wordpress/otsu-thresholder-algorithm-works/
 *
 * separate the pixels into two clusters according to the threshold
 * find the mean of each cluster
 * square the difference between the means
 * multiply by the number of pixels in one cluster times the number in the other
 * 
 * To binarize our image, we will use Otsu’s method, which is a method created by Nobuyuki Otsu.
 * The algorithm assumes that the image to be thresholded contains two classes of pixels or bi-modal histogram 
 * (e.g. foreground and background) then calculates the optimum threshold separating those two classes so that
 * their combined spread (intra-class variance) is minimal. Its main advantages are speed (because we only use histograms
 * and arrays of length 256) and the easiness of the implementation.
 */

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.ZhangSuenThinning;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Binarization {

    private static BufferedImage image, original, grayscale, binarized;

    public static void main(String[] args) throws IOException {
        //load original image
        BufferedImage image = ImageIO.read(new File("C:\\Users\\colin\\Desktop\\1_8.png"));
   
        //convert original to grayscale
        grayscale = toGray(image);
        //binarize grayscale image
        binarized = binarize(image);
        
        //convert binarized image to FastBitmap format
        FastBitmap fb = new FastBitmap(binarized);
 
        //skeletonze image
        ZhangSuenThinning zs = new ZhangSuenThinning();
        zs.applyInPlace(fb);
        
        //display skeleton image
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(fb.toBufferedImage())));
        frame.setSize(400,400);
        frame.setVisible(true);
        
        
       /* byte[] pixels = ((DataBufferByte)binarized.getRaster().getDataBuffer()).getData();
        for(byte pixel : pixels){
            System.out.println("Pixel " + pixel);
        }*/
        
       //extract ridges
        //extractRidgeEndings(binarized);
        
        
    }//end main //////////////////////

    
    // The luminance method to covert to Grayscale
    //This is the same result as the ConvertToGrayscale class
    private static BufferedImage toGray(BufferedImage original) {
        int alpha, red, green, blue;
        int newPixel;
        System.out.println("GRAYSCALED!!");
        BufferedImage luminance = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int row = 0; row < original.getWidth(); row++) {
            for (int col = 0; col < original.getHeight(); col++) {
                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(row, col)).getAlpha(); //Returns the alpha component in the range 0-255.
             
                red = new Color(original.getRGB(row, col)).getRed();
                green = new Color(original.getRGB(row, col)).getGreen();
                blue = new Color(original.getRGB(row, col)).getBlue();

                red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
                // Return back to original format
                newPixel = colorToRGB(alpha, red, red, red);

                // Write pixels into image
                luminance.setRGB(row, col, newPixel);
            }
        }
        return luminance;
    }
    
    // Return histogram of grayscale image//calculates the maximum intensity on each pixel in the image
    public static int[] imageHistogram(BufferedImage input) {
        //Histogram array length of 256 0-255 grascale range
        int[] histogram = new int[256];

        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }

        for (int row = 0; row < input.getWidth(); row++) {
            for (int col = 0; col < input.getHeight(); col++) {
                int red = new Color(input.getRGB(row, col)).getRed();
                histogram[red]++;
            }
        }
        System.out.println("HISTOGRAM VALUE IS" + histogram);
        return histogram;
    }

    /*  Get binary threshold using Otsu's method
        separate the pixels into two clusters according to the threshold
        find the mean of each cluster
        square the difference between the means 
        multiply by the number of pixels in one cluster times the number in the other
    */
    
    private static int otsuTreshold(BufferedImage original) {
        //pixels seperated into 2 clusters according to the threshold 
        //find the mean of each Cluster
        //get histogram intensity values of original image pixels
        int[] histogram = imageHistogram(original); //calculates maximum intensity on each pixel
        int totalPixelsInImage = original.getHeight() * original.getWidth(); 
        
        //calculate the mean value for the overall Histogram
        float meanTotal = 0;
        for (int i = 0; i < 256; i++) {
            meanTotal += i * histogram[i];
        }

        // The probability of the first class occurrence
        float meanCurrent = 0;
        // The probability of the second class occurrence
        int backgroundWeight = 0;
        int foregroundWeight = 0;

        float maximumVariance = 0;
        int thresholdValue = 0;

        for (int k = 0; k < 256; k++) {
             // Compute background weight for current k
            backgroundWeight += histogram[k];
            if (backgroundWeight == 0) {
                continue;
            }
            // Compute foreground weight for current k
            //foreground = total - background
            foregroundWeight = totalPixelsInImage - backgroundWeight;

            if (foregroundWeight == 0) {
                break;
            }
            
            //steps through all possible maximum threshold intensitys
            meanCurrent += (float) (k * histogram[k]);
            
            // The probability of the first class occurrence
            float mean0 = meanCurrent / (float)backgroundWeight;
            
            //mean value for class1
            float mean1 = (meanTotal - meanCurrent) / foregroundWeight;
            
            // Calculate the between-class variance [sigmasquareB]
            float varianceBetween = (float) backgroundWeight * (float) foregroundWeight * (mean0 - mean1) * (mean0 - mean1);

            //if varBetween is greater than 0 then set the max threshold to the varBetween value
            if (varianceBetween > maximumVariance) {
                maximumVariance = varianceBetween;
                thresholdValue = k;
            }
        }
        System.out.println("THRESHOLD VALUE IS: " + thresholdValue);
        return thresholdValue;
    }

    private static BufferedImage binarize(BufferedImage original) {
        int red;
        int newPixel;
        System.out.println("BINARIZED");
        //gets otsu threshold of image which is 134
        int threshold = otsuTreshold(original);
        System.out.print("THRESHOLD " + threshold);

        BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int row = 0; row < original.getWidth(); row++) {
            for (int col = 0; col < original.getHeight(); col++) {

                // Get pixels
                red = new Color(original.getRGB(row, col)).getRed();
                //System.out.println("RED VALUE" + red);
                int alpha = new Color(original.getRGB(row, col)).getAlpha();
                if (red > threshold) { //if red is > 134 convert to 255 or 0
                    newPixel = 255;
                } else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(row, col, newPixel);
            }
        }
        System.out.println("Image successfully binarized");
        return binarized;
    }

    // Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel <<= 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }
    
    
    
    public static ArrayList<Point> extractRidgeEndings(BufferedImage binarizedImage) {

        System.out.println("Extract Method!");
        ArrayList<Point> p = new ArrayList<>();
     
        //int[][] imageDataInput = new int[imageDataInput.length][imageDataInput[0].length];
        
        for (int y = 0; y < binarizedImage.getHeight(); y++) {
           // System.out.println("Outer loop Y");
            for (int x = 0; x < binarizedImage.getWidth(); x++) {
               // System.out.println("Inner loop X");
              
                   // -1 = white
                   // -16777216 = black
                //System.out.println("Values at["+y+"]["+x+"] is " + imageDataInput[y][x]);
                /*if (imageDataInput[y - 1][x] == 0 && imageDataInput[y - 1][x + 1] == 1) {
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
                }*/
            }//end width loop
        }//end height loop
        
        for (int y = 0; y < binarizedImage.getHeight(); y++) {
            System.out.println("Outer loop Y");
            for (int x = 0; x < binarizedImage.getWidth(); x++) {
               // System.out.println("Inner loop X");
                System.out.println("BLAH: " + binarizedImage.getRGB(x, y));
                
                   // 0 = white
                   // -41 = black
                //System.out.println("Values at["+y+"]["+x+"] is " + imageDataInput[y][x]);
                /*if (imageDataInput[y - 1][x] == 0 && imageDataInput[y - 1][x + 1] == 1) {
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
                }*/
            }//end width loop
        }//end height loop

        System.out.println("Points found \n" + p);
        return p;
    }
}//end class
