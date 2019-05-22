/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Beans.CountBean;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class countmanager {

    public static boolean addCountData(CountBean objbean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("insert into countmaster(taskid,screentime,keystrokes,mouseclicks) values(?,?,?,?)");
            pstmt.setInt(1, objbean.getTask_id());
            pstmt.setString(2, objbean.getScreen_time());
            pstmt.setInt(3, objbean.getKeystrokes());
            pstmt.setInt(4, objbean.getMouseclicks());

            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Catch exeption of addCountData:" + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception p) {

                System.out.println("Final exeption of addCountData:" + p);
            }

        }
        return false;
    }

    public static ArrayList<CountBean> getAllCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CountBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from countmaster");
            rs = pstmt.executeQuery();
            emp = new ArrayList<CountBean>();
            while (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setS_no(rs.getInt("sno"));
                objbean.setTask_id(rs.getInt("taskid"));
                objbean.setKeystrokes(rs.getInt("keystrokes"));
                objbean.setMouseclicks(rs.getInt("mouseclicks"));
                objbean.setScreen_time(rs.getString("screentime"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllCount()" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllCount()" + e);
            }
        }
        return null;
    }

    /* public static CountBean getLastCount_taskid(int task_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CountBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select max(sno),taskid,keystrokes,mouseclicks from countmaster where taskid=?;");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<CountBean>();
            if (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setTask_id(rs.getInt("taskid"));
                objbean.setKeystrokes(rs.getInt("keystrokes"));
                objbean.setMouseclicks(rs.getInt("mouseclicks"));
                
                return objbean;
            }
            
        } catch (Exception e) {
            System.out.println("Exception in getLastCount_taskid" + e);
        }
        finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getLastCount_taskid" + e);
            }
        }
        return null;
    }*/
    public static CountBean getAllCount_taksid(int task_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CountBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from countmaster where taskid=? and Sno=(select max(Sno) from countmaster)");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<CountBean>();
            if (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setS_no(rs.getInt("sno"));
                objbean.setTask_id(rs.getInt("taskid"));
                objbean.setKeystrokes(rs.getInt("keystrokes"));
                objbean.setMouseclicks(rs.getInt("mouseclicks"));
                objbean.setScreen_time(rs.getString("screentime"));
                return objbean;
            }

        } catch (Exception e) {
            System.out.println("Exception in getAllCount_taksid" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllCount_taksid" + e);
            }
        }
        return null;
    }

    public static ArrayList<CountBean> getAllCount_task_id(int task_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CountBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from countmaster where taskid=?");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();
            emp = new ArrayList<CountBean>();
            while (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setS_no(rs.getInt("sno"));
                objbean.setTask_id(rs.getInt("taskid"));
                objbean.setKeystrokes(rs.getInt("keystrokes"));
                objbean.setMouseclicks(rs.getInt("mouseclicks"));
                objbean.setScreen_time(rs.getString("screentime"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllCount_task_id" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllCount_task_id" + e);
            }
        }
        return null;
    }

    /* public static CountBean getTask(int task_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from taskmaster where taskid=?");
            pstmt.setInt(1, task_id);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setProject_id(rs.getInt("projectid"));
                objbean.setEmp_id(rs.getInt("empid"));
                objbean.setEmployee_id(rs.getInt("employeeid"));
                objbean.setStatus(rs.getBoolean("status"));
                objbean.setTask_id(rs.getInt("taskid"));
                return objbean;
            }
            
        } catch (Exception e) {
            System.out.println("Exception in getTask" + e);
        }
        finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getTask" + e);
            }
        }
        return null;
    }*/
    public static ArrayList<CountBean> getAllCountTaskIdDate(int task_id, String date) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CountBean> emp = null;
        try {
            conn = DBConnection.connect();
            pstmt = conn.prepareStatement("select * from countmaster where taskid=? and Screentime=?");
            pstmt.setInt(1, task_id);
            pstmt.setString(2, date);
            rs = pstmt.executeQuery();
            emp = new ArrayList<CountBean>();
            while (rs.next()) {
                CountBean objbean = new CountBean();
                objbean.setS_no(rs.getInt("sno"));
                objbean.setTask_id(rs.getInt("taskid"));
                objbean.setKeystrokes(rs.getInt("keystrokes"));
                objbean.setMouseclicks(rs.getInt("mouseclicks"));
                objbean.setScreen_time(rs.getString("screentime"));
                emp.add(objbean);
            }
            return emp;
        } catch (Exception e) {
            System.out.println("Exception in getAllCountTaskIdDate" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in finally of getAllCountTaskIdDate" + e);
            }
        }
        return null;
    }
}
