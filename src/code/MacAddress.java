package code;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author AbdElFateh
 */

public class MacAddress {
    String getmac="";
        public  MacAddress() {
        try {
                InetAddress address = InetAddress.getLocalHost();

                /*
                * Get NetworkInterface for the current host and then read the
                * hardware address.
                */
                NetworkInterface ni = NetworkInterface.getByInetAddress(address);
                if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                        /*
                        * Extract each array of mac address and convert it to hexa with the
                        * following format 08-00-27-DC-4A-9E.
                        */
                        for (int i = 0; i < mac.length; i++) {
                       // System.err.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                        getmac=getmac+String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                        }

                } else {
                        System.out.println("Address doesn't exist or is not accessible.");
                       
                
                }
                } else {
                System.out.println("Network Interface for the specified address is not found.");
                }
        } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
        }
}


    public String encrypt() throws NoSuchAlgorithmException {
        //partie get mac
        String mac=new MacAddress().getmac;
      //  System.out.println(mac);

        //partie crypté mac
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode("fateh1"+mac));
        String macCrypted= String.format("%032x", new BigInteger(1, md5.digest()));
      //  System.out.println(macCrypted);
        return macCrypted;
        //partie get key from db (klé li 7na rana 3a6yinahleh) from db
        //wmenba3d ntesto lclé li rana 3a6yinahleh m3a da lclé macCrypted
        //hada makan
    }
    public String securePWS() throws NoSuchAlgorithmException {


        //partie crypté mac
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(encrypt()+"mahdi2"));
        String macCrypted= String.format("%032x", new BigInteger(1, md5.digest()));
        System.out.println(macCrypted);
        System.err.println(macCrypted);
        return macCrypted;
        //partie get key from db (klé li 7na rana 3a6yinahleh) from db
        //wmenba3d ntesto lclé li rana 3a6yinahleh m3a da lclé macCrypted
        //hada makan
    }
    Boolean checking()throws IOException{

    try (Connection con = Connecter.getConnection(); Statement st = con.createStatement();ResultSet rs = st.executeQuery("SELECT serialPc From serial")) {
     if (rs.next()) {
                // cheking  if pass change go to creation
                if(rs.getString(1).equals(securePWS())){
                    //lunch nrml
                    return false;
                }else{
                   // incorrect Pc

                    return true;
                }
            }else{
                // creation serial page
         securePWS();
                     return true;
            }

        } catch (SQLException e) {

            System.err.println(e);
            System.exit(1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    return false;
        }


}
