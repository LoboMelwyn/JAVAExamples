import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.text.*;

class choosers extends JPanel
{
  static JFrame frame;
  private File defaultDirectory=null;
  private Thread loader;
  private JTextArea editor;
  private JScrollPane scroll;
  private Color currentback=Color.WHITE;
  private Color currentfore=Color.BLACK;
  private Color current=null;
  private final JColorChooser chooser = new JColorChooser(current!=null ? current : Color.WHITE);
  private ActionListener okListener;
  
  public static void main(String args[])
  {
    frame=new JFrame("JFileChooser and JColorChooser");
    choosers choose=new choosers();
    frame.getContentPane().add(choose);
    choose.makeGui();
    frame.setSize(500,500);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
  }
  public void makeGui()
  {
    editor=new JTextArea();
    editor.setDragEnabled(true);
    editor.setEditable(false);
    editor.setFont(new Font("Arial",Font.ITALIC,20));
    int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    scroll=new JScrollPane(editor,v,h);
    frame.add(scroll,BorderLayout.CENTER);
    
    JMenuBar menu=new JMenuBar();
    JMenu m=new JMenu("File");
    JMenuItem item;
    item=createMenuItem("Open...",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {chooseFile();
        }
    },KeyEvent.VK_O,InputEvent.CTRL_MASK,'o',0);
    m.add(item);
    menu.add(m);
    item=createMenuItem("Exit",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {System.exit(0);
        }
    },KeyEvent.VK_Q,InputEvent.CTRL_MASK,'x',2);
    m.add(item);
    menu.add(m);
    m=new JMenu("Color");
    item=createMenuItem("Change Background Color",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {chooseBackground();
        }
    },0,0,'B',7);
    m.add(item);
    item=createMenuItem("Change Foreground Color",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {chooseForeground();
        }
    },0,0,'F',7);
    m.add(item);
    menu.add(m);
    frame.setJMenuBar(menu);
    okListener=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        current=chooser.getColor();
      }
    };
  }
  public static JMenuItem createMenuItem(String name,ActionListener a,int acc,int accmodes,char mnemonic,int mnemonicPos)
  {
    JMenuItem item=new JMenuItem(name);
    item.addActionListener(a);
    if((acc!=0) && (accmodes!=0))
      item.setAccelerator(KeyStroke.getKeyStroke(acc,accmodes));
    item.setMnemonic(mnemonic);
    item.setDisplayedMnemonicIndex(mnemonicPos);
    return item;
  }
  public void chooseFile()
  {
    final JFileChooser chooser=new JFileChooser();
    chooser.setMultiSelectionEnabled(false);
    chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){public boolean accept(File f){return(f.isDirectory()||f.getName().endsWith(".txt"));}
        public String getDescription()
        {
          return "Text files(*.txt)";
    }});
    
    chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){public boolean accept(File f){return(f.isDirectory()||f.getName().endsWith(".java"));}
        public String getDescription()
        {
          return "Java Files(*.java)";
    }});
    
    int res=chooser.showOpenDialog(new Frame());
    if(res==JFileChooser.APPROVE_OPTION)
    {
      File file=chooser.getSelectedFile();
      defaultDirectory=file.getParentFile();
      editor.setText("");
      new JTextArea().setDocument(new PlainDocument());
      Thread loader=new openFile(file,editor.getDocument());
      loader.start();
    }
  }
  public void chooseBackground()
  {
    JDialog dialog = JColorChooser.createDialog(new Frame(),"Choose Color for Background",true,chooser,okListener,null);
    dialog.setVisible(true);
    currentback=current;
    editor.setBackground(currentback);
  }
  public void chooseForeground()
  {
    JDialog dialog = JColorChooser.createDialog(new Frame(),"Choose Color for Background",true,chooser,okListener,null);
    dialog.setVisible(true);
    currentfore=current;
    editor.setForeground(currentfore);
  }
}

class openFile extends Thread
{
  private Document doc;
  private File f;
  private StatusBar status=new StatusBar();
  
  openFile(File f,Document doc)
  {
    setPriority(4);
    this.f=f;
    this.doc=doc;
  }
  
  public void run()
  {
    try{
      status.removeAll();
      JProgressBar progress=new JProgressBar();
      progress.setMinimum(0);
      progress.setMaximum((int)f.length());
      status.add(progress);
      status.revalidate();
      
      Reader in=new FileReader(f);
      char[] buff=new char[4096];
      int nch;
      while((nch=in.read(buff,0,buff.length))!=-1)
      {
        doc.insertString(doc.getLength(),new String(buff,0,nch),null);
        progress.setValue(progress.getValue()+nch);
      }
      status.removeAll();
      status.revalidate();
    }
    catch(Exception e)
    {
      final String msg=e.getMessage();
      SwingUtilities.invokeLater(new Runnable(){public void run(){JOptionPane.showMessageDialog(new Frame(),"Could not open File:"+msg,"Error Opening File",JOptionPane.ERROR_MESSAGE);}});
      status.removeAll();
      status.revalidate();
    }
  }
}

class StatusBar extends JComponent
{
  StatusBar()
  {
    super();
    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
  }
  public void paint(Graphics g)
  {
    super.paint(g);
  }
}
