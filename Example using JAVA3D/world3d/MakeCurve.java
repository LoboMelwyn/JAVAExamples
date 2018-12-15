/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world3d;

import javax.vecmath.Point2d;

/**
 *
 * @author Administrator
 */
public class MakeCurve {
    private final int STEP=5;
    private double x[],y[];
    private double height;
    
    MakeCurve(double x[],double y[])
    {
        int numVerts=x.length;
        height = y[0]; 
        for(int i=1; i < y.length; i++)
            if (y[i] >= height)
                height = y[i];
        Point2d startTangent=new Point2d((Math.abs(x[1])-Math.abs(x[0]))*2,0);
        Point2d endTangent=new Point2d((Math.abs(x[numVerts-1])-Math.abs(x[numVerts-2]))*2,0);
        createCurve(x,y,startTangent,endTangent);
    }
    private void createCurve(double xsIn[], double ysIn[], Point2d startTangent, Point2d endTangent)
    {
        int numInVerts = xsIn.length;
        int numOutVerts = countVerts(xsIn, numInVerts);
        x = new double[numOutVerts];    // resulting sequence after adding extra pts
        y = new double[numOutVerts];

        x[0] = Math.abs(xsIn[0]);    // start of curve is initialised
        y[0] = ysIn[0];
        int startPosn = 1;

    // holds the tangents for the current curve segment between two points
        Point2d t0 = new Point2d();
        Point2d t1 = new Point2d();

        for (int i=0; i < numInVerts-1; i++) {
            if (i == 0)
                t0.set( startTangent.x, startTangent.y);
            else   // use previous t1 tangent
                t0.set(t1.x, t1.y);

            if (i == numInVerts-2)    // next point is the last one
                t1.set( endTangent.x, endTangent.y);
            else
                setTangent(t1, xsIn, ysIn, i+1);   // tangent at pt i+1

      // if xsIn[i] < 0 then use a straight line to link (x,y) to next (x,y)
            if (xsIn[i] < 0) {
                x[startPosn] = Math.abs(xsIn[i+1]);    // in case the next pt is -ve also
                y[startPosn] = ysIn[i+1];
                startPosn++;
            }
            else {   // make a Hermite curve between the two points by adding STEP new pts
                makeHermite(x, y, startPosn, xsIn[i], ysIn[i], xsIn[i+1], ysIn[i+1], t0, t1);
                startPosn += (STEP+1);
            }
        }
    }

    private int countVerts(double[] xsIn, int num) {
        int numOutVerts = 1;   // counting last coord
        for(int i=0; i < num-1; i++) {
        if (xsIn[i] < 0)   // straight line starts here
            numOutVerts++;
        else    // curve segment starts here 
            numOutVerts += (STEP+1);
        }
        return numOutVerts;
    }

    private void setTangent(Point2d tangent, double[] xsIn, double[] ysIn, int i) {
        double xLen = Math.abs(xsIn[i+1]) - Math.abs(xsIn[i-1]);   // ignore any -ve
        double yLen = ysIn[i+1] - ysIn[i-1];
        tangent.set(xLen/2, yLen/2);
    }

    private void makeHermite(double[] xs, double[] ys, int startPosn, double x0, double y0, double x1, double y1, Point2d t0, Point2d t1) {
        double xCoord, yCoord;   
        double tStep = 1.0/(STEP+1);
        double t;

        if (x1 < 0)      // next point is negative to draw a line, make it
            x1 = -x1;      // +ve while making the curve

        for(int i=0; i < STEP; i++) {
            t = tStep * (i+1);
            xCoord = (fh1(t) * x0) + (fh2(t) * x1) +(fh3(t) * t0.x) + (fh4(t) * t1.x);
            xs[startPosn+i] = xCoord;

            yCoord = (fh1(t) * y0) + (fh2(t) * y1) +(fh3(t) * t0.y) + (fh4(t) * t1.y);
            ys[startPosn+i] = yCoord;
        }

        xs[startPosn+STEP] = x1;
        ys[startPosn+STEP] = y1;
    }

    private double fh1(double t) {
        return (2.0)*Math.pow(t,3) - (3.0*t*t) + 1;
    }

    private double fh2(double t) {
        return (-2.0)*Math.pow(t,3) + (3.0*t*t);
    }

    private double fh3(double t) {
        return Math.pow(t,3) - (2.0*t*t) + t;
    }

    private double fh4(double t) {
        return Math.pow(t,3) - (t*t);
    }
    protected double[] getX()
    {
        return x;
    }
    protected double[] getY()
    {
        return y;
    }
    protected double getHeight()
    {
        return height;
    }
}
