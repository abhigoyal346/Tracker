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
public class CountBean {
    private int s_no,task_id,keystrokes,mouseclicks;
    private String screen_time;

    public int getS_no() {
        return s_no;
    }

    public void setS_no(int s_no) {
        this.s_no = s_no;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getKeystrokes() {
        return keystrokes;
    }

    public void setKeystrokes(int keystrokes) {
        this.keystrokes = keystrokes;
    }

    public int getMouseclicks() {
        return mouseclicks;
    }

    public void setMouseclicks(int mouseclicks) {
        this.mouseclicks = mouseclicks;
    }

    public String getScreen_time() {
        return screen_time;
    }

    public void setScreen_time(String screen_time) {
        this.screen_time = screen_time;
    }
    
}
