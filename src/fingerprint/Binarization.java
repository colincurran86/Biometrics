
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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Binarization {

    private static BufferedImage original, grayscale, binarized;

    public static void main(String[] args) throws IOException {
        File inputImage = new File("C:\\Users\\colin\\Desktop\\grayscale.jpg");
        original = ImageIO.read(inputImage);
        //convert to grayscale
        grayscale = toGray(original);
        //binarize
        binarized = binarize(grayscale);

        File outputImage = new File("C:\\Users\\colin\\Desktop\\grayscaleToBinary.jpg");
        ImageIO.write(binarized, "jpg", outputImage);
    }//end main 

    
    // The luminance method to covert to Grayscale
    //This is the same result as the ConvertToGrayscale class
    private static BufferedImage toGray(BufferedImage original) {
        int alpha, red, green, blue;
        int newPixel;

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
        return thresholdValue;
    }

    private static BufferedImage binarize(BufferedImage original) {
        int red;
        int newPixel;

        //gets otsu threshold of image which is 134
        int threshold = otsuTreshold(original);

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
}//end class
