/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.ProjectBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class ProjectManagerServices {
    
public static boolean addProject(ProjectBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into projectmaster values(?,?,?,?,?,?,?)");
            pstmt.setInt(1, getMaxProjectId());
            pstmt.setInt(2, objbean.getEmp_id());
            pstmt.setString(3, objbean.getProject_name());
            pstmt.setString(4, objbean.getProject_type());
            pstmt.setString(5, objbean.getProject_details());
            pstmt.setInt(6, objbean.getEmp_requirement());
            pstmt.setDouble(7, objbean.getBudget_project()); 
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addProject :" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) { 

                System.out.println("Final exeption of addProject:" + p);
            }

        }
        return false;
    }
    public static boolean updateProjectDetails(ProjectBean objbean) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update  projectmaster set projectname=?,projectdetails=?,emprequirement=?,budgetproject=? where projectid=?");
            pstmt.setString(1, objbean.getProject_name());
           pstmt.setString(2, objbean.getProject_details());
            pstmt.setInt(3, objbean.getEmp_requirement());
            pstmt.setDouble(4,objbean.getBudget_project());
            pstmt.setInt(5, objbean.getProject_id());
            int i=pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of updateProjectDetails:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of updateProjectDetails:" + p);
            }

        }
        return false;
    }

    public static ArrayList<ProjectBean> getAllProjects(int emp_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from projectmaster where empid=?");
            pstmt.setInt(1,emp_id);
            rs = pstmt.executeQuery();
            if(rs.wasNull()){
                return null;
            }
            emp = new ArrayList<ProjectBean>();
            while (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setProject_details(rs.getString("projectdetails"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));
                
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllProjects()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllProjects()" + e);
            }
        }
        return null;
    }
    public static ProjectBean getSingleProject(String project_name){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from projectmaster where projectname=?");
            pstmt.setString(1,project_name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setProject_details(rs.getString("projectdetails"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));
                
                return objbean;
            }
            
        } catch (Exception e) {
            System.out.println("Exception in getSingleProject():" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleProject():" + e);
            }
        }
        return null;
    }
    public static ProjectBean getSingleProject(int project_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from projectmaster where projectid=?");
            pstmt.setInt(1,project_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setProject_details(rs.getString("projectdetails"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));
                
                return objbean;
            }
            
        } catch (Exception e) {
            System.out.println("Exception in getSingleProject():" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleProject():" + e);
            }
        }
        return null;
    }
    public static int getMaxProjectId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int i = 0;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select max(projectid) from projectmaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                i = rs.getInt("max(projectid)");
            }
        } catch (Exception e) {
            System.out.println("Exception of getMaxProjectId():" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Final Exception of getMaxProjectId():" + e);
            }
        }
        return (i + 1);
    }
     public static ArrayList<ProjectBean> getAllProjects() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from projectmaster");
           
            rs = pstmt.executeQuery();
            if(rs.wasNull()){
                return null;
            }
            emp = new ArrayList<ProjectBean>();
            while (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setProject_details(rs.getString("projectdetails"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));
                
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllProjects()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllProjects()" + e);
            }
        }
        return null;
    }
}

