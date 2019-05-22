/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Beans.CountBean;
import Beans.ProjectBean;
import Beans.TaskBean;
import Services.CountKeyStrokes;
import Services.CountMouseClicked;
import Services.ProjectManagerServices;
import Services.ScreenshotThread;
import Services.TaskManager;
import Services.countmanager;
import Utility.Utilities;
import ZipperUnzipper.ZipFile;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.net.Socket;

/**
 *
 * @author dell
 */
public class TaskScreenshot extends javax.swing.JPanel {

    /**
     * Creates new form TaskScreenshot
     */
    private int project_id;
    private int task_id;
    private boolean task_status;
    private ScreenshotThread st;
    private File[] files;
    private File SubDirectpryFolder;
    private File lastScreenshot;
    private Socket skt;
    private DataOutputStream dos;
    private FileInputStream fis;

    public TaskScreenshot(int project_id, int task_id) {

        initComponents();
        skt=null;
        dos=null;
        fis=null;
        this.project_id = project_id;
        this.task_id = task_id;
        this.task_status = TaskManager.getTaskStatus(task_id);

        files = getFileDirectory();
        if (files != null) {
            SubDirectpryFolder = getLastModifiedFile();
            files = getLastScreenshotSubMainDirectoryFiles();
            lastScreenshot = getLastModifiedFile();
            showScreenshotsAndCountsWhenTaskStartEntered();
        }
        if (!this.task_status) {
            ddlStatus.setEnabled(false);
            ddlStatus.setSelectedIndex(1);
            btnOn.setEnabled(false);
            btnOff.setEnabled(false);

        } else {
            ddlStatus.setEnabled(true);//signifies task is complete 
            ddlStatus.setSelectedIndex(0);
            btnOn.setEnabled(true);
            btnOff.setEnabled(true);
        }

        enableFields(false);
        showRecord();

    }

    private void enableFields(boolean flag) {
        txtProjectID.setEditable(flag);
        txtProjectName.setEditable(flag);
        txtMouseClicks.setEditable(flag);
        txtKeystrokes.setEditable(flag);
        btnSave.setEnabled(false);

    }

    private void showRecord() {
        txtProjectID.setText(String.valueOf(this.project_id));
        ProjectBean objbean = ProjectManagerServices.getSingleProject(project_id);
        txtProjectName.setText(objbean.getProject_name());

    }

    private String getScreenshotDirectoryPath() {
        TaskBean tbean = TaskManager.getTask(this.task_id);
        String path = Utilities.screenshotStoreDrive() + tbean.getEmp_id() + "\\" + tbean.getProject_id() + "\\" + tbean.getEmp_id() + tbean.getProject_id() + tbean.getEmployee_id() + "-";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Date d = new Date();
        path = path + sdf.format(d) + "\\";
        return path;
    }

    private File getLastModifiedFile() {
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    private String getLastScreenshotMainDirectory() {
        TaskBean tbean = TaskManager.getTask(this.task_id);
        String path = Utilities.screenshotStoreDrive() + tbean.getEmp_id() + "\\" + tbean.getProject_id();
        return path;

    }

    private String getLastScreenshotSubMainDirectory() {
        //Though getLastScreenShotSubMainFDIrectory funtion having Name Convention Problem but here it is fullfilling Requirements that why name is not chnaged
        TaskBean tbean = TaskManager.getTask(this.task_id);
        String path = Integer.toString(tbean.getEmp_id()) + Integer.toString(tbean.getProject_id()) + Integer.toString(tbean.getEmployee_id());
        return path;
    }
    private String fileName(){
        TaskBean tbean = TaskManager.getTask(this.task_id);
        String temp=Integer.toString(tbean.getEmp_id()) + Integer.toString(tbean.getProject_id()) + Integer.toString(tbean.getEmployee_id())+"-";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Date d = new Date();
        temp= temp + sdf.format(d);
        return temp;
    }
    private String zipFilePath() {
        
        String path = "C:\\Users\\Abhishek\\Documents\\NetBeansProjects\\PikaTracker\\" +fileName()+".zip";
        
        return path;
    }

    private File[] getLastScreenshotSubMainDirectoryFiles() {
        File[] files = SubDirectpryFolder.listFiles();
        return files;
    }

    private void showScreenshotsAndCountsWhenTaskStartEntered() {
        Utilities.setImageEmployee(lbImg1, lastScreenshot.toString());
        CountBean cb = countmanager.getAllCount_taksid(task_id);
        txtMouseClicks.setText(String.valueOf(cb.getMouseclicks()));
        txtKeystrokes.setText(String.valueOf(cb.getKeystrokes()));
    }

    private File[] getFileDirectory() {
        File dir = new File(getLastScreenshotMainDirectory());
        File[] files = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().startsWith(getLastScreenshotSubMainDirectory())) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return files;
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
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbImg1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMouseClicks = new javax.swing.JTextField();
        txtKeystrokes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProjectName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtProjectID = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOn = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnOff = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ddlStatus = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Screenshot & Count", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Last SceeenShot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Mouse Clicks");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Keystrokes");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Project Name");

        txtProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProjectNameActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Project ID");

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtMouseClicks, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(79, 79, 79)
                                        .addComponent(btnCancel))
                                    .addComponent(txtKeystrokes, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(49, 49, 49)
                                .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel6)
                                .addGap(27, 27, 27)
                                .addComponent(txtProjectID, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtProjectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMouseClicks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnCancel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKeystrokes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        buttonGroup1.add(btnOn);
        btnOn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnOn.setText("ON");
        btnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setPreferredSize(new java.awt.Dimension(178, 30));

        jLabel4.setText("Task Manager");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(549, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        buttonGroup1.add(btnOff);
        btnOff.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnOff.setText("Off");
        btnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOffActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Task Activity");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Status");

        ddlStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "InProgress", "Complete" }));
        ddlStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddlStatusActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOn)
                        .addGap(18, 18, 18)
                        .addComponent(btnOff))
                    .addComponent(ddlStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnOn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOff, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ddlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProjectNameActionPerformed

    private void btnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnActionPerformed

        try {
            // TaskBean tbean=TaskManager.getSingleTask(this.employee_id);
            st = new ScreenshotThread(getScreenshotDirectoryPath(), this.task_id);
            st.start();
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new CountKeyStrokes());
        CountMouseClicked example = new CountMouseClicked();
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
    }//GEN-LAST:event_btnOnActionPerformed

    private void btnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOffActionPerformed

        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
        Date d = new Date();
        try {
            st.stop();
            GlobalScreen.unregisterNativeHook();
            ZipFile.Zip(getScreenshotDirectoryPath());
            skt = new Socket(Utilities.serverAddress(), Utilities.portAddress());
            System.out.println("connected client side");
            dos = new DataOutputStream(skt.getOutputStream());
            dos.writeUTF("EmployeeUpload");
            dos.writeUTF(fileName());
            File myFile = new File(zipFilePath());
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            byte b[] = new byte[4096];
            int a;
            while ((a = fis.read(b)) != -1) {
                dos.write(b, 0, a);
            }
            dos.flush();
            fis.close();

        } catch (NativeHookException e) {
            System.out.println("NativeHookException in TakeScreenshot" + e);

        } catch (Exception e) {
            System.out.println("Exception in TakeScreenshot" + e);
        } finally {
            try {

                
                dos.close();
                skt.close();
            } catch (Exception e) {
                System.out.println("Exception in TakeScreenshot Socket Related" + e);
            }
        }
        CountBean cb = countmanager.getAllCount_taksid(task_id);
        File dir = new File(getScreenshotDirectoryPath());
        files = dir.listFiles();
        lastScreenshot = getLastModifiedFile();
        showScreenshotsAndCountsWhenTaskStartEntered();
    }//GEN-LAST:event_btnOffActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if (!btnOff.isEnabled()) {
            btnOff.setEnabled(true);
        }
        MainFrame.c.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        TaskManager.updateTask(task_id, false);
        btnOff.setEnabled(false);
        btnOn.setEnabled(false);
        btnSave.setEnabled(false);

    }//GEN-LAST:event_btnSaveActionPerformed

    private void ddlStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddlStatusActionPerformed
        // TODO add your handling code here:
        if (ddlStatus.getSelectedIndex() == 1) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }//GEN-LAST:event_ddlStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JRadioButton btnOff;
    private javax.swing.JRadioButton btnOn;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JComboBox<String> ddlStatus;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbImg1;
    private javax.swing.JTextField txtKeystrokes;
    private javax.swing.JTextField txtMouseClicks;
    private javax.swing.JTextField txtProjectID;
    private javax.swing.JTextField txtProjectName;
    // End of variables declaration//GEN-END:variables
}
