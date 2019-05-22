/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.EmployeeBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class ManageEmployeeServices {

    public static boolean addEmployee(EmployeeBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into employeemaster(employeeid,loginid,experience,hourlysalary,noofjobsdone,dateofjoining,ratings,location) values(?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, getMaxEmployeeId());
            pstmt.setInt(2, objbean.getLogin_id());
            pstmt.setInt(3, objbean.getExperience());
            pstmt.setDouble(4, objbean.getHourly_salary());
            pstmt.setInt(5, objbean.getNo_of_jobs_done());
            pstmt.setString(6, objbean.getDate_of_joining());
            pstmt.setInt(7, objbean.getRatings());
            pstmt.setString(8, objbean.getLocation());

            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addEmployer of ManageEmployeeServices:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addEmployer  of ManageEmployeeServices:" + p);
            }

        }
        return false;
    }
   public static void setEmployeeRatings(int employeeid,int ratings){
       Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update employeemaster set ratings=? where employeeid=?;");
            pstmt.setInt(1, ratings);
            pstmt.setInt(2, employeeid);
          pstmt.executeUpdate();
            
            
        } catch (Exception e) {
            System.out.println("Exception in setEmployeeRatings()" + e);
        } finally {
            try {
                
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of setEmployeeRatings()" + e);
            }
        }
        
   }
   public static ArrayList<EmployeeBean> getEmployeeId(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<EmployeeBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select employeeid from employeemaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<EmployeeBean>();
            while (rs.next()) {
                EmployeeBean objbean = new EmployeeBean();
                objbean.setEmployee_id(rs.getInt("employeeid"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getEmployeeId()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployeeId()" + e);
            }
        }
        return null;
    } 
    public static boolean updateEmployeeDetails(EmployeeBean objbean) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update  employeemaster set experience=?,location=?,noofjobsdone=?,loginid=? ,ratings=? where  employeeid=?");
            pstmt.setInt(1, objbean.getExperience());
            pstmt.setString(2, objbean.getLocation());
            pstmt.setInt(3, objbean.getNo_of_jobs_done());
            pstmt.setInt(4, objbean.getLogin_id());
            pstmt.setInt(5, 5);
            pstmt.setInt(6, objbean.getEmployee_id());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of updateEmployeeDetails:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of UpdateEmployeeDetails:" + p);
            }

        }
        return false;
    }

    public static ArrayList<EmployeeBean> getAllEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<EmployeeBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from employeemaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<EmployeeBean>();
            while (rs.next()) {
                EmployeeBean objbean = new EmployeeBean();
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setExperience(rs.getInt("experience"));
                objbean.setRatings(rs.getInt("ratings"));
                objbean.setLocation(rs.getString("location"));
                objbean.setHourly_salary(rs.getDouble("hourlysalary"));
                objbean.setNo_of_jobs_done(rs.getInt("noofjobsdone"));
                objbean.setDate_of_joining(rs.getString("dateofjoining"));
                emp.add(objbean);
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
                System.out.println("Exception in finally of getAllEmployee()" + e);
            }
        }
        return null;
    }

    public static EmployeeBean getSingleEmployee(int login_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from employeemaster where loginid=?");
            pstmt.setInt(1, login_id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                EmployeeBean objbean = new EmployeeBean();
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setExperience(rs.getInt("experience"));
                objbean.setRatings(rs.getInt("ratings"));
                objbean.setLocation(rs.getString("location"));
                objbean.setHourly_salary(rs.getDouble("hourlysalary"));
                objbean.setNo_of_jobs_done(rs.getInt("noofjobsdone"));
                objbean.setDate_of_joining(rs.getString("dateofjoining"));
                return objbean;
            }

        } catch (Exception e) {
            System.out.println("Exception in getSingleEmployee()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleEmployee()" + e);
            }
        }
        return null;
    }
   public static int getEmployeeLoginId(int emloyee_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select loginid from employeemaster where employeeid=?");
            pstmt.setInt(1, emloyee_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("loginid");
            }

        } catch (Exception e) {
            System.out.println("Exception in getEmployeeLoginId()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployeeLoginId()" + e);
            }
        }
        return -1;
    }
    public static int getMaxEmployeeId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int i = 0;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select max(employeeid) from employeemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                i = rs.getInt("max(employeeid)");
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

    public static ArrayList<EmployeeBean> getSearchEmployee(int experience, double salary, int no_of_jobs_done, int ratings) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<EmployeeBean> emp = null;
        try {
            con = DBConnection.connect();
            st = con.createStatement();
            String query = "Select * from employeemaster where";
            boolean flag = false;
            if (experience != -1) {
                if (flag) {
                    
                    query=query+" and ";
                }
                query = query + " experience=" + experience;
                flag = true;
            }
            if (salary != -1) {
                if (flag) {
                    
                    query=query+" and ";
                }
                query = query + " hourlysalary=" + salary;
                flag=true;
            }
            if (no_of_jobs_done != -1) {
                if (flag) {
                    
                    query=query+" and ";
                }
                query = query + " noofjobsdone=" + no_of_jobs_done;
                flag=true;
            }
            if (ratings != -1) {
                if (flag) {
                    
                    query=query+" and ";
                }
                query = query + " ratings=" + ratings;
            }
            System.out.println("query"+query);
            rs = st.executeQuery(query);
            emp = new ArrayList<EmployeeBean>();
            while (rs.next()) {
                EmployeeBean objbean = new EmployeeBean();
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setExperience(rs.getInt("experience"));
                objbean.setRatings(rs.getInt("ratings"));
                objbean.setLocation(rs.getString("location"));
                objbean.setHourly_salary(rs.getDouble("hourlysalary"));
                objbean.setNo_of_jobs_done(rs.getInt("noofjobsdone"));
                objbean.setDate_of_joining(rs.getString("dateofjoining"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception of getSearchEmployee:" + e);
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                System.out.println("Final Exception of getSearchEmployee:" + e);
            }
        }

        return null;
    }
    public static int getEmployeeId(int login_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select employeeid from employeemaster where loginid=?");
            pstmt.setInt(1, login_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {

                return rs.getInt("employeeid");
            }

        } catch (Exception e) {
            System.out.println("Exception in getEmployeeId()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployeeId()" + e);
            }
        }
        return -1;
    }
    public static int getEmployeeRating(int employeeid){
       Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select ratings from employeemaster where employeeid=?");
            pstmt.setInt(1, employeeid);
            rs = pstmt.executeQuery();
            if (rs.next()) {

                return rs.getInt("ratings");
            }

        } catch (Exception e) {
            System.out.println("Exception in getEmployeeRating()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployeeRating()" + e);
            }
        }
        return 1;
   }
}
