import java.io.*;
import java.net.*;

class TCPServer
{
  public static void main(String argv[]) throws Exception
  {
    String fromclient;
    String toclient;
    
    ServerSocket Server = new ServerSocket (5000);
    
    System.out.println ("TCPServer Waiting for client on port 5000");
    
    while(true)
    {
      Socket connected = Server.accept();
      System.out.println( " THE CLIENT"+" "+
      connected.getInetAddress() +":"+connected.getPort()+" IS CONNECTED ");
      
      BufferedReader inFromUser =
      new BufferedReader(new InputStreamReader(System.in));
      
      BufferedReader inFromClient =
      new BufferedReader(new InputStreamReader (connected.getInputStream()));
      
      PrintWriter outToClient =
      new PrintWriter(
      connected.getOutputStream(),true);
      
      while ( true )
      {
        
        System.out.println("SEND(Type Q or q to Quit):");
        toclient = inFromUser.readLine();//read Data From The Server User
        
        if ( toclient.equals ("q") || toclient.equals("Q") )
        {
          outToClient.println(toclient);//Sent the data to client
          connected.close();//close the connection
          break;
        }
        else
        {
          outToClient.println(toclient);//sent the data to client
        }
        
        fromclient = inFromClient.readLine();//read the data from client
        
        if ( fromclient.equals("q") || fromclient.equals("Q") )
        {
          connected.close();//close the connection
          break;
        }
        
        else
        {
          System.out.println( "RECIEVED:" + fromclient );
        }
        
      }
      
    }
  }
}