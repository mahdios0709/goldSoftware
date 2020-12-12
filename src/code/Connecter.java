package code;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author abdelfateh Bekkair
 */
public class Connecter {
    Connection con;

    /**
     *
     * @return
     */
    protected static Connection getConnection() {
        Connection con = null ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connecter.class.getName()).log(Level.SEVERE, null, ex);
               new DialogOption().DialogOptionERROR("probléme au dirver de connection jdbc","poblème de  dirver manager");
        }
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/gold?useSSL=false","root","akader01");

        
        } catch (SQLException ex) {
            Logger.getLogger(Connecter.class.getName()).log(Level.SEVERE, null, ex);
             new DialogOption().DialogOptionERROR("probléme de connection soit le lien soit le mot de passe de connection","problème de connexion");
            System.exit(0);
        }
      //  System.out.println("Connection Work Correctly.........");
       return con; 
}


}
