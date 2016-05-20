/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Kevin R..
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Servidor{
     ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Servidor(){}
    void run()
    {
        try{
            //1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            //2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            //3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            //4. The two parts communicate via the input and output streams
            do{
                try{
                    message = (String)in.readObject();
                  switch(message){
                        case "1" : System.out.print(message);
                            sendMessage("Martin Gabriel");
                        break;
                        case "2" : System.out.print(message);
                            sendMessage("18");
                        break;
                        case "3" : System.out.print(message);
                            ArrayList<String> fortuna = new ArrayList();
        
                            fortuna.add("No ganaras la loto");
                            fortuna.add("Encontraras 500lps");
                            fortuna.add("Te robaran");
                            fortuna.add("Pasaras el examen");
                            int random = (int)(Math.random()*100)%fortuna.size();
                            sendMessage(fortuna.get(random));
                        break;
                        case "4" : System.out.print(message);
                            sendMessage("Jugar en la PC");
                        break;
                        case "5" : System.out.println(message);
                            sendMessage("Dormir");
                        break;
                        default : System.out.println("Comando no encontrado");
                  
                  }
                       
                }
                catch(ClassNotFoundException classnot){
                    System.err.println("Data received in unknown format");
                }
            }while(!message.equals("bye"));
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                providerSocket.close();
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
            System.out.println("server>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        Servidor server = new Servidor();
        while(true){
            server.run();
        }
    }
    
}