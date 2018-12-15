/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diamond;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
/**
 *
 * @author administrator
 */
public class DiamondPoints extends Shape3D{
    private float a=1.0f;
    private GeometryArray hex;
    private Color3f red=new Color3f(0.8f,0.2f,0.2f);
    private Color3f green=new Color3f(0.2f,0.8f,0.2f);
    private Color3f blue=new Color3f(0.2f,0.2f,0.8f);
    
    private Point3f p00=new Point3f(0.0f,a/3,0.0f);
    private Point3f p01=new Point3f(-a/3,a/3,0.0f);
    private Point3f p02=new Point3f(-a/6,a/3,a/3);
    private Point3f p03=new Point3f(a/6,a/3,a/3);
    private Point3f p04=new Point3f(a/3,a/3,0.0f);
    private Point3f p05=new Point3f(a/6,a/3,-a/3);
    private Point3f p06=new Point3f(-a/6,a/3,-a/3);
    
    private Point3f p1=new Point3f(-a,0.0f,0.0f);
    private Point3f p2=new Point3f(-a/2,0.0f,a);
    private Point3f p3=new Point3f(a/2,0.0f,a);
    private Point3f p4=new Point3f(a,0.0f,0.0f);
    private Point3f p5=new Point3f(a/2,0.0f,-a);
    private Point3f p6=new Point3f(-a/2,0.0f,-a);
    
    private Point3f p0=new Point3f(0,-a,0);
    
    private final Point3f[] verts={
        p02,p00,p01,
        p03,p00,p02,
        p04,p00,p03,
        p05,p00,p04,
        p06,p00,p05,
        p01,p00,p06,
        
        p1,p02,p01,
        p2,p02,p1,
        p2,p03,p02,
        p3,p03,p2,
        p3,p04,p03,
        p4,p04,p3,
        p4,p05,p04,
        p5,p05,p4,
        p5,p06,p05,
        p6,p06,p5,
        p6,p01,p06,
        p1,p01,p6,
        
        p2,p0,p1,
        p3,p0,p2,
        p4,p0,p3,
        p5,p0,p4,
        p6,p0,p5,
        p1,p0,p6,
    };
    
    public Color3f sidecolor[]={
        red,red,red,
        red,red,red,
        red,red,red,
        red,red,red,
        red,red,red,
        red,red,red,
        
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        green,green,green,
        
        blue,blue,blue,
        blue,blue,blue,
        blue,blue,blue,
        blue,blue,blue,
        blue,blue,blue,
        blue,blue,blue,
    };
    
    DiamondPoints()
    {
        int[] texCoordMap = new int[6];
        texCoordMap[0]=0;
        texCoordMap[1]=0;
        texCoordMap[2]=1;
        texCoordMap[3]=0;
        texCoordMap[4]=1;
        texCoordMap[5]=1;
        hex=new TriangleArray(24*3,GeometryArray.COORDINATES|GeometryArray.NORMALS|GeometryArray.COLOR_3,3,texCoordMap);
        hex.setCoordinates(0, verts);
        hex.setColors(0, sidecolor);
        this.setGeometry(hex);
    }
    protected GeometryArray getHexagon()
    {
        return hex;
    }
}
