/*
** Code adopted from -
**References: https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
 */
package fingerprint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class ConvertToGrayscale {

   private BufferedImage originalImage;
   private int width;
   private int height;
   
   public ConvertToGrayscale() {
   
      try {
        //read original image in & get width & height of image
         File input = new File("C:\\Users\\\\colin\\Desktop\\excellent1.bmp");
         originalImage = ImageIO.read(input);
         width = originalImage.getWidth();
         height = originalImage.getHeight();
         
         for(int i=0; i<height; i++){
         
            for(int j=0; j<width; j++){
            
               //get RGB values of image
               Color c = new Color(getOriginalImage().getRGB(j, i));
               
               //Y = 0.299 R + 0.587 G + 0.114 B Formula      
               int red = (int)(c.getRed() * 0.299);  
               int green = (int)(c.getGreen() * 0.587);    
               int blue = (int)(c.getBlue() *0.114);
              
               //Y = (R+R+R+B+G+G+G+G)>>3
               Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);    
               //sets the new rgb vales to the new image
               originalImage.setRGB(j,i,newColor.getRGB());
              
            }
         }
         
         File output = new File("C:\\Users\\colin\\Desktop\\grayscaled.jpg");
         ImageIO.write(originalImage, "bmp", output);
         System.out.println("New Grayscale imaged successfully created!!");
      } catch (Exception ex) {
            System.out.println(ex.toString());
        }
   }
   
   static public void main(String args[]) throws Exception 
   {
      ConvertToGrayscale obj = new ConvertToGrayscale();
   }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
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