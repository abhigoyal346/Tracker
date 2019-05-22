/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.EmployeeBean;
import Beans.LoginBean;
import Beans.ProjectBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Abhishek
 */
public class ReportService {

    public static ArrayList<LoginBean> getUserReport() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<LoginBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select loginid,username,type,status,name,email,contact,gender from loginmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<LoginBean>();
            while (rs.next()) {
                LoginBean objbean = new LoginBean();
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setUser_name(rs.getString("username"));
                objbean.setType(rs.getString("type"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setName(rs.getString("name"));
                objbean.setEmail(rs.getString("email"));
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

    public static ArrayList<ProjectBean> getFilteredProjectReport(int name_flag, String projectName, int emp_flag, int empId, int budget_flag, int budget, int projectId, int projectType_flag, String projectType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sqlStatement = "select * from projectmaster where ";
        boolean flag = false;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            if (name_flag == 1) {
                flag = true;
                sqlStatement = sqlStatement + "projectname=" + "'" + projectName + "'";
            }
            if (emp_flag == 1) {
                if (empId != 0) {
                    if (flag) {
                        sqlStatement = sqlStatement + " AND ";

                    } else {
                        flag = true;
                    }
                    sqlStatement = sqlStatement + "empid=" + empId;
                    
                }
            }
            if (budget_flag == 1) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";

                } else {
                    flag = true;
                }
                sqlStatement = sqlStatement + "budgetProject>=" + budget;
            }
            if (projectId != 0) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";
                } else {
                    flag = true;
                }
                sqlStatement = sqlStatement + "projectid=" + projectId;
            }
            if (projectType_flag == 1) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";

                } else {
                    flag = true;
                }
                sqlStatement = sqlStatement + "projectType='" + projectType + "'";
            }
            sqlStatement = sqlStatement + ";";
            
            pstmt = conn.prepareStatement(sqlStatement);
            rs = pstmt.executeQuery();
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
            if (emp.isEmpty()) {
                emp = null;
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getFilteredProjectReport()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getFilteredProjectReport()" + e);
            }
        }
        return null;
    }

    public static ArrayList<LoginBean> getUserReport(int name_flag, int status, int type, int gender, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sqlStatement = "select loginid,username,type,status,name,email,contact,gender from loginmaster where ";
        boolean flag = false;
        ArrayList<LoginBean> emp = null;
        try {
            conn = DBConnection.connect();
            if (name_flag == 1) {
                flag = true;
                sqlStatement = sqlStatement + "name=" + "'" + name + "'";
            }
            if (status != 0) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";

                } else {
                    flag = true;
                }
                if (status == 1) {
                    sqlStatement = sqlStatement + "status=1";
                } else {
                    sqlStatement = sqlStatement + "status=0";
                }
            }
            if (gender != 0) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";

                } else {
                    flag = true;
                }
                if (gender == 1) {
                    sqlStatement = sqlStatement + "gender=1";
                } else {
                    sqlStatement = sqlStatement + "gender=0";
                }
            }
            if (type != 0) {
                if (flag) {
                    sqlStatement = sqlStatement + " AND ";
                }
                if (type == 1) {
                    sqlStatement = sqlStatement + "type='admin'";
                } else if (type == 2) {
                    sqlStatement = sqlStatement + "type='Employer'";
                } else {
                    sqlStatement = sqlStatement + "type='Employee'";
                }
            }
            sqlStatement = sqlStatement + ";";
            // System.out.println("SqlStatement" + sqlStatement);
            pstmt = conn.prepareStatement(sqlStatement);
            rs = pstmt.executeQuery();
            emp = new ArrayList<LoginBean>();
            while (rs.next()) {
                LoginBean objbean = new LoginBean();
                objbean.setLogin_id(rs.getInt("loginid"));
                objbean.setUser_name(rs.getString("username"));
                objbean.setType(rs.getString("type"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setName(rs.getString("name"));
                objbean.setEmail(rs.getString("email"));
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

    public static ArrayList<ProjectBean> getAllProjects() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from projectmaster");

            rs = pstmt.executeQuery();
            if (rs.wasNull()) {
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

    public static ArrayList<EmployeeBean> getAllEmployeeEmployerReport(int emp_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<EmployeeBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select employeemaster.employeeid,experience,ratings,location,hourlysalary,noofjobsdone,dateofjoining from employeemaster inner join taskmaster on employeemaster.employeeid=taskmaster.employeeid and taskmaster.empid=?");
            pstmt.setInt(1, emp_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<EmployeeBean>();
            while (rs.next()) {
                EmployeeBean objbean = new EmployeeBean();
                objbean.setEmployee_id(rs.getInt("employeeid"));
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
            System.out.println("Exception in getAllEmployeeEmployerReport" + e);
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

    public static ArrayList<ProjectBean> getAllProjectsEmployeeReport(int emp_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select projectmaster.projectid,projectname,projecttype,emprequirement,budgetproject from projectmaster inner join taskmaster on projectmaster.projectid=taskmaster.projectid and taskmaster.empid=?");
            pstmt.setInt(1, emp_id);
            rs = pstmt.executeQuery();
            if (rs.wasNull()) {
                return null;
            }
            emp = new ArrayList<ProjectBean>();
            while (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));

                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllProjectsEmployeeReport()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllProjectsEmployeeReport()" + e);
            }
        }
        return null;
    }

    public static ArrayList<ProjectBean> getEmployeeProjectList(int employee_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ProjectBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select projectmaster.projectid,projectname,projecttype,emprequirement,budgetproject from projectmaster inner join taskmaster on projectmaster.projectid=taskmaster.projectid and taskmaster.employeeid=?");
            pstmt.setInt(1, employee_id);
            rs = pstmt.executeQuery();
            if (rs.wasNull()) {
                return null;
            }
            emp = new ArrayList<ProjectBean>();
            while (rs.next()) {
                ProjectBean objbean = new ProjectBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setProject_name(rs.getString("projectname"));
                objbean.setProject_type(rs.getString("projecttype"));
                objbean.setEmp_requirement(rs.getInt("emprequirement"));
                objbean.setBudget_project(rs.getDouble("budgetproject"));

                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getEmployeeProjectList()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getEmployeeProjectList()" + e);
            }
        }
        return null;
    }
}
