import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.Container;

class PictureBall
{
  PictureBall()
  {
    SimpleUniverse su=new SimpleUniverse();
    BranchGroup bg=new BranchGroup();
    
    Color3f black=new Color3f(0.0f,0.0f,0.0f);
    Color3f white=new Color3f(1.0f,1.0f,1.0f);
    Color3f red=new Color3f(0.11f,0.11f,0.8f);
    
    TextureLoader loader=new TextureLoader("01.jpg","LUMINANCE",new Container());
    Texture texture=loader.getTexture();
    texture.setBoundaryModeS(Texture.WRAP);
    texture.setBoundaryModeT(Texture.WRAP);
    texture.setBoundaryColor(new Color4f(0.0f,1.0f,0.0f,0.0f));
    TextureAttributes attr=new TextureAttributes();
    attr.setTextureMode(TextureAttributes.MODULATE);
    Appearance ap=new Appearance();
    ap.setTexture(texture);
    ap.setTextureAttributes(attr);
    ap.setMaterial(new Material(white,red,white,red,1.0f));
    
    int primFlag=Primitive.GENERATE_NORMALS+Primitive.GENERATE_TEXTURE_COORDS;
    Sphere sphere=new Sphere(0.5f,primFlag,ap);
    bg.addChild(sphere);
    
    Color3f lightcolor=new Color3f(1f,1f,1f);
    BoundingSphere bound=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
    Vector3f v=new Vector3f(4.0f,-7.0f,-12.0f);
    DirectionalLight light=new DirectionalLight(lightcolor,v);
    light.setInfluencingBounds(bound);
    bg.addChild(light);
    
    AmbientLight ambient=new AmbientLight(new Color3f(0.5f,0.5f,0.5f));
    ambient.setInfluencingBounds(bound);
    bg.addChild(ambient);
    
    su.getViewingPlatform().setNominalViewingTransform();
    su.addBranchGraph(bg);
  }
  
  public static void main(String args[])
  { new PictureBall();  }
  
}
