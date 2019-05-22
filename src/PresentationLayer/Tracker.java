/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Beans.CountBean;
import Beans.EmployeeBean;
import Beans.EmployerBean;
import Beans.LoginBean;
import Beans.ProjectBean;
import Beans.TaskBean;
import Services.ManageEmployeeServices;
import Services.ManageEmployerServices;
import Services.ManageUser;
import Services.ProjectManagerServices;
import Services.TaskManager;
import Services.countmanager;
import Utility.Utilities;
import ZipperUnzipper.UnZipFile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Abhishek Goyal
 */
public class Tracker extends javax.swing.JPanel {

    /**
     * Creates new form Tracker
     */
    private LoginBean objbean;
    private int employer_id;
    private int employee_id;
    private int project_id;
    private int task_id;
    private ArrayList<ProjectBean> alpbean;
    private ArrayList<TaskBean> tbean;
    private ArrayList<LoginBean> lbean;
    private ArrayList<CountBean> cbean;
    private ArrayList<EmployerBean> ebean;
    private ArrayList<EmployeeBean> employeebean;
    private String filename;
    private String file[];
    private int no_of_Images;
    private int count;
    private String path;
    private String date;
    private Socket skt;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Files[] files;

    public Tracker(LoginBean objbean) {
        initComponents();
        this.objbean = objbean;
        employer_id = ManageEmployerServices.getEmployerId(objbean.getLogin_id());
        count = 0;
        skt = null;
        dis = null;
        dos = null;
        disableFields();
        if (objbean.getType().equalsIgnoreCase("Admin")) {
            showEmployerIds();
            showProjectName();
            showEmployeeId();
        } else {
            showEmployerIds();
            ddlSelectEmployer.setEnabled(true);
        }
        showProjectName();

        lbean = ManageUser.getAllUser("employee");

    }

    private void showEmployerIds() {
        ddlSelectEmployer.removeAllItems();
        if (objbean.getType().equalsIgnoreCase("Employer")) {
            ddlSelectEmployer.addItem(Integer.toString(employer_id));
            ddlSelectEmployer.setEnabled(false);

        } else if (objbean.getType().equalsIgnoreCase("Admin")) {
            ddlSelectEmployer.addItem("No Employer");
            System.out.println("sst");
            ebean = ManageEmployerServices.getEmployerId();
            if (ebean != null) {
                for (int i = 0; i < ebean.size(); i++) {
                    EmployerBean objbean = ebean.get(i);
                    ddlSelectEmployer.addItem(Integer.toString(objbean.getEmp_id()));
                }
            }
            ddlSelectProject.setEnabled(true);
        }

    }

    private void showProjectName() {
        if (objbean.getType().equalsIgnoreCase("admin")) {
            alpbean = ProjectManagerServices.getAllProjects();
        } else {
            alpbean = ProjectManagerServices.getAllProjects(employer_id);
        }
        ddlSelectProject.removeAllItems();
        ddlSelectProject.addItem("No Project");
        if (alpbean != null) {
            for (int i = 0; i < alpbean.size(); i++) {
                ProjectBean objbean = alpbean.get(i);
                ddlSelectProject.addItem(objbean.getProject_id() + "-" + objbean.getProject_name());
            }
        }
    }

    private void showEmployeeId() {
        ddlSelectEmployee.removeAllItems();
        ddlSelectEmployee.addItem("no Employee");
        if (objbean.getType().equalsIgnoreCase("Admin")) {
            employeebean = ManageEmployeeServices.getEmployeeId();
            if (employeebean != null) {
                for (int i = 0; i < employeebean.size(); i++) {
                    EmployeeBean objbean = employeebean.get(i);
                    ddlSelectEmployee.addItem(Integer.toString(objbean.getEmployee_id()));

                }
            }
        }

    }

    private void showDateDetails() {
        try {
            skt = new Socket(Utilities.serverAddress(), Utilities.portAddress());
            dis = new DataInputStream(skt.getInputStream());
            dos = new DataOutputStream(skt.getOutputStream());
            dos.writeUTF("EmployerFilesNameDownload");
            dos.writeInt(employer_id);
            dos.writeInt(project_id);
            dos.writeInt(employee_id);
            ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
            String[] filesinDir = (String[]) ois.readObject();
            ois.close();
            String[] str = null;
            str = new String[filesinDir.length];
            for (int i = 0; i < filesinDir.length; i++) {
                String s = filesinDir[i].substring(0, filesinDir[i].length() - 4);
                String[] arrOfStr = s.split("-");
                String sDate1 = arrOfStr[1];
                Date date1 = new SimpleDateFormat("ddMMyyyy").parse(sDate1);
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                str[i] = df.format(date1);
            }
            ddlSelectDate.removeAllItems();
            ddlSelectDate.addItem("no");
            if (str != null) {
                for (int i = 0; i < str.length; i++) {
                    ddlSelectDate.addItem(str[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in showDateDetails() Tracker" + e);
        } finally {
            try {
                skt.close();
            } catch (Exception e) {
                System.out.println("Final Exception in showDateDetails() Tracker" + e);
            }
        }
    }

    private void disableFields() {
        boolean flag = false;
        btnSave.setEnabled(flag);
        btnEdit.setEnabled(flag);
        rbComplete.setEnabled(flag);
        rbIncomplete.setEnabled(flag);
        ddlEmployeeRatings.setEnabled(flag);
        txtKeyStrokeCount.setEditable(flag);
        txtEffieciency.setEditable(flag);
        txtMouseClickCount.setEditable(flag);
        txtTime.setEditable(flag);
        btnCancel.setEnabled(true);
        btnCalculate.setEnabled(flag);
        rbKeyStrokeNo.setEnabled(flag);
        rbKeyStrokeYes.setEnabled(flag);
        rbMouseNo.setEnabled(flag);
        rbMouseYes.setEnabled(flag);
        txtEmployeeName.setEditable(false);
    }

    public void enableButtons() {
        boolean flag = true;
        btnNext.setEnabled(flag);
        btnFirst.setEnabled(flag);
        btnPrevious.setEnabled(flag);
        btnLast.setEnabled(flag);
    }

    private void showScreenshots() {
        enableButtons();
        Utilities.setImage(lblImage1, path + file[count]);
        if (count == no_of_Images-1) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrevious.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        if (count == 0) {
            btnPrevious.setEnabled(false);
            btnFirst.setEnabled(false);
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblImage1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ddlSelectProject = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        ddlSelectEmployee = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnShow = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtEmployeeName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        ddlSelectEmployer = new javax.swing.JComboBox<>();
        ddlSelectDate = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rbKeyStrokeYes = new javax.swing.JRadioButton();
        rbKeyStrokeNo = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rbMouseYes = new javax.swing.JRadioButton();
        rbMouseNo = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        txtEffieciency = new javax.swing.JTextField();
        btnCalculate = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtKeyStrokeCount = new javax.swing.JTextField();
        txtMouseClickCount = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        KeyStrokes = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rbComplete = new javax.swing.JRadioButton();
        rbIncomplete = new javax.swing.JRadioButton();
        ddlEmployeeRatings = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(73, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Tracker");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ScreenShots", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        lblImage1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Project Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Select Project ");

        ddlSelectProject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlSelectProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlSelectProjectActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Select Employee Id");

        ddlSelectEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlSelectEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlSelectEmployeeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Enter Date");

        btnShow.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnShow.setText("Show");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setText("Employee Name");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Employer Id");

        ddlSelectEmployer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlSelectEmployer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlSelectEmployerActionPerformed(evt);
            }
        });

        ddlSelectDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddlSelectDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlSelectDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmployeeName)
                    .addComponent(ddlSelectEmployee, 0, 161, Short.MAX_VALUE)
                    .addComponent(ddlSelectProject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnShow)
                    .addComponent(ddlSelectEmployer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ddlSelectDate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(73, 73, 73))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ddlSelectEmployer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ddlSelectProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ddlSelectEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ddlSelectDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnShow))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Efficiency", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Include KeyStrokes");

        buttonGroup2.add(rbKeyStrokeYes);
        rbKeyStrokeYes.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbKeyStrokeYes.setText("Yes");

        buttonGroup2.add(rbKeyStrokeNo);
        rbKeyStrokeNo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbKeyStrokeNo.setText("No");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Include MouseClicks");

        buttonGroup3.add(rbMouseYes);
        rbMouseYes.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbMouseYes.setText("Yes");
        rbMouseYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMouseYesActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbMouseNo);
        rbMouseNo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbMouseNo.setText("No");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("Efficiency");

        btnCalculate.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCalculate.setText("Calculate");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtEffieciency, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbKeyStrokeYes, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbMouseYes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbMouseNo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbKeyStrokeNo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(btnCalculate)
                .addGap(146, 146, 146))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rbKeyStrokeYes)
                    .addComponent(rbKeyStrokeNo)
                    .addComponent(btnCalculate))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbMouseYes)
                    .addComponent(rbMouseNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEffieciency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Count Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setText("TIme");

        KeyStrokes.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        KeyStrokes.setText("KeyStrokes");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Mouse Clicks");

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrevious.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnPrevious.setText("<");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLast.setText(">>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFirst)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(KeyStrokes)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtKeyStrokeCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addComponent(txtMouseClickCount, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnPrevious)
                        .addGap(49, 49, 49)
                        .addComponent(btnNext)
                        .addGap(58, 58, 58)
                        .addComponent(btnLast)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKeyStrokeCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KeyStrokes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMouseClickCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Status", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Employee Ratings");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Work Status");

        buttonGroup1.add(rbComplete);
        rbComplete.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbComplete.setText("Complete");

        buttonGroup1.add(rbIncomplete);
        rbIncomplete.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rbIncomplete.setText("Incomplete");

        ddlEmployeeRatings.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1 Star", "2 Star", "3 Star", "4 Star", "5 Star" }));

        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(rbComplete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbIncomplete))
                    .addComponent(ddlEmployeeRatings, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbComplete)
                    .addComponent(rbIncomplete))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ddlEmployeeRatings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1371, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if (count < no_of_Images-1) {
            count++;
             showScreenshots();
        showCount();
        }
       
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        if (count > 0) {
            count--;
            showScreenshots();
        showCount();
        }
        
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void rbMouseYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMouseYesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbMouseYesActionPerformed
    private void showCount() {
        try {
            Date date1 = new SimpleDateFormat("ddMMyyy").parse(date);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date2 = df.format(date1);
            //   System.err.println("ss"+date2);
            task_id = TaskManager.getTask_id(employer_id, project_id, employee_id);
            cbean = countmanager.getAllCountTaskIdDate(task_id, date2);
            txtKeyStrokeCount.setText(String.valueOf(cbean.get(count).getKeystrokes()));
            txtMouseClickCount.setText(String.valueOf(cbean.get(count).getMouseclicks()));
            txtTime.setText(cbean.get(count).getScreen_time());
            btnCalculate.setEnabled(true);
            boolean flag = true;
            rbKeyStrokeNo.setEnabled(flag);
            rbKeyStrokeYes.setEnabled(flag);
            rbMouseNo.setEnabled(flag);
            rbMouseYes.setEnabled(flag);
        } catch (Exception e) {
            System.out.println("Format Expetion in showCount");
        }
    }

    private void ddlSelectProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlSelectProjectActionPerformed
        // TODO add your handling code here:
        if (ddlSelectProject.getSelectedIndex() == 0) {
            ddlSelectEmployee.setEnabled(false);
            ddlSelectDate.setEnabled(false);
        } else if (ddlSelectProject.getSelectedIndex() > 0) {
            if (objbean.getType().equalsIgnoreCase("admin")) {
                showEmployeeId();
                String pname = (String) ddlSelectProject.getSelectedItem();
                project_id = Character.getNumericValue(pname.charAt(0));
            } else {
                ddlSelectEmployee.removeAllItems();
                ddlSelectEmployee.addItem("none");
                String pname = (String) ddlSelectProject.getSelectedItem();
                project_id = Character.getNumericValue(pname.charAt(0));
                tbean = TaskManager.getSingleTask_projectid(project_id);

                if (tbean != null) {
                    for (int j = 0; j < tbean.size(); j++) {
                        ddlSelectEmployee.addItem(String.valueOf(tbean.get(j).getEmployee_id()));
                    }
                }
            }
            ddlSelectEmployee.setEnabled(true);
        }

    }//GEN-LAST:event_ddlSelectProjectActionPerformed

    private void ddlSelectEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlSelectEmployeeActionPerformed
        // TODO add your handling code here:
        if (ddlSelectEmployee.getSelectedIndex() == 0) {
            ddlSelectDate.setEnabled(false);
            btnEdit.setEnabled(false);
        } else if (ddlSelectEmployee.getSelectedIndex() > 0) {
            employee_id = Integer.parseInt((String) ddlSelectEmployee.getSelectedItem());
            showEmployeeName();
            showDateDetails();

            ddlSelectDate.setEnabled(true);
            btnEdit.setEnabled(true);
            task_id = TaskManager.getTask_id(employer_id, project_id, employee_id);
            if (TaskManager.getTaskStatus(task_id)) {
                rbIncomplete.setSelected(true);
            } else {
                rbComplete.setSelected(true);
            }
            ddlEmployeeRatings.setSelectedIndex(ManageEmployeeServices.getEmployeeRating(employee_id));

        }
    }//GEN-LAST:event_ddlSelectEmployeeActionPerformed
    private void showEmployeeName() {
        int loginid = ManageEmployeeServices.getEmployeeLoginId(employee_id);
        txtEmployeeName.setText(ManageUser.getUserName(loginid));
    }
    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
        // TODO add your handling code here:
        int kstar = 0, mstar = 0;
        int efficiency = 0;
        int kcount = cbean.get(count).getKeystrokes();
        if (kcount > 0 && kcount <= 600) {
            kstar = 5;
        } else if (kcount > 600 && kcount <= 800) {
            kstar = 7;
        } else if (kcount > 800) {
            kstar = 10;
        }
        int mcount = cbean.get(count).getMouseclicks();
        if (mcount > 0 && mcount <= 400) {
            mstar = 5;
        } else if (mcount > 400 && mcount <= 700) {
            mstar = 7;
        } else if (mcount > 700) {
            mstar = 10;
        }
        if (rbKeyStrokeYes.isSelected() && rbMouseYes.isSelected()) {
            efficiency = (kstar + mstar) * 5;
        } else if (rbKeyStrokeYes.isSelected()) {
            efficiency = kstar * 10;
        } else if (rbMouseYes.isSelected()) {
            efficiency = mstar * 10;
        }
        txtEffieciency.setText(String.valueOf(efficiency));
    }//GEN-LAST:event_btnCalculateActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        count = 0;
        showScreenshots();
        showCount();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        count = no_of_Images - 1;
        showScreenshots();
        showCount();
    }//GEN-LAST:event_btnLastActionPerformed

    private void ddlSelectEmployerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlSelectEmployerActionPerformed
        // TODO add your handling code here:
        if (ddlSelectEmployer.getSelectedIndex() == 0) {
            ddlSelectProject.setEnabled(false);
            ddlSelectEmployee.setEnabled(false);
            if (objbean.getType().equalsIgnoreCase("Employer")) {
                ddlSelectProject.setEnabled(true);
            } else {
                if (ddlSelectEmployer.getSelectedIndex() == 0) {
                    ddlSelectProject.setEnabled(false);
                } else {
                    ddlSelectProject.setEnabled(true);
                }
            }
            ddlSelectDate.setEnabled(false);
        } else if (ddlSelectEmployer.getSelectedIndex() > 0) {
            showProjectName();
            if (objbean.getType().equalsIgnoreCase("admin")) {
                employer_id = Integer.parseInt((String) ddlSelectEmployer.getSelectedItem());
            }

        }
    }//GEN-LAST:event_ddlSelectEmployerActionPerformed

    private void ddlSelectDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlSelectDateActionPerformed
        // TODO add your handling code here:
        if (ddlSelectDate.getSelectedIndex() == 0) {
            btnShow.setEnabled(false);
        } else if (ddlSelectDate.getSelectedIndex() > 0) {
            btnShow.setEnabled(true);
            date = (String) ddlSelectDate.getSelectedItem();
            try {
                Date date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(date);

                DateFormat df = new SimpleDateFormat("ddMMyyyy");

                date = df.format(date1);

            } catch (Exception e) {
                System.out.println("Date Exception while Converting Selected Date Tracker" + e);
            }
            btnShow.setEnabled(true);

        }
    }//GEN-LAST:event_ddlSelectDateActionPerformed
    private void downloadZipFileAndUnzipIt() {
        try {
            skt = new Socket(Utilities.serverAddress(), Utilities.portAddress());
            dis = new DataInputStream(skt.getInputStream());
            dos = new DataOutputStream(skt.getOutputStream());
            FileOutputStream fos = null;
            dos.writeUTF("EmployerDownload");
            String filename = Integer.toString(employer_id) + Integer.toString(project_id) + Integer.toString(employee_id) + "-" + date;
            //System.out.println("filename"+filename);
            dos.writeUTF(filename);
            boolean flag = dis.readBoolean();

            if (flag) {
                File f = new File(Utilities.EmployeeScreenShotsLocEmployerSide());
                if (!f.exists()) {
                    f.mkdir();
                }
                //System.out.println("kay");
                String fileName = Utilities.EmployeeScreenShotsLocEmployerSide() + filename + ".zip";
                //System.out.println("kay"+filename);
                fos = new FileOutputStream(filename);
                Long filesize = dis.readLong();
                //System.out.println("eefgg"+filesize);
                byte b[] = new byte[4096];
                int a;
                for (long i = 0; i < filesize;) {
                    a = dis.read(b);
                    fos.write(b, 0, a);
                    i = i + a;

                }
                //System.out.println("eefgg");
                fos.close();
                dis.readBoolean();
                //System.out.println(dis.readBoolean());
                f = new File(filename);
                String destFileLoc = Utilities.EmployeeScreenShotsLocEmployerSide() + filename;
                path = destFileLoc + "\\";
                if (f.exists() && !f.isDirectory()) {
                    UnZipFile.UnZipFile(filename, destFileLoc);
                    f.delete();

                }
                f = new File(destFileLoc);
                file = f.list();
                /*   for(int i=0;i<file.length;i++)
                {
                    System.out.println("aa:"+file[i]);
                }*/
                no_of_Images = file.length;
            }
        } catch (Exception e) {
            System.out.println("donwloadZipFileAndUnzipIt() Exception" + e);
        } finally {
            try {

                dis.close();
                dos.close();
                skt.close();

            } catch (Exception e) {
                System.out.println("donwloadZipFileAndUnzipIt() Final Exception" + e);
            }
        }
    }
    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
        downloadZipFileAndUnzipIt();
        showScreenshots();
        showCount();
    }//GEN-LAST:event_btnShowActionPerformed
    private void getRatings() {
        if (ddlEmployeeRatings.getSelectedIndex() != 0) {
            ManageEmployeeServices.setEmployeeRatings(employee_id, ddlEmployeeRatings.getSelectedIndex());
        }
    }
    private void getStatus() {

        if (rbComplete.isSelected()) {
            TaskManager.updateTask(task_id, false);
        } else if (rbIncomplete.isSelected()) {
            TaskManager.updateTask(task_id, true);
        }
    }
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:

        btnSave.setEnabled(true);
        rbComplete.setEnabled(true);
        rbIncomplete.setEnabled(true);
        ddlEmployeeRatings.setEnabled(true);


    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        btnSave.setEnabled(false);
        rbComplete.setEnabled(false);
        rbIncomplete.setEnabled(false);
        ddlEmployeeRatings.setEnabled(false);
        getRatings();
        getStatus();
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel KeyStrokes;
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShow;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> ddlEmployeeRatings;
    private javax.swing.JComboBox<String> ddlSelectDate;
    private javax.swing.JComboBox<String> ddlSelectEmployee;
    private javax.swing.JComboBox<String> ddlSelectEmployer;
    private javax.swing.JComboBox<String> ddlSelectProject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JRadioButton rbComplete;
    private javax.swing.JRadioButton rbIncomplete;
    private javax.swing.JRadioButton rbKeyStrokeNo;
    private javax.swing.JRadioButton rbKeyStrokeYes;
    private javax.swing.JRadioButton rbMouseNo;
    private javax.swing.JRadioButton rbMouseYes;
    private javax.swing.JTextField txtEffieciency;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtKeyStrokeCount;
    private javax.swing.JTextField txtMouseClickCount;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
