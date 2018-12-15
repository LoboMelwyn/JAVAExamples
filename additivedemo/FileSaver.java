/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package additivedemo;

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.Segment;

/**
 *
 * @author administrator
 */
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
      JProgressBar progress=new JProgressBar();
      progress.setMinimum(0);
      progress.setMaximum((int)doc.getLength());

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
   SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
                JOptionPane.showMessageDialog(new Frame(),"Could not save file: "+msg,"ERROR SAVING FILE",JOptionPane.ERROR_MESSAGE);
                }});
    }
 }

}
