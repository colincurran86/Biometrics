/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerprint;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.FourierTransform;
import Catalano.Imaging.Filters.FrequencyFilter;
import Catalano.Imaging.Filters.Sharpen;
import javax.swing.JOptionPane;

/**
 *
 * @author colin
 */
public class Enhancement {

    public static void main(String[] args) {
        //load image in
        FastBitmap fb = new FastBitmap("C:\\Users\\colin\\Desktop\\excellent1.bmp");
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Original Image", JOptionPane.PLAIN_MESSAGE);
        
        //Sharpen
        Sharpen s = new Sharpen();
        s.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Sharpened Image", JOptionPane.PLAIN_MESSAGE);
        
        
        //convert to grayscale
        fb.toGrayscale();
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Grayscale Image", JOptionPane.PLAIN_MESSAGE);
        
        //BradleyLocalThreshold filter
        BradleyLocalThreshold brad = new BradleyLocalThreshold();
        brad.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Bradley Image", JOptionPane.PLAIN_MESSAGE);

        /*perform Fourier transform
        FourierTransform ft = new FourierTransform(fb);
        ft.Forward();
        fb = ft.toFastBitmap();
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Fourier Transform", JOptionPane.PLAIN_MESSAGE);

        FrequencyFilter ff = new FrequencyFilter(0, 60);
        ff.ApplyInPlace(ft);
        fb = ft.toFastBitmap();
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Frequency Filter", JOptionPane.PLAIN_MESSAGE);

        ft.Backward();
        fb = ft.toFastBitmap();
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);*/
    }

}
