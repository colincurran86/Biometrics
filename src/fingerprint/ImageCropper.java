/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerprint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author colin
 * image cropping class
 */
public class ImageCropper {

	public static void main(String[] args) {
		try {
			BufferedImage origImage = ImageIO.read(new File("C:\\Users\\colin\\Desktop\\ThinnedImage.png"));
			
			System.out.println("Original Dimensions: " + origImage.getWidth() + "x" + origImage.getHeight() + "y");

			BufferedImage croppedImage = origImage.getSubimage(10, 12, 220, 300);
			System.out.println("Cropped Dimensions: " + croppedImage.getWidth() + "x" + croppedImage.getHeight());

			File outputfile = new File("C:\\Users\\colin\\Desktop\\CroppedImage2.png");
                      
			ImageIO.write(croppedImage, "png", outputfile);

			System.out.println("Image cropped successfully: " + outputfile.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
