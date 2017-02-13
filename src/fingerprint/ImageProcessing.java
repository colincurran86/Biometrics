package fingerprint;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.Grayscale;
import javax.swing.JOptionPane;

/**
 *
 * @author colin
 */
public class ImageProcessing {

    public static void main(String[] args) {

        //Load an existing image
        FastBitmap fb = new FastBitmap("C:\\Users\\colin\\Desktop\\today.jpg");

            // Convert to grayscale
            Grayscale g = new Grayscale();
            g.applyInPlace(fb);

            // Apply Bradley local threshold
            BradleyLocalThreshold bradley = new BradleyLocalThreshold();
            bradley.applyInPlace(fb);

            //Show the result
            JOptionPane.showMessageDialog(null, fb.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        }
}
