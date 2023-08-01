/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPServer 
{
    public static void main(String argv[]) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(6789);
        File myObj = new File("student.txt"); 
        String[] item=new String[10];
        course c[]=new course[5];

        int j=0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) 
            {
                String items[] = myReader.nextLine().split("  ");
                for(String w:items)
                {
                    item[j]=w;
                    j++;
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
        }
        int k=0;  
        for(int i=0;i<5;i++)
        {
            c[i]=new course(item[k],item[k+1]);    
            k+=2;
        }
        while(true) 
        {
            Socket connectionSocket = serverSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String s = inFromClient.readLine();
            System.out.println("FROM Client: " + s );
            //delete any spaces in the message, assume the request from client (assume _ is space) ___10636111 it delete spaces to be : 10636111
            s=s.replaceAll("\\s", "");
            if(s.equals("END"))
            {
                System.out.println("End Connect");
                outToClient.writeBytes("End Connected Succsesfully"+'\n');
            }
            else
            {
                boolean found=false;
                int i;
                for(i=0;i<c.length;i++)
                {
                    if(s.equals(c[i].id))
                    {
                       found=true;
                       break;  
                    }  
                }
                if(found==true)
                    s=c[i].courseName;
                else 
                    s="Error 404";
                outToClient.writeBytes(s+'\n');
            }
        }
    }
}