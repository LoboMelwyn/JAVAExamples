import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MDIForm extends JFrame
{
  private JDesktopPane desktop;
  private int count=0;
  private String flavour[]={"Vanilla","Butter Scotch","Chocolate","Strawberry"};
  private String color[]={"Red","Blue","Violet","Green","Yelow"};
  private String cities[]={"Mumabi","Delhi","Chennai","Kolkata"};
  
  MDIForm()
  {
    super("MDI form example");
    int inset=50;
    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
    int w=d.width/2;
    int h=d.height/2;
    setSize(w,h);
    setBounds(inset, inset, w - inset * 2,h - inset * 2);
    setDefaultCloseOperation(3);
    desktop=new JDesktopPane();
    setContentPane(desktop);
    desktop.putClientProperty("JDesktopPane.dragMode", "outline");
    setJMenuBar(createMenuBar());
  }
  private JMenuBar createMenuBar()
  {
    JMenuBar menubar=new JMenuBar();
    JMenu menu=new JMenu("Create new Frame");
    JMenuItem item = new JMenuItem("New Flavour Frame");
    item.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {createFrame("Flavor's of Ice-cream",flavour);}});
    menu.add(item);
    item = new JMenuItem("New Colour Frame");
    item.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e1) {createFrame("Colour's of Rainbow",color);}});
    menu.add(item);
    item = new JMenuItem("New City Frame");
    item.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e2) {createFrame("Cities of India",cities);}});
    menu.add(item);
    item = new JMenuItem("Exit");
    item.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e2) {System.exit(1);}});
    menu.add(item);
    menubar.add(menu);
    return menubar;
  }
  private void createFrame(String name,String key[])
  {
    JInternalFrame jif=new JInternalFrame(name);
    jif.setLayout(new GridLayout());
    jif.setSize(500,100);
    try {
      jif.setSelected(true);
      } catch (java.beans.PropertyVetoException e) {
    }
    jif.setLocation(10*count,10*count);
    count++;
    for(int i=0;i<key.length;i++)
    {
      JLabel temp=new JLabel(key[i]);
      jif.add(temp);
    }
    jif.setResizable(true);
    jif.setClosable(true);
    jif.setMaximizable(true);
    jif.setIconifiable(true);
    jif.setVisible(true);
    desktop.add(jif);
  }
  public static void main(String args[])
  {
    MDIForm frame=new MDIForm();
    frame.setVisible(true);
  }
}
