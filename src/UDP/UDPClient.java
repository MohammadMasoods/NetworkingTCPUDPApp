/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;
import java.io.*;
import java.net.*;
class UDPClient 
{
    public static void main(String args[]) throws Exception
    {
        while(true){
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
            byte[] sendData = new byte[65535];
            byte[] receiveData = new byte[65535];
            System.out.print("Clinet: ");
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence=data(receiveData).toString();
            if(modifiedSentence.equals("End Connected Succsesfully"))
            {
                System.out.println("FROM SERVER: " + modifiedSentence);
                clientSocket.close();
                break;
            }
            else
                System.out.println("FROM SERVER: " + modifiedSentence);
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
