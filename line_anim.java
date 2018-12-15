import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.net.*;
/*
<applet code="line_anim" height="400" width="400"></applet>
 */
public class line_anim extends Applet implements Runnable,ActionListener
{
  Thread t;
  int h,w;
  private double ang=0.0;
  Graphics g1;
  Panel button;
  Button clock,anti;
  private int dir;
  
  public void init()
  {
    dir=0;
    t=new Thread(this,"cycle thread");
    t.start();
    button=new Panel();
    setBackground(Color.WHITE);
    setForeground(Color.BLUE);
    makeButton();
  }
  public void run()
  {
    try{
      while(true)
      {
        repaint();
        Thread.sleep(100);
      }
    }
    catch(Exception e)
    {
      System.out.println("Error: "+t+" Interrupted");
    }
  }
  public void paint(Graphics g)
  {
    Dimension d=getSize();
    h=(int)d.getWidth()/2;
    w=(int)d.getHeight()/2;
    g1=g;
    g.setColor(Color.BLACK);
    g.drawOval(w-100,h-100,200,200);
    g.setColor(Color.darkGray);
    if(dir==0)
      anti_clockwise();
    else if(dir==1)
      clockwise();
    ang+=0.1;
  }
  public void makeButton()
  {
    clock=new Button("Clockwise");
    anti=new Button("Anti-Clockwise");
    clock.addActionListener(this);
    anti.addActionListener(this);
    button.add("East",clock);
    button.add("West",anti);
    add("South",button);
  }
  public void clockwise()
  {
    double result=(50)*Math.cos(ang)-(50)*Math.sin(ang);
    double result1=(50)*Math.sin(ang)+(50)*Math.cos(ang);
    result+=w;
    result1+=h;
    g1.drawLine(w,h,(int)result,(int)result1);
  }
  public void anti_clockwise()
  {
    double result=(50)*Math.cos(ang)+(50)*Math.sin(ang);
    double result1=-(50)*Math.sin(ang)+(50)*Math.cos(ang);
    result+=w;
    result1+=h;
    g1.drawLine(w,h,(int)result,(int)result1);
  }
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==clock)
      dir=1;
    if(e.getSource()==anti)
      dir=0;
  }
}
