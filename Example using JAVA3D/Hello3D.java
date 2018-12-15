import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.BranchGroup;

class Hello3D
{
  Hello3D()
  {
    SimpleUniverse su=new SimpleUniverse();// Creates Virtual Universe to contain the scene
    BranchGroup bg=new BranchGroup();// Creates the data structure to contain objects
    bg.addChild(new ColorCube(0.3));// adding Cube to the data structure
    su.getViewingPlatform().setNominalViewingTransform();// To set the viewer position
    su.addBranchGraph(bg);// adding data structure to the Virtual Universe
  }
  
  public static void main(String args[])
  {
    new Hello3D();
  }
  
}
