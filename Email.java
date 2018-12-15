package my_project;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Email extends JPanel implements ActionListener
{
  JTextField from,to,smtpServer;
  JTextArea message,communication;
  JButton ok,cancel;
  JScrollPane jspmess,jspcomm;
  JPanel p;
  PrintWriter out;
  BufferedReader in;
  
  public Email()
  {
    from=new JTextField(20);
    to=new JTextField(20);
    smtpServer=new JTextField(20);
    message=new JTextArea(5,10);
    communication=new JTextArea(5,10);
    ok=new JButton("OK");
    cancel=new JButton("CANCEL");
    p=new JPanel(new BorderLayout());
    makeGUI();
  }
  
  void makeGUI()
  {
    JLabel lblfrom=new JLabel("FROM: ");
    JLabel lblto=new JLabel("TO: ");
    JLabel lblsmtp=new JLabel("SMTP SERVER: ");
    
    JPanel p1=new JPanel(new FlowLayout());
    JPanel p2=new JPanel(new FlowLayout());
    JPanel p3=new JPanel(new FlowLayout());
    
    p1.add(lblfrom);p1.add(from);
    p2.add(lblto);p2.add(to);
    p3.add(lblsmtp);p3.add(smtpServer);
    
    JPanel p4=new JPanel(new BorderLayout());
    p4.add(p1,"North");
    p4.add(p2,"Center");
    p4.add(p3,"South");
    
    JPanel p5=new JPanel(new FlowLayout());
    p5.add(ok);p5.add(cancel);
    ok.addActionListener(this);
    cancel.addActionListener(this);
    
    JPanel p6=new JPanel(new BorderLayout());
    //message.addActionListener(this);
    jspmess=new JScrollPane(message);
    jspcomm=new JScrollPane(communication);
    communication.setEditable(false);
    p6.add(jspmess,"North");
    p6.add(p5,"Center");
    p6.add(jspcomm,"South");
    
    p.add(p4,"North");
    p.add(p6,"Center");
    add(p);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==ok)
    {
      sendMail();
    }
    else if(e.getSource()==cancel)
    {
      System.exit(0);
    }
  }
  
  public void sendMail()
  {
    try{
      Socket s=new Socket(smtpServer.getText(),25);
      out=new PrintWriter(s.getOutputStream());
      in=new BufferedReader(new InputStreamReader(s.getInputStream()));
      String hostname=InetAddress.getLocalHost().getHostName();
      receive();
      send("HELLO"+hostname);
      receive();
      send("MAIL FROM: <"+from.getText()+">");
      receive();
      send("RCPT TO: <"+to.getText()+">");
      receive();
      send("DATA");
      receive();
      StringTokenizer token=new StringTokenizer(message.getText());
      while(token.hasMoreTokens())
        send(token.nextToken());
      send(".");
      receive();
      s.close();
    }
    catch(Exception e)
    {
      communication.append("ERROR: "+e);
    }
  }
  
  public void send(String s)throws Exception
  {
    communication.append(s);
    communication.append("\n");
    out.print(s);
    out.print("\r\n");
    out.flush();
  }
  
  public void receive()throws Exception
  {
    String line=in.readLine();
    if(line!=null)
    {
      communication.append(line);
      communication.append("\n");
    }
  }
  
  public static void main(String args[])
  {
    Email e=new Email();
    JFrame frame=new JFrame("Email app");
    frame.getContentPane().add(e,"Center");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,400);
    frame.setResizable(false);
  }
  
}