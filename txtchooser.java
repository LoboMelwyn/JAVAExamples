/*
create a folder with name app inside that folder create a file named txtchooser.properties
In that files write the following things
Title=MPad
 */

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.*;
import javax.swing.undo.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.net.URL.*;
import java.io.*;
import java.net.*;

class txtchooser extends JPanel
{
  private static ResourceBundle resources;
  static
  {
    try{
      resources=ResourceBundle.getBundle("app.txtchooser",Locale.getDefault());
    }
    catch(MissingResourceException mre)
    {
      System.err.println("TXReader.properties not found");
      System.exit(1);
    }
  }
  static JFrame f;
  public JTextArea editor;
  public JScrollPane scroll;
  public JComponent status;
  public static JDialog d;
  // Possible Look & Feels
  private static final String mac="com.sun.java.swing.plaf.mac.MacLookAndFeel";
  private static final String nimbus="javax.swing.plaf.nimbus.NimbusLookAndFeel";
  private static final String metal="javax.swing.plaf.metal.MetalLookAndFeel";
  private static final String motif="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  private static final String windows="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  private static final String gtk="com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
  
  public static void main(String args[])
  {
    txtchooser choose=new txtchooser();
    f=new JFrame(resources.getString("Title"));
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add("Center",choose);
    choose.gui();
    f.add("South",choose.createStatusbar());
    choose.theme(nimbus);
    Image img=Toolkit.getDefaultToolkit().getImage("icon.png");
    f.setIconImage(img);
    f.pack();
    f.setSize(500,600);
    f.setVisible(true);
  }
  
  txtchooser()
  {
    super(true);
    setBorder(BorderFactory.createEtchedBorder());
    setLayout(new BorderLayout());
  }
  
  public void gui()
  {
    editor=getEditor();
    JMenuBar menu=new JMenuBar();
    JMenu m=createMenu("File",'F',0);
    
    JMenuItem item=createMenuItem("New",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {newFile();
        }
    },KeyEvent.VK_N,InputEvent.CTRL_MASK,'n',0);
    m.add(item);
    item=createMenuItem("Open...",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {openFile();
        }
    },KeyEvent.VK_O,InputEvent.CTRL_MASK,'o',0);
    m.add(item);
    m.addSeparator();
    item=createMenuItem("Save",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {saveFile();
        }
    },KeyEvent.VK_S,InputEvent.CTRL_MASK,'s',0);
    m.add(item);
    item=createMenuItem("Exit",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {System.exit(0);
        }
    },KeyEvent.VK_Q,InputEvent.CTRL_MASK,'x',2);
    m.add(item);
    menu.add(m);
    m=createMenu("Format",'F',1);
    item=createMenuItem("Font...",new ActionListener()
      {public void actionPerformed(ActionEvent e)
        {pickFont p=new pickFont();
          p.init();
        }
    },'x',3);
    m.add(item);
    menu.add(m);
    
    m=createMenu("Theme",'T',0);
    item=createMenuItem("Metal",new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          theme(metal);
        }
    },'M',0);
    m.add(item);
    item=createMenuItem("Nimbus",new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          theme(nimbus);
        }
    },'W',0);
    m.add(item);
    m.addSeparator();
    UIManager.LookAndFeelInfo[] lafinfo=UIManager.getInstalledLookAndFeels();
    for (int counter = 0; counter < lafinfo.length; counter++) {
      String className = lafinfo[counter].getClassName();
      if (className == motif) {
        item=createMenuItem("Motif",new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              theme(motif);
            }
        },'M',0);
        m.add(item);
      }
      else if(className==windows){
        item=createMenuItem("Windows",new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              theme(windows);
            }
        },'W',0);
        m.add(item);
      }
      else if(className==gtk){
        item=createMenuItem("GTK+ Theme",new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              theme(gtk);
            }
        },'G',0);
        m.add(item);
      }
      else if(className==mac){
        item=createMenuItem("Mac Theme",new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              theme(mac);
            }
        },'M',0);
        m.add(item);
      }
    }
    
    menu.add(m);
    
    f.setJMenuBar(menu);
    scroll=createJscrollpane();
    f.add(scroll,BorderLayout.CENTER);
  }
  
  public JTextArea getEditor()
  {
    JTextArea editor=new JTextArea();
    editor.setDragEnabled(true);
    editor.setFont(new Font("Arial",Font.ITALIC,20));
    return editor;
  }
  
  public JComponent createStatusbar()
  {
    status=new StatusBar();
    return status;
  }
  
  public JScrollPane createJscrollpane()
  {
    int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane c=new JScrollPane(editor,v,h);
    return c;
  }
  
  public static JMenu createMenu(String name,char mnemonic,int mnemonicpos)
  {
    JMenu menu=new JMenu(name);
    menu.setMnemonic(mnemonic);
    menu.setDisplayedMnemonicIndex(mnemonicpos);
    return menu;
  }
  
  public static JMenuItem createMenuItem(String name,ActionListener a,char mnemonic,int mnemonicPos)
  {
    JMenuItem item=new JMenuItem(name);
    item.addActionListener(a);
    item.setMnemonic(mnemonic);
    item.setDisplayedMnemonicIndex(mnemonicPos);
    return item;
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
  
  public void changeFont(Font font)
  {
    editor.setFont(font);
  }
  
  private void theme(String laf)
  {
    try {
      UIManager.setLookAndFeel(laf);
      SwingUtilities.updateComponentTreeUI(f);
    }
    catch (Exception excep) {
      JOptionPane.showMessageDialog(new Frame(),"ERROR: "+excep,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void saveFile()
  {
    Frame frame=new Frame();
    frame.setTitle("Save....");
    JFileChooser chooser=new JFileChooser();
    int ret=chooser.showSaveDialog(frame);
    
    if(ret!=JFileChooser.APPROVE_OPTION)
      {return;}
    
    File f=chooser.getSelectedFile();
    Thread saver=new FileSaver(f,editor.getDocument());
    saver.start();
  }
  
  public void openFile()
  {
    File defaultDirectory;
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
  
  public void newFile()
  {
    getEditor().setDocument(new PlainDocument());
    editor.setText("");
    revalidate();
  }
  
  /***************************************************
   ******************Class Pick Font*********************
   ****************************************************/
  class pickFont extends JPanel implements ActionListener
  {
    String item[]={"Arial","Courier New","Arial Black","Chiller"};
    String style[]={"BOLD","REGULAR","ITALIC"};
    String fo,ty;
    int si;
    private JTextArea sample;
    private JButton ok;
    private JButton cancel;
    private JComboBox l;
    private JComboBox cbstyle;
    private JComboBox fontsize;
    private Font font;
    private pickFont p;
    
    public void init()
    {
      d=new JDialog();
      p=new pickFont();
      d.setTitle("Font...");
      d.setSize(310,150);
      d.setResizable(false);
      d.add(p);
      p.makeGraphics();
      d.setVisible(true);
    }
    
    public void makeGraphics()
    {
      setLayout(new FlowLayout());
      
      int len=item.length;
      JPanel left=new JPanel(new BorderLayout());
      JPanel center=new JPanel(new BorderLayout());
      JPanel right=new JPanel(new BorderLayout());
      JPanel south=new JPanel(new BorderLayout());
      JLabel font=new JLabel("Font Name");
      JLabel fontype=new JLabel("Font Style");
      JLabel size=new JLabel("Size");
      l=new JComboBox();
      cbstyle=new JComboBox();
      fontsize=new JComboBox();
      ok=new JButton("OK");
      cancel=new JButton("Cancel");
      ok.addActionListener(this);
      cancel.addActionListener(this);
      sample=new JTextArea(1,6);
      sample.setEditable(false);
      sample.setText("AaBbCc");
      
      for(int i=0;i<len;i++)
      {
        l.addItem(item[i]);
      }
      len=style.length;
      for(int i=0;i<len;i++)
      {
        cbstyle.addItem(style[i]);
      }
      len=60;
      for(int i=10;i<=len;i++)
      {
        fontsize.addItem(i);
      }
      
      left.add("North",font);
      left.add("Center",l);
      center.add("North",fontype);
      center.add("Center",cbstyle);
      right.add("North",size);
      right.add("Center",fontsize);
      south.add("North",sample);
      south.add("West",ok);
      south.add("East",cancel);
      add("West",left);
      add("Center",center);
      add("East",right);
      add("South",south);
    }
    
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource()==ok)
      {
        fo=(String)l.getSelectedItem();
        ty=(String)cbstyle.getSelectedItem();
        si=(Integer)fontsize.getSelectedItem();
        if(ty.equals("BOLD"))
          font=new Font(fo,Font.BOLD,si);
        else if(ty.equals("REGULAR"))
          font=new Font(fo,Font.PLAIN,si);
        else
          font=new Font(fo,Font.ITALIC,si);
        sample.setFont(font);
        changeFont(font);
      }
      if(e.getSource()==cancel)
      {
        if(d!=null)
          d.setVisible(false);
      }
    }
  }
  /***************************************************
   ******************Class Open File*********************
   ****************************************************/
  class openFile extends Thread
  {
    Document doc;
    File f;
    
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
  
  /***************************************************
   ******************Class File Saver*********************
   ****************************************************/
  class FileSaver extends Thread
  {
    Document doc;
    File f;
    
    FileSaver(File f,Document doc)
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
        progress.setMaximum((int)doc.getLength());
        status.add(progress);
        status.revalidate();
        
        Writer out=new FileWriter(f);
        Segment text=new Segment();
        text.setPartialReturn(true);
        int charsLeft=doc.getLength();
        int offset=0;
        while(charsLeft>0)
        {
          doc.getText(offset,Math.min(4096,charsLeft),text);
          out.write(text.array,text.offset,text.count);
          charsLeft-=text.count;
          progress.setValue(offset);
          try{Thread.sleep(10);}
          catch(InterruptedException e){e.printStackTrace();}
          }
        out.flush();
        out.close();
      }
      catch(Exception e)
      {
        final String msg=e.getMessage();
        SwingUtilities.invokeLater(new Runnable(){public void run(){
        JOptionPane.showMessageDialog(new Frame(),"Could not save file: "+msg,"ERROR SAVING FILE",JOptionPane.ERROR_MESSAGE);}});
      }
    }
    
  }
  /***************************************************
   ******************Class Status Bar********************
   ****************************************************/
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
  
}
