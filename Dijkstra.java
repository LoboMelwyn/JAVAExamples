/* PROGRAM TO FIND THE SHORTEST PATH USING DIJKSTRAS SHORTEST PATH ALGORITHM
THIS PROGRAM USES ADJACENCY MATRIX TO READ A GRAPH STRUCTURE */
import java.io.*;
import java.util.*;

class Graph
{
  int MAXNODES=50;
  int MAX1=150;
  int INFINITY=5000;
  int i,j,finall=0;
  int smalldist,newdist,k,s,d,current,n,distcurr;
  int weight[][]=new int[MAXNODES][MAXNODES];
  int distance[]=new int[MAXNODES];
  int visit[]=new int[MAXNODES];
  int precede[]=new int[MAXNODES];
  int path[]=new int[MAX1];
  
  
  /*function to print the result*/
  void Display_Result()
  {
    i=d;
    path[finall]=d;
    finall++;
    while(precede[i] != s)
    {
      j = precede[i];
      i = j;
      path[finall] = i;
      finall++;
    }
    path[finall] = s;
    System.out.println("\nThe shortest path followed is : \n\n");
    for(i=finall;i>0;i--)
    {System.out.print("\t\t( "+ path[i]+" ->"+path[i-1]+" ) with cost = ");
    System.out.print(weight[path[i]][path[i-1]]+"\n\n");}
    System.out.println("For the Total Cost = "+distance[d]);
  }/*end Display_Result*/
  
  /*function to read number*/
  int getNumber()
  {
    String str;
    int ne=0;
    InputStreamReader input=new InputStreamReader(System.in);
    BufferedReader in=new BufferedReader(input);
    try
    {
      str=in.readLine();
      ne=Integer.parseInt(str);
    }
    catch(Exception e)
    {
      System.out.println("I/O Error");
    }
    return ne;
  }/*end getNumber*/
  
  /*function for shortest path*/
  void SPA()
  {
    System.out.print("\nEnter the number of nodes (Less than 50) in the matrix : ");
    n=getNumber();
    
    System.out.print("\nEnter the cost matrix : \n\n");
    for(i=0;i<n;i++)
    for(j=0;j<n;j++)
    {
      System.out.print("\nCost "+(i)+"--"+(j)+" : ");
      weight[i][j]=getNumber();
    }
    
    
    System.out.print("\nEnter the source node : ");
    s=getNumber();
    
    System.out.print("\nEnter the destination node : ");
    d=getNumber();
    
    for (i=0;i<n;i++)
    {
      distance[i]=INFINITY;
      precede[i]=INFINITY;
    }
    distance[s]=0;
    current=s;
    visit[current]=1;
    while(current!=d)
    {
      distcurr=distance[current];
      smalldist=INFINITY;
      for(i=0; i<n; i++)
      {
        if(visit[i]==0)
        {
          newdist=distcurr+weight[current][i];
          if(newdist<distance[i])
          {
            distance[i]=newdist;
            precede[i]=current;
          }
          if(distance[i]< smalldist)
          {
            smalldist=distance[i];
            k=i;
          }
        }
      }
      current = k;
      visit[current]=1;
    }
    Display_Result();
  }
  
}/*end SPA*/


class Dijkstra
{
  public static void main(String args[])
  {
    Graph g=new Graph();
    g.SPA();
  }
}
