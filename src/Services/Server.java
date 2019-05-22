/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Utility.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Abhishek Goyal
 */
public class Server {

    public static void main(String ab[]) {
        FileOutputStream fos = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        ServerSocket sskt = null;
        Socket skt = null;
        File file = null;
        try {
            sskt = new ServerSocket(12445);
            System.out.println("waiting for connection");

            while (true) {
                skt = sskt.accept();
                dis = new DataInputStream(skt.getInputStream());
                dos = new DataOutputStream(skt.getOutputStream());
                System.out.println("connected");
                String filename = null;
                String type = dis.readUTF();
                System.out.println("ss" + type);

                if (type.equalsIgnoreCase("EmployeeUpload")) {
                    file = new File(Utilities.serverFileLocation());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    filename = Utilities.serverFileLocation() + dis.readUTF() + ".zip";
                    fos = new FileOutputStream(filename);
                    byte b[] = new byte[4096];
                    int a;
                    while ((a = dis.read(b)) != -1) {
                        fos.write(b, 0, a);

                    }
                    fos.close();
                    //Data Recieved
                } else if (type.equalsIgnoreCase("EmployerDownload")) {

                    filename = Utilities.serverFileLocation() + dis.readUTF() + ".zip";
                    System.out.println("s:"+filename);
                    file = new File(filename);
                    if (file.exists() && !file.isDirectory()) {
                        dos.writeBoolean(true);
                        FileInputStream fis = new FileInputStream(filename);
                        byte b[] = new byte[4096];
                        int a;
                        dos.writeLong(file.length());
                        while ((a = fis.read(b)) != -1) {
                            dos.write(b, 0, a);
                        }
                        dos.flush();
                        dos.writeBoolean(true);
                        fis.close();
                        //Data Sent 
                    } else {
                        dos.writeBoolean(false);
                    }
                } else if (type.equalsIgnoreCase("ProfileImagesDownload")) {
                    //Profile Photo Sent
                    //first login id is taken then checked
                    String image = Utilities.serverProfPhotoLocation() + dis.readUTF();
                    file = new File(image);
                    if (file.exists() && !file.isDirectory()) {
                        FileInputStream fis = new FileInputStream(image);
                        byte b[] = new byte[4096];
                        int a;
                        while ((a = fis.read(b)) != -1) {
                            dos.write(b, 0, a);
                        }
                        fis.close();
                    } else {
                        image = Utilities.serverProfPhotoLocation() + "default.jpg";
                        file = new File(image);
                        if (file.exists() && !file.isDirectory()) {
                            FileInputStream fis = new FileInputStream(image);
                            byte b[] = new byte[4096];
                            int a;
                            while ((a = fis.read(b)) != -1) {
                                dos.write(b, 0, a);
                            }
                            fis.close();
                        }
                    }
                } else if (type.equalsIgnoreCase("ProfileImageUpload")) {
                    //Profile Photo New/change
                    //Login id to which pic need to be changed
                    file = new File(Utilities.serverProfPhotoLocation());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    filename = Utilities.serverFileLocation() + dis.readUTF() + ".jpg";
                    file = new File(filename);
                    if (file.exists() && !file.isDirectory()) {
                        file.delete();
                    }
                    fos = new FileOutputStream(filename);
                    byte b[] = new byte[4096];
                    int a;
                    while ((a = dis.read(b)) != -1) {
                        fos.write(b, 0, a);
                    }
                    fos.close();
                } else if (type.equalsIgnoreCase("EmployerFilesNameDownload")) {
                    file = new File(Utilities.serverFileLocation());
                    int employerid = dis.readInt();
                    int projectid = dis.readInt();
                    int employeeid = dis.readInt();
                    String start = Integer.toString(employerid) + Integer.toString(projectid) + Integer.toString(employeeid) + "-";
                    String[] filesinDir = file.list(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return (name.startsWith(start) && name.endsWith(".zip"));
                        }
                    });
                    ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());
                    oos.writeObject(filesinDir);
                    oos.flush();
                    oos.close();
                }
                skt.close();
                System.out.println("ss");
                
            }
        } catch (Exception e) {

        } finally {
            try {
                dos.close();
                dis.close();
                skt.close();
                sskt.close();
            } catch (Exception e) {

            }
        }
    }
}
