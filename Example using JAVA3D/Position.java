import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.swing.JFrame;

class Position
{
  Position()
  {
    SimpleUniverse su=new SimpleUniverse();
    BranchGroup bg=new BranchGroup();
    
    for(float x=-1.0f;x<=1.0f;x+=0.1f)
    {
      Sphere sp=new Sphere(0.05f);
      TransformGroup tg=new TransformGroup();
      Transform3D trans=new Transform3D();
      Vector3f v=new Vector3f(x,0.0f,0.0f);
      trans.setTranslation(v);
      tg.setTransform(trans);
      tg.addChild(sp);
      bg.addChild(tg);
    }
    
    for(float y=-1.0f;y<=1.0f;y+=0.1f)
    {
      Cone cone=new Cone(0.05f,0.1f);
      TransformGroup tg=new TransformGroup();
      Transform3D trans=new Transform3D();
      Vector3f v=new Vector3f(0.0f,y,0.0f);
      trans.setTranslation(v);
      tg.setTransform(trans);
      tg.addChild(cone);
      bg.addChild(tg);
    }
    
    for(float z=-1.0f;z<=1.0f;z+=0.1f)
    {
      Cylinder cy=new Cylinder(0.05f,0.1f);
      TransformGroup tg=new TransformGroup();
      Transform3D trans=new Transform3D();
      Vector3f v=new Vector3f(0.0f,0.0f,z);
      trans.setTranslation(v);
      tg.setTransform(trans);
      tg.addChild(cy);
      bg.addChild(tg);
    }
    
    BoundingSphere bound=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
    
    Color3f lightcolor=new Color3f(0.1f,1.4f,0.1f);
    Vector3f lightdirection=new Vector3f(4.0f,-7.0f,-12.0f);
    DirectionalLight light=new DirectionalLight(lightcolor,lightdirection);
    light.setInfluencingBounds(bound);
    
    bg.addChild(light);
    su.getViewingPlatform().setNominalViewingTransform();
    su.addBranchGraph(bg);
  }
  public static void main(String args[])
  { new Position(); }
}
