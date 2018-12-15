import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

class Cube3D
{
  Cube3D()
  {
    SimpleUniverse su=new SimpleUniverse();// Creates Virtual Universe to contain the scene
    BranchGroup bg=new BranchGroup();// Creates the data structure to contain objects
    Sphere sp=new Sphere(0.3f);//Creating a new Sphere
    bg.addChild(sp);// adding Cube to the data structure
    Color3f light1color=new Color3f(1.8f,0.1f,0.1f);//creating a new colour to the light
    BoundingSphere bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);//creating sphereical Boundary
    Vector3f light1direction =new Vector3f(4.0f,-7.0f,-12.0f);//Creating direction to the light
    DirectionalLight light1=new DirectionalLight(light1color,light1direction);//creating new directional light
    light1.setInfluencingBounds(bounds);
    bg.addChild(light1);//adding new child
    su.getViewingPlatform().setNominalViewingTransform();// To set the viewer position
    su.addBranchGraph(bg);// adding data structure to the Virtual Universe
  }
  
  public static void main(String args[])
  {
    new Cube3D();
  }
  
}
