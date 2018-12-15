import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.applet.Applet;

public class sudoku_panel extends Applet implements MouseListener,KeyListener
{
  int width,height,x,y;
  Graphics g;
  
  public void paint(Graphics g)
  {
    Dimension d=getSize();
    width=(int)d.getWidth();
    height=(int)d.getHeight();
    makeBoard();
    addMouseListener(this);
    addKeyListener(this);
  }
  
  void makeBoard()
  {
    g=getGraphics();
    g.setColor(Color.yellow);
    g.fillRect(0,0,width,height);
    g.setColor(Color.black);
    
    g.drawLine(width/9,0,width/9,height);
    g.drawLine(2*width/9,0,2*width/9,height);
    g.drawLine(width/3,0,width/3,height);
    g.drawLine(width/3+1,0,width/3+1,height);
    
    g.drawLine(4*width/9,0,4*width/9,height);
    g.drawLine(5*width/9,0,5*width/9,height);
    g.drawLine(2*width/3,0,2*width/3,height);
    g.drawLine(2*width/3+1,0,2*width/3+1,height);
    
    g.drawLine(7*width/9,0,7*width/9,height);
    g.drawLine(8*width/9,0,8*width/9,height);
    
    g.drawLine(0,height/9,width,height/9);
    g.drawLine(0,2*height/9,width,2*height/9);
    g.drawLine(0,height/3,width,height/3);
    g.drawLine(0,height/3+1,width,height/3+1);
    
    g.drawLine(0,4*height/9,width,4*height/9);
    g.drawLine(0,5*height/9,width,5*height/9);
    g.drawLine(0,2*height/3,width,2*height/3);
    g.drawLine(0,2*height/3+1,width,2*height/3+1);
    
    g.drawLine(0,7*height/9,width,7*height/9);
    g.drawLine(0,8*height/9,width,8*height/9);
  }
  
  void drawSelect(int x,int y)
  {
    makeBoard();
    x=x*width/9;
    y=y*height/9;
    g.setColor(Color.orange);
    g.fillRect(x,y,width/9,height/9);
    g.setColor(Color.red);
    g.drawRect(x,y,width/9,height/9);
  }
  
  void drawChar(String draw)
  {
    Font f=new Font("Times New Roman",Font.BOLD,48);
    g.setColor(Color.red);
    x=(x*width/9)+width/25;
    y=(y+1)*height/9-height/50;
    g.setFont(f);
    g.drawString(draw,x,y);
    System.out.println(draw);
  }
  
  public void destroy()
  {
    removeMouseListener(this);
    removeKeyListener(this);
  }
  
  public void keyTyped(KeyEvent k)
  {
    if(k.getKeyChar()==k.VK_1)
      drawChar("1");
    if(k.getKeyChar()==k.VK_2)
      drawChar("2");
    if(k.getKeyChar()==k.VK_3)
      drawChar("3");
    if(k.getKeyChar()==k.VK_4)
      drawChar("4");
    if(k.getKeyChar()==k.VK_5)
      drawChar("5");
    if(k.getKeyChar()==k.VK_6)
      drawChar("6");
    if(k.getKeyChar()==k.VK_7)
      drawChar("7");
    if(k.getKeyChar()==k.VK_8)
      drawChar("8");
    if(k.getKeyChar()==k.VK_9)
      drawChar("9");
    if(k.getKeyChar()==k.VK_UP)
    {
      System.out.println("up");
    }
    if(k.getKeyChar()==k.VK_DOWN)
    {
      System.out.println("down");
    }
    if(k.getKeyChar()==k.VK_LEFT)
    {
      System.out.println("left");
    }
    if(k.getKeyChar()==k.VK_RIGHT)
    {
      System.out.println("right");
    }
  }
  
  public void keyPressed(KeyEvent k){}
  
  public void keyReleased(KeyEvent k){}
  
  public void mouseClicked(MouseEvent e)
  {
    x=(9*e.getX()/width);
    y=(9*e.getY()/height);
    drawSelect(x,y);
  }
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
}