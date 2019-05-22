/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Beans.EmployerBean;
import Beans.LoginBean;
import Beans.ProjectBean;
import Services.ManageEmployerServices;
import Services.ProjectManagerServices;
import Utility.Utilities;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Abhishek Goyal
 */
public class ProjectManager extends javax.swing.JPanel {

    /**
     * Creates new form ProjectManager
     */
    private int emp_id;
    private int add_edit_flag;
    private int count;
    private ArrayList<ProjectBean> al;

    public ProjectManager(LoginBean objbean) {

        emp_id = ManageEmployerServices.getSingleEmployer(objbean.getLogin_id()).getEmp_id();
        initComponents();
        add_edit_flag = -1;
        count = 0;
        al = ProjectManagerServices.getAllProjects(emp_id);
        if (al.size() == 0) {
            Utilities.showMsg(this, "Add Your First Project", 0);
            enableTextFields(false);
            btnEdit.setEnabled(false);
            btnSave.setEnabled(false);
            btnAdd.setEnabled(true);
            btnCancel.setEnabled(true);
            txtSearchByProjectName.setEditable(false);
            btnFirst.setEnabled(false);
            btnNext.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
        } else {
            showRecord();
            enableButtons();
        }
    }

    private void enableButtons() {
        boolean flag = true;
        btnFirst.setEnabled(flag);
        btnPrevious.setEnabled(flag);
        btnNext.setEnabled(flag);
        btnLast.setEnabled(flag);
    }

    public void enableTextFields(boolean flag) {
        txtProjectId.setEditable(false);
        txtEmployerId.setEditable(false);
        txtProjectName.setEditable(flag);
        ddlProjectType.setEnabled(flag);
        txtNoofEmployeesRequired.setEditable(flag);
        txtBudgetSactioned.setEditable(flag);
        taProjectDetails.setEditable(flag);

    }

    public void showRecord() {
        enableTextFields(false);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);
        btnCancel.setEnabled(true);
        txtSearchByProjectName.setEnabled(true);
        enableButtons();
        ProjectBean objbean = al.get(count);
        txtProjectId.setText(String.valueOf(objbean.getProject_id()));
        txtEmployerId.setText(String.valueOf(objbean.getEmp_id()));
        txtProjectName.setText(objbean.getProject_name());
        ddlProjectType.setSelectedItem(objbean.getProject_type());
        txtNoofEmployeesRequired.setText(String.valueOf(objbean.getEmp_requirement()));
        txtBudgetSactioned.setText(String.valueOf(objbean.getBudget_project()));
        taProjectDetails.setText(objbean.getProject_details());
        if (count == 0) {
            btnPrevious.setEnabled(false);
            btnFirst.setEnabled(false);
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);

        }
        if (count == al.size() - 1) {

            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrevious.setEnabled(true);
            btnFirst.setEnabled(true);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtProjectId = new javax.swing.JTextField();
        txtEmployerId = new javax.swing.JTextField();
        txtProjectName = new javax.swing.JTextField();
        txtNoofEmployeesRequired = new javax.swing.JTextField();
        txtBudgetSactioned = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taProjectDetails = new javax.swing.JTextArea();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtSearchByProjectName = new javax.swing.JTextField();
        ddlProjectType = new javax.swing.JComboBox<>();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Project ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Employer ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Project Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Project Type");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("No of Employee Required:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Budget Sactioned");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Project Details");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(135, 30));
        jPanel1.setRequestFocusEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Project Manager");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        taProjectDetails.setColumns(20);
        taProjectDetails.setRows(5);
        jScrollPane1.setViewportView(taProjectDetails);

        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Search By Project Name");

        txtSearchByProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchByProjectNameActionPerformed(evt);
            }
        });
        txtSearchByProjectName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchByProjectNameKeyPressed(evt);
            }
        });

        ddlProjectType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Coding", "NonCoding" }));

        btnFirst.setText("First");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrevious.setText("Previous");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText("Last");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtProjectId)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(txtEmployerId)
                    .addComponent(txtProjectName)
                    .addComponent(ddlProjectType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNoofEmployeesRequired)
                    .addComponent(txtBudgetSactioned))
                .addGap(121, 121, 121)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchByProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd)
                            .addComponent(btnEdit))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(94, 94, 94))
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1154, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProjectId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtSearchByProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmployerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ddlProjectType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNoofEmployeesRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBudgetSactioned, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnCancel))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit)
                            .addComponent(btnSave))
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchByProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchByProjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchByProjectNameActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        enableTextFields(true);
        emptyAllTextFields();
        buttonTextFieldsEnableDisable();
        add_edit_flag = 0;

    }//GEN-LAST:event_btnAddActionPerformed
    private void buttonTextFieldsEnableDisable() {
        enableTextFields(true);
        btnEdit.setEnabled(false);
        btnAdd.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
        txtSearchByProjectName.setEnabled(false);
        btnFirst.setEnabled(false);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
        btnLast.setEnabled(false);
    }
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if (add_edit_flag == 0) {
            count = al.size() - 1;
            showRecord();
        } else if (add_edit_flag == 1) {
            showRecord();
        } else {
            MainFrame.c.setVisible(false);
        }
        add_edit_flag = -1;

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        buttonTextFieldsEnableDisable();
        add_edit_flag = 0;


    }//GEN-LAST:event_btnEditActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        count = 0;
        showRecord();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        if (count > 0) {
            count--;
            showRecord();
        }

    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if (count != al.size() - 1) {
            count++;
            showRecord();

        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        count = al.size() - 1;
        showRecord();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (txtProjectName.getText().trim().isEmpty()) {
            Utilities.showMsg(this, "Please Enter Project Name", 1);
            txtProjectName.requestFocus();

        } else if (txtNoofEmployeesRequired.getText().trim().isEmpty()) {
            Utilities.showMsg(this, "Please Enter UserName", 1);
            txtNoofEmployeesRequired.requestFocus();
        } else if (txtBudgetSactioned.getText().trim().isEmpty()) {
            Utilities.showMsg(this, "Please Enter UserName", 1);
            txtBudgetSactioned.requestFocus();
        } else if (taProjectDetails.getText().trim().isEmpty()) {
            Utilities.showMsg(this, "Please Enter Dob", 1);
            taProjectDetails.requestFocus();
        } /*else if (!txtContactNo.getText().trim().isEmpty() && !Utilities.checkContactNo(txtContactNo.getText().trim())) {
            Utilities.showMsg(this, "Please Enter Corect Contact ", 1);
            txtContactNo.requestFocus();

        }*/ else {
            if (add_edit_flag == 0)//add record
            {
                if (ProjectManagerServices.addProject(projectDetails())) {
                    Utilities.showMsg(this, "Project Added", 0);
                } else {
                    Utilities.showMsg(this, "Project Not Added", 1);
                }
            } else if (add_edit_flag == 1) {
                //    System.out.println("Count"+count);
                if (ProjectManagerServices.updateProjectDetails(projectDetails())) {
                    Utilities.showMsg(this, "Record Updated", 0);
                } else {
                    Utilities.showMsg(this, "Record Not Updated", 1);
                }

            }
        }
        afterSave();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtSearchByProjectNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchByProjectNameKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (txtSearchByProjectName.getText().isEmpty()) {
                Utilities.showMsg(this, " Enter Project Name", 2);

            } else {
                boolean flag=false;
                for (int i = 0; i < al.size(); i++) {
                    ProjectBean objbean = al.get(i);
                    if (txtSearchByProjectName.getText().equalsIgnoreCase(objbean.getProject_name())) {
                        objbean = ProjectManagerServices.getSingleProject(txtSearchByProjectName.getText());
                        count = i;
                        showRecord();
                        flag=true;
                        break;
                        
                    }
                }
                if(!flag){
                    
                    Utilities.showMsg(this, " Project Not Found", 1);
                }else{
                    flag=false;
                }
                txtSearchByProjectName.setText("");
            }
        
        }
    }//GEN-LAST:event_txtSearchByProjectNameKeyPressed
    private void emptyAllTextFields() {
        txtProjectName.setText("");
        txtNoofEmployeesRequired.setText("");
        txtBudgetSactioned.setText("");
        taProjectDetails.setText("");
    }

    private ProjectBean projectDetails() {
        ProjectBean objbean = new ProjectBean();
        if (add_edit_flag == 1) {

            objbean.setProject_id(al.get(count).getProject_id());
        }
        objbean.setEmp_id(emp_id);
        objbean.setProject_name(txtProjectName.getText());
        objbean.setProject_type((String) ddlProjectType.getSelectedItem());
        objbean.setEmp_requirement(Integer.parseInt(txtNoofEmployeesRequired.getText().trim()));
        objbean.setBudget_project(Integer.parseInt(txtBudgetSactioned.getText().trim()));
        objbean.setProject_details(taProjectDetails.getText());
        return objbean;
    }

    private void afterSave() {
        al = ProjectManagerServices.getAllProjects(emp_id);
        if (add_edit_flag == 0) {
            if (al.size() != 0) {
                count = al.size() - 1;
            } else {
                count = 0;
            }
        }
        add_edit_flag = -1;
        showRecord();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> ddlProjectType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taProjectDetails;
    private javax.swing.JTextField txtBudgetSactioned;
    private javax.swing.JTextField txtEmployerId;
    private javax.swing.JTextField txtNoofEmployeesRequired;
    private javax.swing.JTextField txtProjectId;
    private javax.swing.JTextField txtProjectName;
    private javax.swing.JTextField txtSearchByProjectName;
    // End of variables declaration//GEN-END:variables
}
