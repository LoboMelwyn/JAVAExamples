import java.io.*;

class sudoku_rule
{
  int sudoku[][],row,col;
  
  sudoku_rule()
  {
    sudoku=new int[9][9];
    for(int i=0;i<9;i++)
    {
      for(int j=0;j<9;j++)
      {
        sudoku[i][j]=0;
      }
    }
  }
  
  boolean insert(int no,int row,int col)
  {
    this.row=row;
    this.col=col;
    if(!errRow(no,row)&&!errCol(no,col)&&!errBox(no,row,col))
    {
      sudoku[row][col]=no;
      return false;
    }
    else
      return true;
  }
  
  boolean errRow(int no,int row)
  {int n;
    for(int i=0;i<9;i++)
    {
      n=sudoku[i][col];
      if((n==no)&&(i!=row))
        return true;
    }
    return false;
  }
  
  boolean errCol(int no,int col)
  {int n;
    for(int i=0;i<9;i++)
    {
      n=sudoku[row][i];
      if((n==no)&&(i!=col))
        return true;
    }
    return false;
  }
  
  boolean errBox(int no,int row,int col)
  {
      int x=0,y=0,x1=0,y1=0;
      int box=checkRow(row)+checkCol(col);
      switch(box)
      {
          case 11:x=0;y=0;x1=2;y1=2;break;
          case 12:x=0;y=3;x1=2;y1=5;break;
          case 13:x=0;y=6;x1=2;y1=8;break;
          case 21:x=3;y=0;x1=5;y1=2;break;
          case 22:x=3;y=3;x1=5;y1=5;break;
          case 23:x=3;y=6;x1=5;y1=8;break;
          case 31:x=6;y=0;x1=8;y1=2;break;
          case 32:x=6;y=3;x1=8;y1=5;break;
          case 33:x=6;y=6;x1=8;y1=8;break;
          default: System.out.println("ERROR");
      }
      return checkBox(x,y,x1,y1,no);
  }

  int checkRow(int row)
  {
      if(row<3)
         return 10;
      else if(row<6)
         return 20;
      else if(row<9)
         return 30;
      else
         return 0;
  }

  int checkCol(int col)
  {
      if(col<3)
         return 1;
      else if(col<6)
         return 2;
      else if(col<9)
         return 3;
      else
         return 0;
  }

  boolean checkBox(int x,int y,int x1,int y1,int no)
  {
     for(int i=x;i<=x1;i++)
        for(int j=y;j<=y1;j++)
           if(sudoku[i][j]==no)
              return true;
     return false;
  }
  
  void display()
  {
    for(int i=0;i<9;i++)
    {
      for(int j=0;j<9;j++)
      {
        System.out.print(sudoku[i][j]+" ");
        if(j%3==2)
           System.out.print("|");
      }
      if(i%3==2)
         System.out.print("\n----------------------");
      System.out.println();
    }
  }
  
  public static void main(String args[])throws IOException
  {
    BufferedReader d=new BufferedReader(new InputStreamReader(System.in));
    sudoku_rule s=new sudoku_rule();
    String more;int no,row,col;boolean err;
    s.display();
    System.out.println("Press Enter if you want to exit or type m:");
    while(true)
    {
      more=d.readLine();
      if(more.equals("m"))
      {
        System.out.print("Enter the no:");
        no=Integer.parseInt(d.readLine());
        System.out.print("Enter the row:");
        row=Integer.parseInt(d.readLine());
        System.out.print("Enter the col:");
        col=Integer.parseInt(d.readLine());
        err=s.insert(no,row,col);
        if(err==true)
          System.out.println("There is some error:");
        s.display();
      }
      if(more.equals(""))
        break;
      else
        System.out.println("Wrong entry");
    }
  }
  
}
