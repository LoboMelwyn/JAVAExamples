//This is a programme on binomial Theorem
class binomial
{
  int n;
  double p;
  binomial(int n,double p)
  {
    this.n=n;
    this.p=p;
  }
  private int Combination(int n,int r)
  {
    double a=factorial(n);
    double b=factorial(r);
    double c=factorial(n-r);
    int result=(int) a/(int)(b*c);
    return result;
  }
  private double factorial(int f)
  {
    double result=1;
    for(int i=1;i<=f;i++)
    {
      result=result*i;
    }
    return result;
  }
  private double power(double no,int n)
  {
    double result=1;
    for(int i=0;i<n;i++)
    {
      result=result*no;
    }
    return result;
  }
  public double process(int x)
  {
    double result=1;
    result=(double)Combination(n,x);
    result=result*power(p,x);
    result=result*power(1-p,n-x);
    return result;
  }
}