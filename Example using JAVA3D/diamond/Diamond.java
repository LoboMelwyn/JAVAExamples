/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diamond;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author administrator
 */
public class Diamond extends JFrame{
    private SimpleUniverse su;
    private BranchGroup bg;
    private BoundingSphere bounds;
    
    Diamond()
    {
        setTitle("Diamond Example");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createScreen();
        setVisible(true);
    }
    public static void main(String[] args) {
        Diamond d=new Diamond();
    }
    private void createScreen()
    {
        setLayout(new BorderLayout());

        GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas=new Canvas3D(config);
        add("Center",canvas);
        canvas.setFocusable(true);
        canvas.requestFocus();

        su=new SimpleUniverse(canvas);
        bg=new BranchGroup();

        bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
        addBackground();
        initUserPosition();
        orbitControls(canvas);
        addHexagon();

        bg.compile();
        su.addBranchGraph(bg);
    }
    private void addHexagon()
    {
        Appearance app=new Appearance();
        Shape3D shape=new DiamondPoints();
        
        // Set up the transparency properties
	TransparencyAttributes ta = new TransparencyAttributes();
	ta.setTransparencyMode(TransparencyAttributes.BLENDED);
	ta.setTransparency(0.6f);
	app.setTransparencyAttributes(ta);

	// Set up the polygon attributes
	PolygonAttributes pa = new PolygonAttributes();
	pa.setCullFace(PolygonAttributes.CULL_NONE);
	app.setPolygonAttributes(pa);
        
        shape.setAppearance(app);
        bg.addChild(shape);
    }
    private void addBackground()
    {
        Background back=new Background();
        back.setApplicationBounds(bounds);
        back.setColor(0.17f,0.65f,0.92f);
        bg.addChild(back);
        Color3f sunlight=new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightdirection=new Vector3f(4.0f,-7.0f,-12.0f);
        DirectionalLight light=new DirectionalLight(sunlight,lightdirection);
        light.setInfluencingBounds(bounds);
        bg.addChild(light);
    }
    private void initUserPosition()
    {
        ViewingPlatform vp=su.getViewingPlatform();
        TransformGroup steerTG=vp.getViewPlatformTransform();
        Transform3D t3d=new Transform3D();
        steerTG.getTransform(t3d);
        Point3d USRPOSN=new Point3d(0,5,15);
        t3d.lookAt(USRPOSN,new Point3d(0,0,0),new Vector3d(0,1,0));
        t3d.invert();
        steerTG.setTransform(t3d);
    }
    private void orbitControls(Canvas3D c)
    {
        OrbitBehavior orbit=new OrbitBehavior(c,OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);
        ViewingPlatform vp=su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }
}
