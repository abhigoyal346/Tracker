/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Abhishek Goyal
 */
public class Utilities {

    public static void showMsg(Component c, String msg, int flag) {
        if (flag == 0) {
            JOptionPane.showMessageDialog(c, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
        } else if (flag == 1) {
            JOptionPane.showMessageDialog(c, msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(c, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setImage(JLabel lbImg, String path) {
        ImageIcon imgcon = new ImageIcon(path);
        lbImg.setIcon(new ImageIcon(imgcon.getImage().getScaledInstance(811, 407, Image.SCALE_SMOOTH)));
    }

    public static void setImageEmployee(JLabel lbImg, String path) {
        ImageIcon imgcon = new ImageIcon(path);
        lbImg.setIcon(new ImageIcon(imgcon.getImage().getScaledInstance(687, 389, Image.SCALE_SMOOTH)));
    }

    public static void setProfilePic(JLabel lbImg, String path) {
        ImageIcon imgcon = new ImageIcon(path);
        lbImg.setIcon(new ImageIcon(imgcon.getImage().getScaledInstance(148, 148, Image.SCALE_SMOOTH)));
    }

    public static String screenshotStoreDrive() {
        return "E:\\";
    }

    public static String serverAddress() {
        return ("127.0.0.1");
    }

    public static int portAddress() {
        return (12445);
    }

    public static String serverFileLocation() {
        String loc = "C:\\Users\\Abhishek\\Documents\\TrackerData\\ScreenShots\\";
        return loc;
    }

    public static String serverProfPhotoLocation() {
        String loc = "C:\\Users\\Abhishek\\Documents\\TrackerData\\Images\\";
        return loc;
    }

    public static String clientProfilePhotoLocation() {
        String loc = "C:\\Users\\Abhishek\\Downloads\\TrackerData";
        return loc;
    }
    public static String EmployeeScreenShotsLocEmployerSide(){
        String loc = "C:\\Users\\Abhishek\\Downloads\\TrackerData\\ScreenShots\\";
        return loc;
    }
    public static String getEmailId(){
        //Your Eamil ID for password Reset
        String email=null;
        return email;
    }
    public static String getPassword(){
        //enter password 
        String password=null;
        return password;
    }
}
