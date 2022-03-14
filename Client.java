import java.io.*;
import java.awt.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Client extends WindowAdapter implements Runnable,ActionListener
{
Frame f;
TextField t1,t2;
Button b1,b2;
List l;
List clients;
Socket s;
public Client()
{
f=new Frame("Client");
t1=new TextField(20);
t2=new TextField(20);
l=new List();
clients=new List();
b1=new Button("Connect");
b2=new Button("Send");
b1.addActionListener(this);
b2.addActionListener(this);

Panel p1=new Panel();
Panel p2=new Panel();
p1.add(t1);
p1.add(b1);
f.addWindowListener(this);
p2.add(t2);
p2.add(b2);
f.add(p1,BorderLayout.NORTH);
f.add(p2,BorderLayout.SOUTH);
f.add(l);
f.add(clients,BorderLayout.WEST);
f.setVisible(true);
f.setSize(300,300);
}
 
void readRead()
{
while(true)
{
try
{
ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
Object o=ois.readObject();
if(o instanceof ArrayList)
{
clients.clear();
ArrayList l=(ArrayList)o;
for(Object o1:l)
{
clients.add(o1.toString());
}
}
else
{
l.add(o.toString());
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}
public static void main(String[] args)
{
Client c=new Client();
}
public void run()
{
readRead();
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==b1)
{
try
{
s=new Socket("localhost",2003);
ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
oos.writeObject(t1.getText());
ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
ArrayList l=(ArrayList)ois.readObject();
for(Object o:l)
{
clients.add(o.toString());
}
Thread t=new Thread(this);
t.start();
}
catch(Exception e1)
{
System.out.println("Error"+e1.getMessage());
}
}
else
{
try
{
System.out.println("Sent"+t2.getText());
ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
oos.writeObject(clients.getSelectedIndex()+"-->"+t2.getText());
}
catch(Exception e1)
{
System.out.println("Error"+e1.getMessage());
}
}
}
public void windowClosing(WindowEvent e)
{
System.exit(0);
}
}
