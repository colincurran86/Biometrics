package fingerprint;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Fingerprint{
  public static void main(String args[])throws IOException{
      
   BufferedImage img = ImageIO.read(new File("C:\\Users\\colin\\Desktop\\GoodImage.bmp"));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(400,400);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }//end main
  
  
  public void insertData(){
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/Student",
            "postgres", "v5v36jwd");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully!");

         stmt = c.createStatement();
         String sql = "INSERT INTO credentials (TNumber, Fingerprint) "
               + "VALUES (T00058011, BLOB );";
         stmt.executeUpdate(sql);

         stmt.close();
         c.commit();
         c.close();
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Records created successfully");
   }
  
}//end class
