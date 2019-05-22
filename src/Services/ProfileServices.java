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
public class ProfileServices {
    public static LoginBean getUserProfile(int user_id){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        LoginBean objbean=null;
        try {
            conn=DBConnection.connect();
            pstmt=conn.prepareStatement("select username,password,name,dob,contact,gender,email from loginmaster where loginid=?");
            pstmt.setInt(1,user_id);
            rs=pstmt.executeQuery();
            if(rs.next()){
                objbean=new LoginBean();
                objbean.setUser_name(rs.getString("username"));
                objbean.setLogin_id(user_id);
                objbean.setName(rs.getString("name"));
                objbean.setPassword(rs.getString("password"));
                objbean.setGender(rs.getBoolean("gender"));
                objbean.setContact(rs.getString("contact"));
                objbean.setEmail(rs.getString("email"));
                objbean.setDob(rs.getString("dob"));
                return objbean;
                
            
            
            
            }
        } catch (Exception e) {
            System.out.println("Exception of getUserProfile"+e);
        }
        finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of getUserProfile"+e);
            }
        }
        return null;
    }
}
