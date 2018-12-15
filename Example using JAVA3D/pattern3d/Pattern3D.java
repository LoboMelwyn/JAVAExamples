/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern3d;

/**
 *
 * @author Administrator
 */
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
public class Pattern3D extends JPanel {
    private SimpleUniverse su;
    private BranchGroup bg;
    private BoundingSphere bounds;
    
    Pattern3D()
    {
        setLayout(new BorderLayout());
        GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add("Center",canvas);
        canvas.setFocusable(true);
        canvas.requestFocus();
        su=new SimpleUniverse(canvas);
        bg=new BranchGroup();
        createSceneGraph();
        initUserPosition();
        orbitControl(canvas);
        su.addBranchGraph(bg);
    }
    private void createSceneGraph()
    {
        bounds=new BoundingSphere(new Point3d(0,0,0),100.0);
        lightScene();
        addBackground();
        createPatternFloor();
        bg.compile();
    }
    private void lightScene()
    {
        Color3f white=new Color3f(1.0f,1.0f,1.0f);
        AmbientLight ambient=new AmbientLight(white);
        ambient.setInfluencingBounds(bounds);
        bg.addChild(ambient);
        Vector3f lightDirection=new Vector3f(-1.0f,-1.0f,-1.0f);
        DirectionalLight light=new DirectionalLight(white,lightDirection);
        light.setBounds(bounds);
        bg.addChild(light);
    }
    private void addBackground()
    {
        Background back=new Background();
        back.setApplicationBounds(bounds);
        back.setColor(0.2f,0.6f,0.9f);
        bg.addChild(back);
    }
    private void createPatternFloor()
    {
        try{
            DesignPattern dp=new DesignPattern(300,300);
            Image img=dp.getPattern();
            
            TextureLoader loader=new TextureLoader(img,"",new Container());
            Texture texture=loader.getTexture();
            Appearance ap=new Appearance();
            ap.setTexture(texture);
            
            int primFlag=Primitive.GENERATE_NORMALS+Primitive.GENERATE_TEXTURE_COORDS;
            Box s=new Box(0.5f,0.5f,0.5f,primFlag,ap);
            bg.addChild(s);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
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
    private void orbitControl(Canvas3D canvas)
    {
        OrbitBehavior orbit=new OrbitBehavior(canvas,OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);
        ViewingPlatform vp=su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }
}
