/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern3d;

/**
 *
 * @author Administrator
 */
import javax.swing.JFrame;

public class Main extends JFrame{
    private Pattern3D pattern;
    
    Main()
    {
        pattern=new Pattern3D();
        setTitle("3D Pattern Example");
        setSize(300,300);
        add(pattern);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        Main m=new Main();
    }
}
