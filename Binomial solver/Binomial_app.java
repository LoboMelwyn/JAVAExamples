import java.io.*;
class Binomial_app
{
  public static void main(String args[])throws IOException
  {
    DataInputStream d=new DataInputStream(System.in);
    double p,result;int n,X;String x,y;
    while(true)
    {
      System.out.println("Enter the meaning of succes:");
      System.out.print("X: ");
      x=d.readLine();
      if(x.equals(""))
        break;
      System.out.print("Enter the value of n and p \nn:");
      n=Integer.parseInt(d.readLine());
      System.out.print("p:");
      p=Double.parseDouble(d.readLine());
      binomial b=new binomial(n,p);
      System.out.println("P(X=x)= "+n+"Cx "+p+"^x "+(1-p)+"^( "+n+"-x)");
      while(true)
      {
        System.out.print("Enter the value of x \nx:");
        y=d.readLine();
        if(y.equals(""))
          break;
        X=Integer.parseInt(y);
        result=b.process(X);
        System.out.println("P(X="+X+")= "+result);
      }
      System.out.println("X:"+x);
      System.out.println("P(X=x)= "+n+"Cx "+p+"^x "+(1-p)+"^("+n+"-x)");
    }
  }
}