
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class TestBlackAndWhite {

    public static void main(String[] args) {
        new TestBlackAndWhite();
    }

    public TestBlackAndWhite() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    System.out.println("Exception"+ ex.toString());
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane(TestBlackAndWhite.this));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }

}