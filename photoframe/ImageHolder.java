package photoframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Melwyn
 */
public class ImageHolder extends JPanel{
    private static Image img=null;
    private int iw,ih,width,height;
    private int init_pos_x,init_pos_y,final_pos_x,final_pos_y;
    private static boolean cal_change=true;
    private String path;
    
    protected void setImage(String path)
    {
        try{
        img=new ImageIcon(path).getImage();
        this.path=path;
        cal_change=true;
        }
        catch(Exception e)
        {
            System.out.println("ERROR: "+e);
        }
        repaint();
    }
    
    protected String getImage()
    {
        return path;
    }
    
    @Override
    public void paint(Graphics g)
    {
        Dimension d=getSize();
        width=d.width;
        height=d.height;
        g.fillRect(0, 0, width, height);
        if(img!=null)
        {
            if(cal_change)
            {
                calculate();
            }
            g.drawImage(img,init_pos_x,init_pos_y,final_pos_x,final_pos_y,null);
            
        }
    }
    
    private void calculate()
    {
        iw=img.getWidth(null);
        ih=img.getHeight(null);
        if(iw>width||ih>height)
        {
            final_pos_x=width;
            final_pos_y=height;
        }
        else
        {
            init_pos_x=(width-iw)/2;
            init_pos_y=(height-ih)/2;
            final_pos_x=iw;
            final_pos_y=ih;
        }
    }
    protected void enlarge()
    {
        cal_change=false;
        repaint();
    }
    
    protected void shrink()
    {
        cal_change=false;
        repaint();
    }
    protected void rotate(boolean isRight)
    {
        cal_change=true;
        if(isRight)
        {
            repaint();
        }
        else
        {
            repaint();
        }
    }
}
