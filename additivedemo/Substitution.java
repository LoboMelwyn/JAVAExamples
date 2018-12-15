/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package additivedemo;

/**
 *
 * @author administrator
 */
public class Substitution {
    private String key="ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    private int length=key.length();
    protected String encrypt(String str,int keyvalue)
    {
        String encmsg="";
        int i=0,temp=0,tempe=0;
	str=str.toUpperCase();
	while(str.charAt(i)!='\0'){
            temp=tempe=+key.indexOf(str.charAt(i))+keyvalue;
            temp=temp%length;
            encmsg=encmsg+key.charAt(temp);
            temp=0;
            i=i+1;
	}
        return encmsg;
    }
    protected String decrypt(String str,int keyvalue)
    {
        int i=0,temp=0;
	String decrmsg="";
        keyvalue=keyvalue%length;
	str=str.toUpperCase();
	while(str.charAt(i)!='\0')
	{
            temp=temp+key.indexOf(str.charAt(i))-keyvalue;	
            if(temp<0)
            {
                temp=temp+length;
            }
            temp=temp%length;
            decrmsg=decrmsg+key.charAt(temp);
            temp=0;
            i++;
        }
	return decrmsg;
    }
}
