import java.net.Socket;
import java.awt.*;
import java.io.*;
public class MyThread extends Thread
{
Socket s;
List l;
String uname;
MyThread(String uname, Socket s, List l)
{
this.s=s;
this.l=l;
this.uname=uname;
}
public void run()
{
while(true)
{
try
{
ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
String msg=ois.readObject().toString();
l.add(uname+":"+msg);
System.out.println("Aaya"+msg);
String sss[]=msg.split("-->");
int index=Integer.parseInt(sss[0]);
ObjectOutputStream oos=new ObjectOutputStream(Server.s[index].getOutputStream());
oos.writeObject(uname+":"+sss[1]);
}
catch(Exception e)
{
System.out.println("Error:"+e.getMessage());
}
}
}
}
