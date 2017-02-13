package fingerprint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Fingerprint {

    public static void main(String args[]) throws IOException, SQLException {
        
        Connection c = null;
        Connection conn2 = null;
        PreparedStatement pst = null;
        

        String tNumber = "t00058011";
        String url = "jdbc:postgresql://localhost:5432/Student";
        String user = "postgres";
        String password = "v5v36jwd*";
        
        
        
        
        try {
            int id = 6;

            c = DriverManager.getConnection(url, user, password);

            File file = new File("C:\\Users\\colin\\Desktop\\GoodImage.bmp");
            try (FileInputStream fis = new FileInputStream(file)) {
                String stm = "INSERT INTO credentials(TNumber, Fingerprint) VALUES(?, ?)";
                pst = c.prepareStatement(stm);
                pst.setString(1, file.getName());
                pst.setBinaryStream(2, fis, (int)file.length());
                pst.setInt(1, id);
                //pst.setString(2, tNumber);
                pst.executeUpdate();
                
                pst.close();
            }

        } catch (SQLException ex) {
            System.out.println("Exception " + ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (c != null) {
                    c.close();
                }

            } catch (SQLException ex) {
                System.out.println("Exception " + ex);
            }
        }

    }//end main

    public void insert() throws SQLException, IOException {
        Connection c = null;
        File file = new File("C:\\Users\\colin\\Desktop\\GoodImage.bmp");
        try (FileInputStream fis = new FileInputStream(file);
                PreparedStatement ps = c.prepareStatement("INSERT INTO credentials VALUES (?, ?)")) {
            ps.setString(1, file.getName());
            ps.setBinaryStream(2, fis, (int) file.length());
            ps.executeUpdate();
            System.out.println("Image successfully inserted");
        }
    }

    //To use the bytea data 
    //type you should simply use the getBytes(), setBytes(), getBinaryStream(), or setBinaryStream() methods.
    /* public void retreive() {
        PreparedStatement ps = c.prepareStatement("SELECT img FROM images WHERE imgname = ?");
        ps.setString(1, "myimage.gif");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            byte[] imgBytes = rs.getBytes(1);
            // use the data in some way here
        }
        rs.close();
        ps.close();
    }*/
}//end class
