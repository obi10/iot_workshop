
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.*;

/**
 * This class implements java Socket server
 * @author junicode
 *
 */


public class socket_server {
    
    private static Connection conn;
    private static ServerSocket listenfd;
    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        //connect to the database
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try { conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:32769/ORCLCDB.localdomain","oracle","oracle"); } catch (Exception e) { System.err.println(e.getMessage()); }
        System.out.println("Conexion exitosa a la base de datos");
        //create the socket server object
        listenfd = new ServerSocket(9876); //se especifica el puerto


        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Esperando la conexion del cliente ...");
            Socket socket = listenfd.accept(); //creating socket and waiting for client connection (listen and accept)
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); //read from socket to ObjectInputStream object
            String message = (String) ois.readObject(); //convert ObjectInputStream object to String
            System.out.println("Mensaje recibido " + message);

            //logica de separacion del string recibido

            try {
            //se inserta la data recibida en la base de datos
            Statement st = conn.createStatement();


            //close database resources
            st.close();

            } catch (Exception e) { 
                System.err.println("Got an exception! "); 
                System.err.println(e.getMessage());
            }
            


            //close resources
            ois.close();
            socket.close(); System.out.println("conexion cerrada");

            //terminate the listenfd if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        
        try { conn.close(); } catch (Exception e) { System.err.println(e.getMessage()); }
        listenfd.close(); //close the ServerSocket object

        System.out.println("server terminated!!");

        
    }
    
}