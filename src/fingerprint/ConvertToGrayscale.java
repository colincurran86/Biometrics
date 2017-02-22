/*
** Code adopted from -
**References: https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
 */
package fingerprint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public final class ConvertToGrayscale {

    private BufferedImage originalImage;
    private int imageWidth;
    private int imageHeight;
    //Luminance values
    private float redValue = 0.299f;
    private float greenValue = 0.587f;
    private float blueValue = 0.114f;

    public ConvertToGrayscale() {

        try {
            //read original image in & get imageWidth & imageHeight of image
            File input = new File("C:\\Users\\\\colin\\Desktop\\test.jpg");
            originalImage = ImageIO.read(input);
            imageWidth = originalImage.getWidth();
            imageHeight = originalImage.getHeight();

            for (int i = 0; i < imageHeight; i++) {
                for (int j = 0; j < imageWidth; j++) {

                    //get RGB values of image
                    Color originalColor = new Color(getOriginalImage().getRGB(j, i));

                    //RGB Luminance formula = 0.3 Red + 0.59 Green + 0.11 Blue    
                    int red = (int) (originalColor.getRed() * redValue);
                    int green = (int) (originalColor.getGreen() * greenValue);
                    int blue = (int) (originalColor.getBlue() * blueValue);
                    
                    Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
                    //sets the new rgb vales to the new image
                    originalImage.setRGB(j, i, newColor.getRGB());
                }//end width
            }//end height

            File output = new File("C:\\Users\\colin\\Desktop\\grayscaled.jpg");
            ImageIO.write(originalImage, "bmp", output);
            System.out.println("New Grayscale imaged successfully created!!");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    static public void main(String args[]) throws Exception {
        ConvertToGrayscale obj = new ConvertToGrayscale();
    }

    /**
     * @return the imageWidth
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * @param imageWidth the imageWidth to set
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * @return the imageHeight
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * @param imageHeight the imageHeight to set
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * @return the originalImage
     */
    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    /**
     * @param originalImage the originalImage to set
     */
    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }
}
