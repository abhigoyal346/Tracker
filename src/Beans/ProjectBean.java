/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Abhishek Goyal
 */
public class ProjectBean {

    private int emp_id, project_id;
    private String project_name, project_type, project_details;
    private int emp_requirement;
    private double budget_project;

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_details() {
        return project_details;
    }

    public void setProject_details(String project_details) {
        this.project_details = project_details;
    }

    public int getEmp_requirement() {
        return emp_requirement;
    }

    public void setEmp_requirement(int emp_requirement) {
        this.emp_requirement = emp_requirement;
    }

    public double getBudget_project() {
        return budget_project;
    }

    public void setBudget_project(double budget_project) {
        this.budget_project = budget_project;
    }
}
