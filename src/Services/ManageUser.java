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
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class ManageUser {

    public static boolean addUser(LoginBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into loginmaster(loginid,username,password,type,status,name ,email ,dob,contact,gender) values(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, objbean.getLogin_id());
            pstmt.setString(2, objbean.getUser_name());
            pstmt.setString(3, objbean.getPassword());
            pstmt.setString(4, objbean.getType());
            pstmt.setBoolean(5, objbean.isStatus());
            pstmt.setString(6, objbean.getName());
            pstmt.setString(7, objbean.getEmail());
            pstmt.setString(8, objbean.getDob());
            pstmt.setString(9, objbean.getContact());
            pstmt.setBoolean(10, objbean.isGender());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addUser:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addUser:" + p);
            }

        }
        return false;
    }

    public static boolean updateUser(LoginBean objbean) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update  loginmaster set password=?,type=?,status=?,name=?,email=? ,dob=?,contact=?,gender=? where loginid=?");
            pstmt.setInt(9, objbean.getLogin_id());
            pstmt.setString(1, objbean.getPassword());
            pstmt.setString(2, objbean.getType());
            pstmt.setBoolean(3, objbean.isStatus());
            pstmt.setString(4, objbean.getName());
            pstmt.setString(5, objbean.getEmail());
            pstmt.setString(6, objbean.getDob());
            pstmt.setString(7, objbean.getContact());
            pstmt.setBoolean(8, objbean.isGender());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of updateUser:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of updateUser:" + p);
            }

        }
        return false;
    }

    public static LoginBean getSingleLoginBean(int login_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from loginmaster where loginid=?");
            pstmt.setInt(1, login_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                LoginBean objbean = new LoginBean();
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setUser_name(rs.getString("username"));
                objbean.setPassword(rs.getString("password"));
                objbean.setType(rs.getString("type"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setName(rs.getString("name"));
                objbean.setEmail(rs.getString("email"));
                objbean.setDob(rs.getString("dob"));
                objbean.setContact(rs.getString("contact"));
                objbean.setGender(rs.getBoolean("gender"));

                return objbean;
            }

        } catch (Exception e) {
            System.out.println("Exception in getSingleLoginBean()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleLoginBean()" + e);
            }
        }
        return null;
    }

    public static ArrayList<LoginBean> getAllUser(String un) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<LoginBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from loginmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<LoginBean>();
            while (rs.next()) {
                LoginBean objbean = new LoginBean();
                if (rs.getString("type").equalsIgnoreCase(un)) {
                    objbean.setLogin_id(rs.getInt("loginid"));
                    objbean.setUser_name(rs.getString("username"));
                    objbean.setPassword(rs.getString("password"));
                    objbean.setType(rs.getString("type"));
                    objbean.setStatus(rs.getBoolean("status"));
                    objbean.setName(rs.getString("name"));
                    objbean.setEmail(rs.getString("email"));
                    objbean.setDob(rs.getString("dob"));
                    objbean.setContact(rs.getString("contact"));
                    objbean.setGender(rs.getBoolean("gender"));
                    emp.add(objbean);
                }
            }
            if (emp.isEmpty()) {
                emp = null;
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllEmployee()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllEmploye()" + e);
            }
        }
        return null;
    }

    public static ArrayList<LoginBean> getAllUser() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<LoginBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from loginmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<LoginBean>();
            while (rs.next()) {
                LoginBean objbean = new LoginBean();
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setUser_name(rs.getString("username"));
                objbean.setPassword(rs.getString("password"));
                objbean.setType(rs.getString("type"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setName(rs.getString("name"));
                objbean.setEmail(rs.getString("email"));
                objbean.setDob(rs.getString("dob"));
                objbean.setContact(rs.getString("contact"));
                objbean.setGender(rs.getBoolean("gender"));
                emp.add(objbean);
            }
            if (emp.isEmpty()) {
                emp = null;
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllEmployee()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllEmploye()" + e);
            }
        }
        return null;
    }
  public static String getUserName(int loginid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select name from loginmaster where loginid=?;");
            pstmt.setInt(1, loginid);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return(rs.getString("name"));
                
                
            }
            
            return null;
        } catch (Exception e) {
            System.out.println("Exception in getUserName" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getUserName" + e);
            }
        }
        return null;
    }
    public static boolean checkAvailabilityUserName(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from loginmaster where username=?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception of checkAvailabilityUserName:" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of checkAvailabilityUserName:" + e);
            }
        }
        return true;
    }

    public static int getMaxUserId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int i = 0;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select max(loginid) from loginmaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                i = rs.getInt("max(loginid)");
            }
        } catch (Exception e) {
            System.out.println("Exception of checkAvailabilityUserName:" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of checkAvailabilityUserName:" + e);
            }
        }
        return (i + 1);
    }

}
