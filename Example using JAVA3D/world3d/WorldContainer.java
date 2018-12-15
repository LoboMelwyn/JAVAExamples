/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world3d;

/**
 *
 * @author Administrator
 */
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class WorldContainer extends JPanel{
    private SimpleUniverse su;
    private BranchGroup bg;
    private Bounds bounds;
    
    WorldContainer()
    {
        setLayout(new BorderLayout());
        GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
        bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
        Canvas3D canvas = new Canvas3D(config);
        add("Center",canvas);
        canvas.setFocusable(true);
        canvas.requestFocus();
        su=new SimpleUniverse(canvas);
        bg=new BranchGroup();
        addBackground();
        initUserPosition();
        orbitControls(canvas);
        createChallis();
        su.addBranchGraph(bg);
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
    private void orbitControls(Canvas3D c)
    {
        OrbitBehavior orbit=new OrbitBehavior(c,OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);
        ViewingPlatform vp=su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
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

    private void createChallis() {
        Color3f golden = new Color3f(0.8f, 0.8f, 0.0f);
        Color3f darkGolden = new Color3f(0.3f, 0.3f, 0.0f);
        double x[]={0,0.2,0.01,0.3,0.3,0};
        double y[]={0,0,0.4,0.6,1,0.5};
        MakeShape ms=new MakeShape(x,y,golden,darkGolden);
        bg.addChild(ms);
    }
}
