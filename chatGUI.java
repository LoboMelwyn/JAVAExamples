package my_project.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class chatGUI extends JPanel implements ActionListener,Runnable
{
  //login screen
  Thread t;
  JPanel loginScreen;
  JLabel user;
  JTextField username;
  JButton login;
  JButton exit;
  String name;
  
  //make chat screen
  JPanel chatScreen;
  JLabel conn;
  JTextField connect;
  JButton connected;
  JTextArea chat;
  JScrollPane jsp;
  JLabel message;
  JTextField sendMessage;
  JButton ok;
  JButton logout;
  String Message,ip;
  
  Socket s;
  
  public void run()
  {
    try{
      while(true)
      {
        messageReceived();
        Thread.sleep(500);
      }
    }
    catch(Exception e)
    {
      System.out.println("Thread Error: "+e);
    }
  }
  
  chatGUI()
  {
    System.out.println("chat gui started");
    loginScreen=new JPanel(new BorderLayout());
    chatScreen=new JPanel(new BorderLayout());
    loginScreen.setVisible(true);
    chatScreen.setVisible(false);
    makeChatScreen();
    loginScreen();
  }
  
  public void loginScreen()
  {
    JPanel p=new JPanel(new FlowLayout());
    user=new JLabel("Please Enter User Name:");
    username=new JTextField(10);
    username.addActionListener(this);
    login=new JButton("Login");
    login.addActionListener(this);
    exit=new JButton("Exit");
    exit.addActionListener(this);
    p.add(user);
    p.add(username);
    p.add(login);
    p.add(exit);
    loginScreen.add(p,"Center");
    add(loginScreen);
  }
  
  public void makeChatScreen()
  {
    JPanel p=new JPanel(new FlowLayout());
    conn=new JLabel("Enter the IP Address which you have to connect");
    connect=new JTextField(20);
    connect.addActionListener(this);
    connected=new JButton("Connect");
    connected.addActionListener(this);
    p.add(conn);
    p.add(connect);
    p.add(connected);
    
    JPanel p1=new JPanel(new BorderLayout());
    chat=new JTextArea(10,10);
    chat.setEditable(false);
    jsp=new JScrollPane(chat);
    message=new JLabel("Enter Your Message Here:");
    sendMessage=new JTextField(30);
    sendMessage.addActionListener(this);
    p1.add(jsp,"North");
    p1.add(message,"Center");
    p1.add(sendMessage,"South");
    
    JPanel p2=new JPanel(new FlowLayout());
    ok=new JButton("OK");
    ok.addActionListener(this);
    logout=new JButton("Logout");
    logout.addActionListener(this);
    p2.add(ok);
    p2.add(logout);
    
    chatScreen.add(p,"North");
    chatScreen.add(p1,"Center");
    chatScreen.add(p2,"South");
    add(chatScreen);
  }
  
  public static void main(String args[])
  {
    JFrame frame=new JFrame("Melwyn Chat");
    chatGUI g=new chatGUI();
    frame.getContentPane().add("Center",g);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,400);
    frame.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==login)
    {
      name=username.getText();
      if(name.length()!=0)
      {
        try{
          System.out.println("username "+name+" has login");
          s=new Socket("localhost",2000);
          loginScreen.setVisible(false);
          chatScreen.setVisible(true);
          username.setText("");
          t=new Thread(this,"chatThread");
          t.start();
        }
        catch(Exception error)
        {
          JOptionPane.showMessageDialog(null,error,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
      }
      else
        JOptionPane.showMessageDialog(null,"No User Name Entered","ERROR",JOptionPane.ERROR_MESSAGE);
    }
    else if(e.getSource()==logout)
    {
      try{
        loginScreen.setVisible(true);
        chatScreen.setVisible(false);
        conn.setVisible(true);
        connect.setVisible(true);
        connected.setVisible(true);
        chat.setText("");
        sendMessage.setText("");
        s.close();
      }
      catch(Exception error)
      {
        System.out.println("ERROR: "+e);
        System.exit(0);
      }
    }
    else if(e.getSource()==exit)
    {
      System.exit(0);
    }
    else if(e.getSource()==ok)
    {
      try{
        Message=sendMessage.getText();
        if(Message.length()==0)
          JOptionPane.showMessageDialog(null,"No Message Entered","ERROR",JOptionPane.ERROR_MESSAGE);
        else{
          System.out.println("Message: "+Message);
          chat.append(Message);
          chat.append("\n -------------------------------------------------- \n");
          PrintWriter out1=new PrintWriter(s.getOutputStream(),true);
          out1.println(Message);
          sendMessage.setText("");
        }
      }
      catch(Exception error)
      {
        JOptionPane.showMessageDialog(null,error,"ERROR",JOptionPane.ERROR_MESSAGE);
      }
    }
    else if(e.getSource()==connected)
    {
      ip=connect.getText();
      connect.setVisible(false);
      conn.setVisible(false);
      connected.setVisible(false);
      System.out.println(ip+" is entered");
    }
  }
  
  void messageReceived()throws Exception
  {
    BufferedReader in1=new BufferedReader(new InputStreamReader(s.getInputStream()));
    String serverMessage=in1.readLine();
    if(serverMessage.length()!=0)
    {
      System.out.println("Message Received: "+serverMessage);
      chat.append(serverMessage);
      chat.append("\n -------------------------------------------------- \n");
      chat.setCaretPosition(chat.getText().length());
    }
  }
  
}