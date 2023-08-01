/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

    
class UDPServer 
{
    public static void main(String args[]) throws Exception
    {
        File myObj = new File("Courses.txt"); 
        String[] word=new String[10];
        course c[]=new course[5];

        int j=0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) 
            {
                String words[] = myReader.nextLine().split("  ");
                for(String w:words){
                    word[j]=w;
                    j++;
                }

            }
            myReader.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int k=0;  
        for(int i=0;i<5;i++)
        {
            c[i]=new course(word[k],word[k+1]);    
            k+=2;
        }    

        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[65535];
        byte[] sendData = new byte[65535];
        while(true)
        {
            DatagramPacket receivePacket =new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String s=data(receiveData).toString();
            System.out.println("FROM Client: " + s);
            s=s.replaceAll("\\s", "");
            if(s.equals("END"))
            {
                System.out.println("End Connect");
                sendData = ("End Connected Succsesfully").getBytes();
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress,port);
                serverSocket.send(sendPacket);
            }
            else
            {
                boolean found=false;
                int i;
                for(i=0;i<c.length;i++)
                {
                    if(s .equals(c[i].id))
                    {
                       found=true;
                       break;  
                    }  
                }
                if(found==true)
                    s=c[i].courseName;
                else 
                    s = "Error 404";
                sendData = s.getBytes();
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress,port);
                serverSocket.send(sendPacket);
                receiveData = new byte[65535];
            }
        }
    }

    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}


