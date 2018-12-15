/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package additivedemo;

/**
 *
 * @author administrator
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javax.swing.text.Document;
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
   JProgressBar progress=new JProgressBar();
   progress.setMinimum(0);
   progress.setMaximum((int)f.length());

   Reader in=new FileReader(f);
   char[] buff=new char[4096];
   int nch;
   while((nch=in.read(buff,0,buff.length))!=-1)
    {
      doc.insertString(doc.getLength(),new String(buff,0,nch),null);
      progress.setValue(progress.getValue()+nch);
    }
   }
   catch(Exception e)
    {
      final String msg=e.getMessage();
      SwingUtilities.invokeLater(new Runnable(){public void run()
                {
                    JOptionPane.showMessageDialog(new Frame(),"Could not open File:"+msg,"Error Opening File",JOptionPane.ERROR_MESSAGE);
                }});
    }
 }

}