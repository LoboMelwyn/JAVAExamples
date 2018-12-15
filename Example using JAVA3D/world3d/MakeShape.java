package world3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.TexCoord2f;


public class MakeShape extends Shape3D
{
  private final static double RADS_DEGREE = Math.PI/180.0;
  private static final double ANGLE_INCR = 15.0;
  private static final int NUM_SLICES = (int)(360.0/ANGLE_INCR);
  private Color3f light; 
  private Color3f dark;
  private double height;    // height of the shape

  public MakeShape(double xsIn[], double ysIn[],Color3f light,Color3f dark)
  {
      this.light=light;
      this.dark=dark;
        MakeCurve lc = new MakeCurve(xsIn, ysIn);
        buildShape(lc.getX(), lc.getY(), lc.getHeight());
  }
  private void buildShape(double[] xs, double[] ys, double h)
  {
    height = h;
    createGeometry(xs, ys, false);
    createAppearance(dark, light);
  }
  private void createGeometry(double[] xs, double[] ys, boolean usingTexture)
  {
    double verts[] = surfaceRevolve(xs, ys);
    GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
    geom.setCoordinates(verts);

    if (usingTexture) {
      geom.setTextureCoordinateParams(1, 2);    // set up texture coords
      TexCoord2f[] texCoords = initTexCoords(verts);
      correctTexCoords(texCoords);
      geom.setTextureCoordinates(0, texCoords); 
    }

    NormalGenerator norms = new NormalGenerator();
    norms.generateNormals(geom);
   
    setGeometry( geom.getGeometryArray() );   // convert back to geo array
  }
  private TexCoord2f[] initTexCoords(double[] verts)
  { int numVerts = verts.length;
    TexCoord2f[] tcoords = new TexCoord2f[numVerts/3];
    double x, y, z;
    float sVal, tVal;
    double angle, frac;
    int idx = 0;
    for(int i=0; i < numVerts/3; i++) {
      x = verts[idx];  y = verts[idx+1];  z = verts[idx+2];
      angle = Math.atan2(x,z);       // -PI to PI
      frac = angle/Math.PI;          // -1.0 to 1.0
      sVal = (float) (0.5 + frac/2);   // 0.0f to 1.0f
      tVal = (float) (y/height);    // 0.0f to 1.0f; uses global height value
      tcoords[i] = new TexCoord2f(sVal, tVal);
      idx += 3;
    }
    return tcoords;
  }
  private void correctTexCoords(TexCoord2f[] tcoords)
  {
    for(int i=0; i < tcoords.length; i=i+4) {
      if( (tcoords[i].x < tcoords[i+3].x) &&
          (tcoords[i+1].x < tcoords[i+2].x)) {  // should not increase
        tcoords[i].x = (1.0f + tcoords[i+3].x)/2 ;  // between x and 1.0
        // tcoords[i+1].x = 1.0f;
        tcoords[i+1].x = (1.0f + tcoords[i+2].x)/2 ;  // between x and 1.0
      }
    }
  }
  private void createAppearance(Color3f darkCol, Color3f lightCol)
  {
    Appearance app = new Appearance();
    PolygonAttributes pa = new PolygonAttributes();
    pa.setCullFace(PolygonAttributes.CULL_NONE);  // see both sides of shape   
    app.setPolygonAttributes(pa);
    Material mat = new Material(darkCol, dark, lightCol, dark, 1.0f);
    mat.setLightingEnable(true);    // lighting switched on
    app.setMaterial(mat);
    setAppearance(app);
  }
  private void createAppearance(Texture tex)
  {
    Appearance app = new Appearance();
    PolygonAttributes pa = new PolygonAttributes();
    pa.setCullFace(PolygonAttributes.CULL_NONE);  // see both sides of shape   
    app.setPolygonAttributes(pa);
    TextureAttributes ta = new TextureAttributes();
    ta.setTextureMode(TextureAttributes.MODULATE);
    app.setTextureAttributes(ta);
    Material mat = new Material();   // set a default white material
    mat.setSpecularColor(dark);     // no specular color
    mat.setLightingEnable(true);
    app.setMaterial(mat);
    app.setTexture( tex );
    setAppearance(app);
  }
  private double[] surfaceRevolve(double xs[], double ys[]) 
  { 
    checkCoords(xs);
    double[] coords = new double[(NUM_SLICES) * (xs.length-1) * 4 * 3];
    int index=0;
    for (int i=0; i < xs.length-1; i++) {
      for (int slice=0; slice < NUM_SLICES; slice++) {
        addCorner( coords, xs[i], ys[i], slice, index);        // bottom right
        index += 3;

        addCorner( coords, xs[i+1], ys[i+1], slice, index);    // top right
        index += 3;

        addCorner( coords, xs[i+1], ys[i+1], slice+1, index);  // top left
        index += 3;

        addCorner( coords, xs[i], ys[i], slice+1, index);      // bottom left
        index += 3;
      }
    }
    return coords;
  }
  private void checkCoords(double xs[])
  {
    for (int i=0; i < xs.length; i++) {
      if (xs[i] < 0) {
        System.out.println("Warning: setting xs[" + i + "] from -ve to 0");
        xs[i] = 0;
      }
    }
  }
  private void addCorner(double[] coords, double xOrig, double yOrig,int slice, int index)
  {
    double angle = RADS_DEGREE * (slice*ANGLE_INCR);

    if (slice == NUM_SLICES)  // back at start
      coords[index] = xOrig;
    else
      coords[index] = xCoord(xOrig, angle);  // x

    coords[index+1] = yOrig;   // y

    if (slice == NUM_SLICES)
      coords[index+2] = 0;
    else
      coords[index+2] = zCoord(xOrig, angle);   // z
  }
  protected double xCoord(double radius, double angle)
  {  return radius * Math.cos(angle);  }
  protected double zCoord(double radius, double angle)
  {  return radius * Math.sin(angle);  }
}