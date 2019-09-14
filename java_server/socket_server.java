
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.*;

import java.util.Arrays;

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

        Socket socket = null;
        ObjectInputStream ois = null;

        String message = "";
        String [] datos = new String[7]; //7 elementos


        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Esperando la conexion del cliente ...");
            socket = listenfd.accept(); //creating socket and waiting for client connection (listen and accept)
            
            ois = new ObjectInputStream(socket.getInputStream()); //read from socket to ObjectInputStream object
            message = (String) ois.readObject(); //convert ObjectInputStream object to String
            System.out.println("Mensaje recibido " + message);

            //terminate the listenfd if client sends exit request
            if(message.equalsIgnoreCase("exit")) {
                ois.close();
                socket.close(); System.out.println("conexion cerrada");
                break;
            }

            //logica de separacion del string recibido (ID1$44$22$A1$100$Jorge Torres$2019-09-13T18:47:41.268)
            datos = message.split("\\$");
            String time_timestamp_format = datos[6].replace('T', ' ');

            System.out.println(datos[0] +  " " + datos[1] + " " + datos[2] + " " + datos[3] + " " + datos[4] + " " + datos[5] + " " + time_timestamp_format);

            try {
            //se inserta la data recibida en la base de datos
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO AGRO VALUES ('" + datos[0] + "', '" + datos[1] + "', '" + datos[2] + "', '" + datos[3] + "', '" + datos[4] + 
                            "', '" + datos[5] + "', TO_TIMESTAMP('" + time_timestamp_format + "', 'YYYY-MM-DD HH24:MI:SS.FF'))");

            //close database resources
            st.close();

            } catch (Exception e) { 
                System.err.println("Got an exception! "); 
                System.err.println(e.getMessage());
            }


            //close resources
            ois.close();
            socket.close(); System.out.println("conexion cerrada");
        }
        
        try { conn.close(); } catch (Exception e) { System.err.println(e.getMessage()); }
        listenfd.close(); //close the ServerSocket object

        System.out.println("server terminated!!");

        
    }
    
}