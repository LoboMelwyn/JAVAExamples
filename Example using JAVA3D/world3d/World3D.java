/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world3d;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class World3D extends JFrame {

    /**
     * @param args the command line arguments
     */
    WorldContainer wc;
    private World3D()
    {
        super("My 3D World");
        wc=new WorldContainer();
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(d.width/2,d.height/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(wc);
        setVisible(true);
    }
    public static void main(String[] args) {
        World3D w3=new World3D();
    }
}
