/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oneonone;

/**
 *
 * @author Lenovo
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class User1 implements ActionListener{
    
    JTextField msg_area;
    JPanel lower,upper;
    static Box upright = Box.createVerticalBox();  
    static DataOutputStream out;
    
    static JFrame f = new JFrame();
    
    User1(){
    f.setSize(1000,500);
    f.setLocation(0,0);
    f.setVisible(true);
     
    f.setLayout(null);
    
    upper = new JPanel();
    upper.setBackground(new Color(0,255,255));
    upper.setBounds(0,0,1000,50);
    f.add(upper);
    
    JButton exit = new JButton("EXIT");
    exit.setLayout(null);
    exit.setBounds(888,0,100,60);
    exit.setFont(new Font("",Font.BOLD,20));
    exit.setForeground(Color.white);
    exit.setBackground(Color.black);
    
    upper.add(exit);
    
    exit.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent a)
        {
            System.exit(0);
        }});
    
    JLabel user_name = new JLabel("** USER 1 **");
    user_name.setLayout(null);
    user_name.setBounds(5,0,200,50);
    user_name.setForeground(Color.black);
    user_name.setFont(new Font("",Font.BOLD,30));
    upper.add(user_name);
    
    JLabel active = new JLabel("(ACTIVE)");
    active.setLayout(null);
    active.setBounds(200,5,100,50);
    active.setForeground(Color.black);
    active.setFont(new Font("",Font.BOLD,15));
    upper.add(active);
    
    lower = new JPanel();
    lower.setBounds(0,50,980,400);
    f.add(lower);
    
    msg_area = new JTextField();
    msg_area.setLayout(null);
    msg_area.setBounds(0,400,850,50);
    msg_area.setFont(new Font("",Font.PLAIN,20));
    f.add(msg_area);
    
    JButton push = new JButton("PUSH");
    push.setLayout(null);
    push.setBounds(870,400,100,50);
    push.setBackground(Color.white);
    push.setForeground(Color.black);
    push.setFont(new Font("",Font.BOLD,20));
    push.addActionListener(this);
    f.add(push);
    
    }
    
    public void actionPerformed(ActionEvent e){
       try{
        String msg = msg_area.getText();
        
        JLabel msg_label = new JLabel(msg);
        msg_label.setFont(new Font("",Font.PLAIN,20));
        
        JPanel ping = formatLabel(msg);
       
        lower.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(ping, BorderLayout.LINE_END);
        upright.add(right);
        upright.add(Box.createVerticalStrut(15));
        
        lower.add(upright,BorderLayout.PAGE_START);
        msg_area.setText("");
        
        
        out.writeUTF(msg);
        
        
        f.repaint();
        f.invalidate();
        f.validate();
       }
        catch(Exception ee){
        System.out.println(ee);
        }
        
    }
    
    public static JPanel formatLabel(String msg){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        
        JLabel out = new JLabel("<html><p style=\"width:150px\">"+msg+"<p></html>");
        out.setFont(new Font("",Font.PLAIN,20));
        out.setBackground(Color.yellow);
        out.setOpaque(true);
        out.setBorder(new EmptyBorder(15,15,15,50));
        p.add(out);
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
        
        JLabel t = new JLabel();
        
        t.setText(s.format(c.getTime()));
        
        p.add(t);
        
        return p;
    }
    
    public static void main(String ar[]){
        new User1();
        
        
        try{
            ServerSocket s = new ServerSocket(6001);
            while(true){
                Socket ss = s.accept();
                DataInputStream in = new DataInputStream(ss.getInputStream());
                out = new DataOutputStream(ss.getOutputStream());
                
                
                while(true){
                    String chat = in.readUTF();
                    JPanel panel = formatLabel(chat);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    upright.add(left);
                    f.validate();
                    
                }
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
