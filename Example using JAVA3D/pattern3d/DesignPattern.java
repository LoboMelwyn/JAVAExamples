/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern3d;

/**
 *
 * @author Administrator
 */
import java.applet.Applet;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class DesignPattern extends Applet{
    private Image img;
    
    DesignPattern(int w,int h)
    {
        int pixels[]=new int[w*h];
        int i = 0;
        for(int y=0; y<h; y++)
        {
            for(int x=0; x<w; x++)
            {
                int r = (x^y)&0xff;
                int g = (x*2^y*2)&0xff;
                int b = (x*4^y*4)&0xff;
                pixels[i++] = (255 << 24) | (r << 16) | (g << 8) | b;
            }
        }
        img = createImage(new MemoryImageSource(w, h, pixels, 0, w));
    }
    protected Image getPattern()
    {
        return img;
    }
}
