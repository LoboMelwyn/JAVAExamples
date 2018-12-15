import java.io.ByteArrayInputStream;
import java.io.File ;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat;
public class SineSound
{
  public static void main(String[] args)
  {
    byte[] data;
    AudioFormat format;
    int amplitude = 10000; // [0..32767]
    int sampleF = 44100;
    int signalF = 440;
    float maximumBufferLengthInSeconds = 1.0F;
    int maximumBufferLengthInFrames=(int)(maximumBufferLengthInSeconds*sampleF);
    int periodLengthInFrames = sampleF/signalF;
    if ( (periodLengthInFrames%2)!=0)
    {
      periodLengthInFrames++;
    }
    int numPeriodsInBuffer=maximumBufferLengthInFrames/periodLengthInFrames;
    int numFramesInBuffer=numPeriodsInBuffer*periodLengthInFrames;
    int bufferLength = numFramesInBuffer*4;
    data = new byte [bufferLength] ;
    format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,sampleF,16,2,4,sampleF,false) ;
    for(int period=0;period<numPeriodsInBuffer;period++)
    {
      for(int frame=0;frame<periodLengthInFrames;frame++)
      {
        int value = 0;
        value = (int)(Math.sin(((double)frame/(double)periodLengthInFrames)*2.0*Math. PI)*amplitude) ;
        int baseAddr = (period*periodLengthInFrames+frame)*4;
        data [baseAddr+0] = (byte) (value & 0xFF) ;
        data[baseAddr+1] = (byte) ((value >>> 8) & 0xFF);
        data[baseAddr+2] = (byte) (value & 0xFF) ;
        data[baseAddr+3] = (byte) ((value >>> 8) & 0xFF) ;
      }
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    AudioInputStream ais=new AudioInputStream(bais,format,data.length/format.getFrameSize());
    try
    {
      AudioSystem.write(ais,AudioFileFormat.Type.WAVE,new File("sine.wav"));
    }
    catch(Exception e)
    {
      System.out.println("ERROR: "+e);
    }
  }
}