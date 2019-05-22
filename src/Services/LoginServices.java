/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.LoginBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Abhishek Goyal
 */
public class LoginServices {

    public static LoginBean authenticateUser(String un, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select loginid,password,type,status,email,name,contact,dob,gender from loginmaster where username=?");
            pstmt.setString(1, un);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (pwd.equals(rs.getString("password"))) {
                    LoginBean objbean = new LoginBean();
                    objbean.setLogin_id(rs.getInt("loginid"));
                    objbean.setType(rs.getString("type"));
                    objbean.setStatus(rs.getBoolean("status"));
                    objbean.setEmail(rs.getString("email"));
                    objbean.setName(rs.getString("name"));
                    objbean.setContact(rs.getString("contact"));
                    objbean.setDob(rs.getString("dob"));
                    objbean.setGender(rs.getBoolean("gender"));
                    objbean.setUser_name(un);
                    objbean.setPassword(pwd);
                    return objbean;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception of aunthenticateUser:" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of aunthenticateUser:" + e);
            }

        }
        return null;
    }

    public static boolean checkMail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select email from loginmaster where email=?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception of checkMail():" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of checkMail():" + e);
            }

        }
        return false;
    }

    public static void setDefaultPassword(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update loginmaster set password=? where email=?;");
            pstmt.setString(1, "cgc");
            pstmt.setString(2, email);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception of setDefaultPassword():" + e);
        } finally {
            try {

                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of setDefaultPassword():" + e);
            }

        }

    }

}
