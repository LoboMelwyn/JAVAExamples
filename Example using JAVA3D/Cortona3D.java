/*****************************************************************
@echo off
echo Compiling Cortona3D ...
javac -classpath %CLASSPATH%;ncsa\portfolio.jar Cortona3D.java

echo Executing Java3D application...

java -cp %CLASSPATH%;ncsa\portfolio.jar Cortona3D
echo Finished.
 ****************************************************************/

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


import ncsa.j3d.loaders.*;
import ncsa.j3d.*;
import com.sun.j3d.loaders.Scene;

class Cortona3D extends JPanel
{
  SimpleUniverse su;
  BoundingSphere bounds;
  Scene loadScene=null;
  BranchGroup bg=null;
  
  public static void main(String args[])
  {
    JFrame f=new JFrame("Cortona3D");
    f.setLayout(new BorderLayout());
    Cortona3D t=new Cortona3D();
    f.add(t,BorderLayout.CENTER);
    f.setDefaultCloseOperation(3);
    f.setUndecorated(false);
    f.pack();
    f.setVisible(true);
  }
  public Cortona3D()
  {
    setLayout(new BorderLayout());
    setOpaque(false);
    setPreferredSize(new Dimension(600,600));
    
    GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas=new Canvas3D(config);
    add("Center",canvas);
    canvas.setFocusable(true);
    canvas.requestFocus();
    
    su=new SimpleUniverse(canvas);
    bg=new BranchGroup();
    
    bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
    loadModel("model/01.wrl");
    addBackground();
    initUserPosition();
    orbitControls(canvas);
    
    bg.compile();
    su.addBranchGraph(bg);
  }
  public void loadModel(String fn)
  {
    FileWriter ofw=null;
    try
    {
      System.out.println("Loading: "+fn);
      ModelLoader loader=new ModelLoader();
      loadScene=loader.load(fn);
      if(loadScene!=null)
        bg=loadScene.getSceneGroup();
    }
    catch(Exception e)
    {
      System.err.println("Couldn't find object file: "+fn);
    }
  }
  void addBackground()
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
  void orbitControls(Canvas3D c)
  {
    OrbitBehavior orbit=new OrbitBehavior(c,OrbitBehavior.REVERSE_ALL);
    orbit.setSchedulingBounds(bounds);
    ViewingPlatform vp=su.getViewingPlatform();
    vp.setViewPlatformBehavior(orbit);
  }
  void initUserPosition()
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
}