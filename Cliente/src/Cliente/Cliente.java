/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Cliente{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Cliente(){}
    void run()
    {
        try{
            //1. creating a socket to connect to the server
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //3: Communicating with the server
            Scanner rd = new Scanner(System.in);
            do{
                try{
                    message = (String)in.readObject();
                    System.out.println("Connected");
                    System.out.println("Ingrese uno de los comandos siguientes");
                    System.out.println("1: Nombre");
                    System.out.println("2: Edad");
                    System.out.println("3: Suerte?");
                    System.out.println("4: Hobbie");
                    System.out.println("5: ¿Que haces?");
                    System.out.println("server>" + message);
                    sendMessage("Hi my server");
                    System.out.print("cliente> ");
                    message = rd.next();
                    sendMessage(message);
                    
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!message.equals("bye"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        Cliente client = new Cliente();
        client.run();
    }
}
