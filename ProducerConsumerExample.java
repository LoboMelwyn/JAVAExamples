
//Program for Producer-Consumer Problem


class Producer extends Thread
{
  Monitor mp;
  Producer(Monitor mmp)
  {
    mp=mmp;
  }
  public void run()
  {
    try
    {
      while(true)
      {
        mp.putitem(1);
        System.out.println("after producing element");
        mp.status();
        sleep(3000);
      }
    }
    catch(InterruptedException e)
    {
      System.out.print("Exception in Producer " + e);
    }
  }
}
class Consumer extends Thread
{
  Monitor mp;
  Consumer(Monitor mmp)
  {
    mp=mmp;
  }
  public void run()
  {
    try
    {
      while(true)
      {
        int c=mp.getitem();
        System.out.println("after consuming element");
        mp.status();
        sleep(6000);
      }
    }
    catch(InterruptedException e)
    {
      System.out.print("Exception in Consumer " + e);
    }
  }
}
class Monitor //throws interruptedException
{
  int [] buffer = new int [10]; //bounded buffer
  int in;//in pointer used by producer
  int out;//out pointer used by consumer
  int i;
  public void Monitor()
  {
    in=0;out=0;i=0;
    for(i=0;i<10;i++)
    {
      buffer[i]=0;
    }
  }
  synchronized int getitem() throws InterruptedException
  {
    notify();
    while(in==out)
    {
      System.out.println("Consumer Blocked");
      wait();
    }
    int c=buffer[out];
    buffer[out]=0;
    out=(out+1)%10;
    return c;
  }
  synchronized void putitem(int item) throws InterruptedException
  {
    while(( (in+1)%10 )==out)
    {
      System.out.println("Producer Blocked");
      wait();
    }
    buffer[in]=item;
    in=(in+1)%10;
    notify();
  }
  synchronized void status()
  {
    System.out.println("");
    System.out.print("Buffer:-");
    for(i=0;i<10;i++)
    {
      System.out.print(buffer[i]+"|");
    }
    System.out.println("");
    System.out.println("in :"+in+""+"out : "+out);
  }
}
class ProducerConsumerExample
{
  public static void main(String args[])
  {
    Monitor m = new Monitor();
    System.out.println("intial status of buffer is ");
    m.status();
    Producer prod = new Producer(m);
    Consumer consume = new Consumer(m);
    prod.start();
    consume.start();
    
  }
}


/* OUTPUT


Buffer:-0|0|0|0|0|0|0|0|0|0|
in :0out : 0
after producing element

Buffer:-1|0|0|0|0|0|0|0|0|0|
in :1out : 0
after consuming element

Buffer:-0|0|0|0|0|0|0|0|0|0|
in :1out : 1
after producing element

Buffer:-0|1|0|0|0|0|0|0|0|0|
in :2out : 1
after producing element

Buffer:-0|1|1|0|0|0|0|0|0|0|
in :3out : 1
after consuming element

Buffer:-0|0|1|0|0|0|0|0|0|0|
in :3out : 2
after producing element

Buffer:-0|0|1|1|0|0|0|0|0|0|
in :4out : 2
after producing element

Buffer:-0|0|1|1|1|0|0|0|0|0|
in :5out : 2
after consuming element

Buffer:-0|0|0|1|1|0|0|0|0|0|
in :5out : 3
after producing element

Buffer:-0|0|0|1|1|1|0|0|0|0|
in :6out : 3
after producing element

Buffer:-0|0|0|1|1|1|1|0|0|0|
in :7out : 3
after consuming element

Buffer:-0|0|0|0|1|1|1|0|0|0|
in :7out : 4
after producing element

Buffer:-0|0|0|0|1|1|1|1|0|0|
in :8out : 4
after producing element

Buffer:-0|0|0|0|1|1|1|1|1|0|
in :9out : 4
after consuming element

Buffer:-0|0|0|0|0|1|1|1|1|0|
in :9out : 5
after producing element

Buffer:-0|0|0|0|0|1|1|1|1|1|
in :0out : 5
after producing element

Buffer:-1|0|0|0|0|1|1|1|1|1|
in :1out : 5
after consuming element

Buffer:-1|0|0|0|0|0|1|1|1|1|
in :1out : 6
after producing element

Buffer:-1|1|0|0|0|0|1|1|1|1|
in :2out : 6
after producing element

Buffer:-1|1|1|0|0|0|1|1|1|1|
in :3out : 6
after consuming element

Buffer:-1|1|1|0|0|0|0|1|1|1|
in :3out : 7
after producing element

Buffer:-1|1|1|1|0|0|0|1|1|1|
in :4out : 7
after producing element

Buffer:-1|1|1|1|1|0|0|1|1|1|
in :5out : 7
after consuming element

Buffer:-1|1|1|1|1|0|0|0|1|1|
in :5out : 8
after producing element

Buffer:-1|1|1|1|1|1|0|0|1|1|
in :6out : 8
after producing element

Buffer:-1|1|1|1|1|1|1|0|1|1|
in :7out : 8
after consuming element

Buffer:-1|1|1|1|1|1|1|0|0|1|
in :7out : 9
after producing element

Buffer:-1|1|1|1|1|1|1|1|0|1|
in :8out : 9
Producer Bloked
after consuming element

Buffer:-1|1|1|1|1|1|1|1|0|0|
in :8out : 0
after producing element

Buffer:-1|1|1|1|1|1|1|1|1|0|
in :9out : 0
Producer Bloked
after consuming element

Buffer:-0|1|1|1|1|1|1|1|1|0|
in :9out : 1
after producing element

Buffer:-0|1|1|1|1|1|1|1|1|1|
in :0out : 1
Producer Bloked
 */



