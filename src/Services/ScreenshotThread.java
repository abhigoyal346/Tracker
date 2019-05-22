package Services;

import Beans.CountBean;
import Utility.Utilities;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreenshotThread extends Thread {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hhmmss");
    public String path;
    private int task_id;
    private Random rn;
    public ScreenshotThread(String path,int task_id) {
        this.path = path;
        this.task_id=task_id;
        rn=new Random();
    }

    public void run() {
        try {
            //screen2image si=new screen2image();
            while (true) {
                Calendar now = Calendar.getInstance();
                Robot robot = new Robot();
                Date d=now.getTime();
                BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                String path1 = this.path + formatter.format(d) + ".jpg";
                File f = new File(path1);
                f.getParentFile().mkdirs();
                try {
                    f.createNewFile();
                } catch (Exception e) {
                    System.out.println("ss" + e);
                }
                ImageIO.write(screenShot, "JPG", f);
                //ImageIO.write(screenShot, "JPG", new File(this.path + formatter.format(now.getTime()) + ".jpg"));
                //System.out.println(formatter.format(now.getTime()));
                //si.robo();
                //System.out.println(String.valueOf(CountMouseClicked.mouse_clicked));
                //System.out.println(String.valueOf(CountKeyStrokes.count));
                countEntry(CountKeyStrokes.count,CountMouseClicked.mouse_clicked,d);
                CountKeyStrokes.count=0;
                CountMouseClicked.mouse_clicked=0;
                Thread.sleep((rn.nextInt(11 - 1 + 1) + 1)*10000);
            }
        } catch (Exception e) {
            //hello
        }
        
    }
    private void countEntry(int keystrokes,int mouseclicks,Date d){
        CountBean countbean=new CountBean();
        countbean.setTask_id(this.task_id);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        countbean.setScreen_time(formatter1.format(d));
        countbean.setKeystrokes(keystrokes);
        countbean.setMouseclicks(mouseclicks);
        if(countmanager.addCountData(countbean)){
           // System.out.println("ddaataenteredsuccessfluuy");
        }
        else{
            //System.out.println("datanotenetered");
        }
    }
}
