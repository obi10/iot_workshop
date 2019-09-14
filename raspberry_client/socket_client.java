import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Random;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

/**
 * This class implements java socket client
 * @author junicode
 *
 */
public class socket_client {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;

        int humedad = 0;
        int temperatura = 0;
        String area = "A1";
        int altura = 100;

        String[] supervisores = {"Juan Perez", "Jose Diaz", "Luis Quispe", "Jorge Torres"}; //los supervisores estaran a cargo para rangos horarios especificos
        String current_supervisor = "";

        Random rand = new Random();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

        if (args.length == 0) {
            System.out.println("Ingresar la cantidad de elementos que se quiere insertar en la base de datos");
            System.exit(0);
        }

        for(int i = 1; i < Integer.parseInt(args[0]) + 2; i++){ //se insertan args[0] elementos en la base de datos
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876); //se usa el puerto 9876
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());

            if(i == Integer.parseInt(args[0]) + 1) {
                oos.writeObject("exit");
                System.out.println("Exit enviado");
                oos.close();
            }
            else {
                humedad = rand.nextInt(5) + 40; // [40-44 %] la humedad no varia drasticamente en segundos
                temperatura = rand.nextInt(5) + 20; // [20-24 celsius] la temperatura ""

                LocalDateTime current_time = LocalDateTime.now();
                if (current_time.getHour() >= 0 && current_time.getHour() <= 5) current_supervisor = supervisores[0];
                if (current_time.getHour() >= 6 && current_time.getHour() <= 11) current_supervisor = supervisores[1];
                if (current_time.getHour() >= 12 && current_time.getHour() <= 17) current_supervisor = supervisores[2];
                if (current_time.getHour() >= 18 && current_time.getHour() <= 23) current_supervisor = supervisores[3];
                

                oos.writeObject("ID" + i + "$" + humedad + "$" + temperatura + "$" + area + "$" + altura + "$" + current_supervisor + "$" + current_time);
                System.out.println("Mensaje enviado");
            
                //close resources
                oos.close();
                Thread.sleep(7000);
            }
        }
    }
}