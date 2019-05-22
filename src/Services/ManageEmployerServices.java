/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.EmployerBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class ManageEmployerServices {

    public static boolean addEmployer(EmployerBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into employermaster(empid,loginid,experience,ratings,location,startingdate,deadline,established,jobsdone,expectedhours,companydetails) values(?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, getMaxEmpId());
            pstmt.setInt(2, objbean.getLogin_id());
            pstmt.setInt(3, objbean.getExperience());
            pstmt.setInt(4, 5);
            pstmt.setString(5, objbean.getLocation());
            pstmt.setString(6, objbean.getStarting_date());
            pstmt.setString(7, objbean.getDeadline());
            pstmt.setString(8, objbean.getEstablished());
            pstmt.setInt(9, objbean.getjobs_done());
            pstmt.setInt(10, objbean.getExpected_hours_of_job());
            pstmt.setString(11, objbean.getDetails());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addEmployer of ManageEmployerServices:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addEmployer  of ManageEmployerServices:" + p);
            }

        }
        return false;
    }
    public static boolean addEmployerParticularDetails(EmployerBean objbean){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into employermaster(empid,loginid,location,experience,established,companydetils) values(?,?,?,?,?,?)");
            pstmt.setInt(1, getMaxEmpId());
            pstmt.setInt(2, objbean.getLogin_id());
            pstmt.setString(3, objbean.getLocation());
            pstmt.setInt(4, objbean.getExperience());
            pstmt.setString(5, objbean.getEstablished());
            pstmt.setString(6, objbean.getDetails());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addEmployerParticularDetails:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addEmployerParticularDetails:" + p);
            }

        }
        return false; 
    }
    public static ArrayList<EmployerBean> getEmployerId(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<EmployerBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select empid from employermaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<EmployerBean>();
            while (rs.next()) {
                EmployerBean objbean = new EmployerBean();
                objbean.setEmp_id(rs.getInt("empid"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getEmployerId()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployerId()" + e);
            }
        }
        return null;
    } 
    public static boolean updateEmployerDetails(EmployerBean objbean) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update  employermaster set location=?,startingdate=?,deadline=?,jobsdone=?,expectedhours=?,companydetails=?, experience=?,established=? ,ratings=? where empid=?");
            pstmt.setString(1, objbean.getLocation());
            pstmt.setString(2, objbean.getStarting_date());
            pstmt.setString(3, objbean.getDeadline());
            pstmt.setInt(4, objbean.getjobs_done());
            pstmt.setInt(5, objbean.getExpected_hours_of_job());
            pstmt.setString(6, objbean.getDetails());
            pstmt.setInt(7, objbean.getExperience());
            pstmt.setString(8,objbean.getEstablished());
            pstmt.setInt(9, 5);
            pstmt.setInt(10, objbean.getEmp_id());
            int i=pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of updateEmployerDetails:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of UpdateEmployerDetails:" + p);
            }

        }
        return false;
    }

    public static ArrayList<EmployerBean> getAllEmployer() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<EmployerBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from employermaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<EmployerBean>();
            while (rs.next()) {
                EmployerBean objbean = new EmployerBean();
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setExperience(rs.getInt("experience"));
                objbean.setRatings(rs.getInt("ratings"));
                objbean.setLocation(rs.getString("location"));
                objbean.setStarting_date(rs.getString("startingdate"));
                objbean.setDeadline(rs.getString("deadline"));
                objbean.setEstablished(rs.getString("established"));
                objbean.setjobs_done(rs.getInt("jobsdone"));
                objbean.setExpected_hours_of_job(rs.getInt("expectedhours"));
                objbean.setDetails(rs.getString("companydetails"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllEmployer()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllEmployer()" + e);
            }
        }
        return null;
    }
    public static EmployerBean getSingleEmployer(int login_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from employermaster where loginid=?");
            pstmt.setInt(1,login_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                EmployerBean objbean = new EmployerBean();
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setExperience(rs.getInt("experience"));
                objbean.setRatings(rs.getInt("ratings"));
                objbean.setLocation(rs.getString("location"));
                objbean.setStarting_date(rs.getString("startingdate"));
                objbean.setDeadline(rs.getString("deadline"));
                objbean.setEstablished(rs.getString("established"));
                objbean.setjobs_done(rs.getInt("jobsdone"));
                objbean.setExpected_hours_of_job(rs.getInt("expectedhours"));
                objbean.setDetails(rs.getString("companydetails"));
                return objbean;
            }
            
        } catch (Exception e) {
            System.out.println("Exception in getSingleEmployer()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleEmployer()" + e);
            }
        }
        return null;
    }
    public static int getMaxEmpId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int i = 0;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select max(empid) from employermaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                i = rs.getInt("max(empid)");
            }
        } catch (Exception e) {
            System.out.println("Exception of getMaxEmpId:" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of getMaxEmpId:" + e);
            }
        }
        return (i + 1);
    }
    public static int getEmployerId(int login_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select empid from employermaster where loginid=?;");
            pstmt.setInt(1, login_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                
                return rs.getInt("empid");
            }else{
                return getMaxEmpId();
            }

        } catch (Exception e) {
            System.out.println("Exception in getEmployerId()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployerId()" + e);
            }
        }
        return -1;
    }
}
