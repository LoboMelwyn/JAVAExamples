/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package photoframe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Melwyn
 */
public class PhotoFrame extends JFrame{
    private int width,height;
    private ImageHolder imageholder;
    private JPanel main;
    private static String dirPath=System.getProperty("usr.dir");
    private JFileChooser chooser;
    private static File file;
    private static ArrayList<String> file_paths=null;
    private FileFilter png=new FileFilter(){
        @Override
        public boolean accept(File f){return(f.isDirectory()||f.getName().endsWith(".jpg"));}
        @Override
        public String getDescription()
        {
            return "JPEG files(*.jpg)";
        }};
    private FileFilter jpg=new FileFilter(){
        @Override
        public boolean accept(File f){return(f.isDirectory()||f.getName().endsWith(".png"));}
        @Override
        public String getDescription()
        {
          return "PNG Files(*.png)";
        }};
    
    private ActionListener open=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            openAction();
        }
    };
    private ActionListener next=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            nextImage();
        }
    };
    private ActionListener previous=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            preImage();
        }
    };
    private ActionListener enlarge=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            enlargeAction();
        }
    };
    private ActionListener shrink=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            shrinkAction();
        }
    };
    private ActionListener rotateleft=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            rotateAction(false);
        }
    };
    private ActionListener rotateright=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            rotateAction(true);
        }
    };
    private ActionListener exit=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            exitAction();
        }
    };
    private ActionListener actions[]={open,previous,next,enlarge,shrink,rotateleft,rotateright,exit};
    PhotoFrame()
    {
        super("Photo Frame");
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        width=d.width/2;
        height=d.height/2;
        setSize(width,height);
        add("North",createToolbar());
        add(createPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {}
        setVisible(true);
    }
    public static void main(String[] args) {
        PhotoFrame pf=new PhotoFrame();
        
    }
    private JPanel createPanel()
    {
        main=new JPanel(new BorderLayout());
        imageholder=new ImageHolder();
        int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane scroll=new JScrollPane(imageholder,v,h);
        main.add(scroll);
        return main;
    }
    private JToolBar createToolbar()
    {
        JToolBar toolbar=new JToolBar();
        String tools[]={"Open","Previous","Next","Enlarge","Shrink","RotateLeft","RotateRight","Exit"};
        String tip[]={"Opens an Image","Show Previous Image","Shows Next Image","Enlarges the Image","Shrinks the Image","Rotates the Image to Left","Rotates the Image to the Right","Exit Application"};
        for(int i=0;i<tools.length;i++)
        {
            toolbar.add(createTool(tools[i],actions[i],tip[i]));
        }
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }
    private Component createTool(String key,ActionListener a,String tip)
    {
        JButton b=new JButton();
        b.setIcon(new ImageIcon(ClassLoader.getSystemResource("photoframe/resources/"+key+".gif")));
        b.setRequestFocusEnabled(true);
        b.setMargin(new Insets(10,10,10,10));
        b.addActionListener(a);
        b.setToolTipText(tip);
        return b;
    }
    private void openAction()
    {
        chooser=new JFileChooser(dirPath);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileHidingEnabled(true);
        chooser.addChoosableFileFilter(png);
        chooser.addChoosableFileFilter(jpg);
    
        int res=chooser.showOpenDialog(new Frame());
        if(res==JFileChooser.APPROVE_OPTION)
        {
            file=chooser.getSelectedFile();
            String path=file.getPath();
            imageholder.setImage(path);
            dirPath=file.getParent();
            file=new File(dirPath);
            String files[]=file.list();
            file_paths=new ArrayList<String>();
            for(int i=0;i<files.length;i++)
                file_paths.add(files[i]);
        }
    }
    private void nextImage()
    {
        if(file_paths!=null)
        {
            int i=file_paths.indexOf(imageholder.getImage());
            System.out.println("Index: "+i);
            if(i==file_paths.size()-1)
                i=-1;
            i++;
            String path=file_paths.get(i);
            imageholder.setImage(path);
            System.out.println("Current Path: "+imageholder.getImage());
        }
        else
        {
            JOptionPane.showMessageDialog(new Frame(), "Error: No Image files can be found in the Folder specified", "ERROR", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    private void preImage()
    {
        if(file_paths!=null)
        {
            int i=file_paths.indexOf(imageholder.getImage());
            System.out.println("Index: "+i);
            if(i==-1)
                i=file_paths.size();
            --i;
            String path=file_paths.get(i);
            imageholder.setImage(path);
            System.out.println("Current Path: "+imageholder.getImage());
        }
        else
        {
            JOptionPane.showMessageDialog(new Frame(), "Error: No Image files can be found in the Folder specified", "ERROR", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    private void enlargeAction()
    {
        System.out.println("You have selected enlarge Image");
        imageholder.enlarge();
    }
    private void shrinkAction()
    {
        System.out.println("You have selected shrink Image");
        imageholder.shrink();
    }
    private void rotateAction(boolean isRight)
    {
        imageholder.rotate(isRight);
        if(isRight)
        {
            System.out.println("You have selected to Rotate right");
        }
        else
        {
            System.out.println("You have selected to Rotate Left");
        }
        
    }
    private void exitAction()
    {
        System.exit(0);
    }
}
