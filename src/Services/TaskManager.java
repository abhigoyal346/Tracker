/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.TaskBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class TaskManager {

    public static boolean addTask(TaskBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        if (checkAssignedOrNot(objbean)) {
            return false;
        } else {
            try {
                conn = DBConnection.connect();

                pstmt = conn.prepareStatement("insert into taskmaster(projectid,empid,employeeid,status) values(?,?,?,?)");
                pstmt.setInt(1, objbean.getProject_id());
                pstmt.setInt(2, objbean.getEmp_id());
                pstmt.setInt(3, objbean.getEmployee_id());
                pstmt.setBoolean(4, objbean.isStatus());

                int i = pstmt.executeUpdate();
                if (i > 0) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Catch exeption of addTask:" + e);
            } finally {
                try {
                    pstmt.close();
                    conn.close();
                } catch (Exception p) {

                    System.out.println("Final exeption of addTask:" + p);
                }

            }
        }
        return false;
    }

    private static boolean checkAssignedOrNot(TaskBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connect();

            pstmt = conn.prepareStatement("select projectid,empid,employeeid,status from taskmaster where projectid=? and empid=? and employeeid=? and status=?");
            pstmt.setInt(1, objbean.getProject_id());
            pstmt.setInt(2, objbean.getEmp_id());
            pstmt.setInt(3, objbean.getEmployee_id());
            pstmt.setBoolean(4, objbean.isStatus());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addTask:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addTask:" + p);
            }

        }
        return false;
    }
    public static ArrayList<TaskBean> getEmployerId(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TaskBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select empid from taskmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<TaskBean>();
            while (rs.next()) {
                TaskBean objbean = new TaskBean();
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
    public static int getTask_id(int employer_id,int project_id,int employee_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
       int task_id=-1;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select taskid from taskmaster where empid=? and projectid=? and employeeid=?");
            pstmt.setInt(1, employer_id);
            pstmt.setInt(2, project_id);
            pstmt.setInt(3, employee_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                task_id=rs.getInt("taskid");
            }
           
        } catch (Exception e) {
            System.out.println("Exception in getAllTask()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllTask()" + e);
            }
        }
        return task_id;
    }
    public static ArrayList<TaskBean> getAllTask() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TaskBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from taskmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<TaskBean>();
            while (rs.next()) {
                TaskBean objbean = new TaskBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setStatus(rs.getBoolean("status"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllTask()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllTask()" + e);
            }
        }
        return null;
    }

    public static ArrayList<TaskBean> getSingleTask_projectid(int project_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TaskBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from taskmaster where projectid=?");
            pstmt.setInt(1, project_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<TaskBean>();
            while (rs.next()) {
                TaskBean objbean = new TaskBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setTask_id(rs.getInt("taskid"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getSingleTask" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleTask" + e);
            }
        }
        return null;
    }

    public static ArrayList<TaskBean> getSingleTask(int employee_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TaskBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from taskmaster where employeeid=?");
            pstmt.setInt(1, employee_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<TaskBean>();
            while (rs.next()) {
                TaskBean objbean = new TaskBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setTask_id(rs.getInt("taskid"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getSingleTask" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getSingleTask" + e);
            }
        }
        return null;
    }

    public static TaskBean getTask(int task_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from taskmaster where taskid=?");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                TaskBean objbean = new TaskBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setTask_id(rs.getInt("taskid"));
                return objbean;
            }

        } catch (Exception e) {
            System.out.println("Exception in getTask" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getTask" + e);
            }
        }
        return null;
    }

    public static boolean getTaskStatus(int task_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select status from taskmaster where taskid=?");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getBoolean("status")) {
                    return true;//Means Project InProgress for this User
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in getTask" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getTask" + e);
            }
        }
        return false;
    }

    public static boolean updateTask(int task_id, boolean status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("update taskmaster set status=? where taskid=?;");
            pstmt.setBoolean(1, status);
            pstmt.setInt(2, task_id);

            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of updateTask:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of updateTask:" + p);
            }

        }
        return false;
    }
}
