import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class sudoku extends JPanel
{
  static JFrame frame;
  static sudoku_panel s;
  
  public static void main(String args[])
  {
    frame=new JFrame("Sudoku Game");
    s=new sudoku_panel();
    sudoku su=new sudoku();
    su.gui();
    frame.getContentPane().add(s);
    frame.setSize(600,600);
    frame.setDefaultCloseOperation(3);
    frame.setResizable(false);
    frame.show();
    frame.setBackground(Color.yellow);
  }
  
  sudoku()
  {
    setBorder(BorderFactory.createEtchedBorder());
    setLayout(new BorderLayout());
  }
  
  public void gui()
  {
    JMenuBar menu=new JMenuBar();
    JMenu m=createMenu("Game",'G',0);
    JMenuItem item=createMenuItem("New Game",new ActionListener()
    {public void actionPerformed(ActionEvent e){newGame();}},KeyEvent.VK_F2,0,'N',0);
    m.add(item);
    item=createMenuItem("Exit",new ActionListener()
    {public void actionPerformed(ActionEvent e){System.exit(0);}},KeyEvent.VK_F4,InputEvent.ALT_MASK,'E',0);
    m.add(item);
    menu.add(m);
    frame.setJMenuBar(menu);
  }
  
  public static JMenu createMenu(String name,char mnemonic,int mnemonicpos)
  {
    JMenu menu=new JMenu(name);
    menu.setMnemonic(mnemonic);
    menu.setDisplayedMnemonicIndex(mnemonicpos);
    return menu;
  }
  
  public static JMenuItem createMenuItem(String name,ActionListener a,int acc,int accmodes,char mnemonic,int mnemonicPos)
  {
    JMenuItem item=new JMenuItem(name);
    item.addActionListener(a);
    item.setAccelerator(KeyStroke.getKeyStroke(acc,accmodes));
    item.setMnemonic(mnemonic);
    item.setDisplayedMnemonicIndex(mnemonicPos);
    return item;
  }
  
  void newGame()
  {
    s.destroy();
    s.makeBoard();
  }
  
}