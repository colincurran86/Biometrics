
package fingerprint;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.AdaptiveContrastEnhancement;
import Catalano.Imaging.Filters.BinaryDilatation;
import Catalano.Imaging.Filters.BinaryErosion;
import Catalano.Imaging.Filters.BinaryOpening;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.CannyEdgeDetector;
import Catalano.Imaging.Filters.ExtractBoundary;
import Catalano.Imaging.Filters.FillHoles;
import Catalano.Imaging.Filters.FourierTransform;
import Catalano.Imaging.Filters.FrequencyFilter;
import Catalano.Imaging.Filters.Threshold;
import Catalano.Imaging.Filters.HistogramStretch;
import Catalano.Imaging.Filters.Sharpen;
import Catalano.Imaging.Filters.SobelEdgeDetector;
import Catalano.Imaging.Filters.ZhangSuenThinning;
import Catalano.Imaging.IProcessImage;
import javax.swing.JOptionPane;

/**
 *
 * @author colin curran
 */
public class Enhancement {

    public static void main(String[] args) {
        
        /*
        Load Image
        Grayscale
        Binarization
        Thinning - Skeletonisation
        
        Minutiae Extraction
        */
      
        //Load image in
        FastBitmap fb = new FastBitmap("C:\\Users\\colin\\Desktop\\fingerprint.jpg");
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Original Image", JOptionPane.PLAIN_MESSAGE);
        
    
        
        //Grayscale
        fb.toGrayscale();
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Grayscale Image", JOptionPane.PLAIN_MESSAGE);
        
        
       //Binarize
        Threshold t = new Threshold(165);  //0-255
        t.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Binary Image", JOptionPane.PLAIN_MESSAGE);
        
        //Extract Boundary from Image
        ExtractBoundary eb = new ExtractBoundary();
        eb.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Extract Image", JOptionPane.PLAIN_MESSAGE);
        
        //Thinning to 1 pixel width
        ZhangSuenThinning zs = new ZhangSuenThinning();
        zs.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "ZhangSuen Thinned Image", JOptionPane.PLAIN_MESSAGE);
        
    }
}
