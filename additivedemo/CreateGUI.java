
package additivedemo;

/**
 *
 * @author administrator
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.text.PlainDocument;

public class CreateGUI extends JFrame{
    private JTextArea editor;
    private static final String mac="com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private static final String nimbus="javax.swing.plaf.nimbus.NimbusLookAndFeel";
    private static final String metal="javax.swing.plaf.metal.MetalLookAndFeel";
    private static final String motif="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private static final String windows="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String gtk="com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    
    CreateGUI()
    {
        super("Decipering Additive ciper");
        setLayout(new BorderLayout());
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        add(createPanel(),BorderLayout.CENTER);
        setVisible(true);
    }
    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu("File");
        JMenuItem menuItem=createMenuItem("Open File",new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        },KeyEvent.VK_O,InputEvent.CTRL_MASK);
        menu.add(menuItem);
        /*menuItem=createMenuItem("Save File",new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        },KeyEvent.VK_S,InputEvent.CTRL_MASK);
        menu.add(menuItem);*/
        menu.addSeparator();
        menuItem=createMenuItem("Exit",new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        },KeyEvent.VK_F4,InputEvent.ALT_MASK);
        menu.add(menuItem);
        menuBar.add(menu);
        menu=createThemeMenu();
        menuBar.add(menu);
        return menuBar;
    }
    private JMenu createThemeMenu()
    {
        JMenu m=new JMenu("Look and Feel");
        JMenuItem item=createMenuItem("Metal",new ActionListener()
				{
            @Override
				public void actionPerformed(ActionEvent ae)
				{
				  theme(metal);
				}
				},'M',0);
        m.add(item);
        item=createMenuItem("Nimbus",new ActionListener()
					{
            @Override
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
        if (className.equals(motif)) {
            item=createMenuItem("Motif",new ActionListener()
				{
				public void actionPerformed(ActionEvent ae)
				{
				  theme(motif);
				}
				},'M',0);
            m.add(item);
         }
        else if(className.equals(windows)){
            item=createMenuItem("Windows",new ActionListener()
					{
                    @Override
					public void actionPerformed(ActionEvent ae)
					{
					  theme(windows);
					}
					},'W',0);
            m.add(item);
         }
        else if(className.equals(gtk)){
            item=createMenuItem("GTK+ Theme",new ActionListener()
				{
                    @Override
				public void actionPerformed(ActionEvent ae)
				{
				  theme(gtk);
				}
				},'G',0);
            m.add(item);
        }
        else if(className.equals(mac)){
             item=createMenuItem("Mac Theme",new ActionListener()
				{
                    @Override
				public void actionPerformed(ActionEvent ae)
				{
				  theme(mac);
				}
				},'M',0);
            m.add(item);
        }
        }
        return m;
    }
    private JMenuItem createMenuItem(String name,ActionListener a,int acc,int accmodes)
    {
        JMenuItem item=new JMenuItem(name);
        item.addActionListener(a);
        if(acc==-1||accmodes==-1)
            item.setAccelerator(KeyStroke.getKeyStroke(acc,accmodes));
        return item;
    }
    private JComponent createPanel()
    {
        JPanel main=new JPanel(new BorderLayout());
        int v=JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h=JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        editor=new JTextArea();
        editor.setFont(new Font("Arial",Font.PLAIN,18));
        editor.setDragEnabled(true);
        JScrollPane jsp=new JScrollPane(editor,v,h);
        main.add(jsp);
        return main;
    }
    private void theme(String laf)
    {
        try {
        UIManager.setLookAndFeel(laf);
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Exception excep) {
            JOptionPane.showMessageDialog(new Frame(),"ERROR: "+excep,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void saveFile()
    {
        Frame frame=new Frame();
        frame.setTitle("Save");
        JFileChooser chooser=new JFileChooser();
        int ret=chooser.showSaveDialog(frame);

        if(ret!=JFileChooser.APPROVE_OPTION)
            {return;}

        File f=chooser.getSelectedFile();
        frame.setTitle("Save....");
        Thread saver=new FileSaver(f,editor.getDocument());
        saver.start();
    }
    private void openFile()
    {
        File defaultDirectory;
        final JFileChooser chooser=new JFileChooser();
        chooser.setMultiSelectionEnabled(false);

        chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){public boolean accept(File f){return(f.isDirectory()||f.getName().endsWith(".txt"));}
        public String getDescription()
        {
            return "Text files(*.txt)";
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
        new enterKey();
    }
    class enterKey
    {
        int keyvalue=0;
        JDialog d;
        JTextField key;
        Substitution s =new Substitution();
        enterKey()
        {
            d=new JDialog();
            JPanel main=new JPanel();
            d.setTitle("Enter Key");
            d.setSize(200,80);
            d.setResizable(false);
        
            key=new JTextField(10);
            JButton ok=new JButton("OK");
            ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractValue();
            }
            });
            main.add(key);
            main.add(ok);
            d.add(main);
            d.setVisible(true);
        }
        private void extractValue()
        {
            keyvalue=Integer.parseInt(key.getText());
            performDecrypt();
            d.setVisible(false);
        }
        private void performDecrypt()
        {
            String str=editor.getText();
            String decr=s.decrypt(str+"\0", keyvalue);
            editor.setText("");
            editor.setText(decr);
        }
    }
}
