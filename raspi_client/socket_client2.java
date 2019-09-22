import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

import com.pi4j.io.gpio.*;

/**
 * This class implements java socket client
 * @author junicode
 *
 */

// caso de uso: escalera electrica

public class socket_client2 {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getByName("52.117.221.6");
        Socket socket = null;
        ObjectOutputStream oos = null;

        GpioPinDigitalOutput sensorTriggerPin;
        GpioPinDigitalInput sensorEchoPin;
        GpioController gpio = GpioFactory.getInstance();

        int presencia = 0; //varia
        int piso = 2;
        int x = 34;
        int y = 45;

        String[] supervisores = {"Juan Perez", "Jose Diaz", "Luis Quispe", "Jorge Torres"}; //los supervisores estaran a cargo para rangos horarios especificos
        String current_supervisor = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

        if (args.length == 0) {
            System.out.println("Ingresar la cantidad de elementos que se quiere insertar en la base de datos");
            System.exit(0);
        }

        sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN); // Echo pin as INPUT

        for(int i = 1; i < Integer.parseInt(args[0]) + 2; i++){ //se insertan args[0] elementos en la base de datos
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876); //se usa el puerto 9876
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());

            if (i == Integer.parseInt(args[0]) + 1) {
                oos.writeObject("exit");
                System.out.println("Exit enviado");
                oos.close();
            }
            else {
                double valor = 0;
        
                //logica para determinar presencia (RPi.GPIO)
                try {
                    Thread.sleep(2000);
                    sensorTriggerPin.high(); // Make trigger pin HIGH
                    Thread.sleep((long) 0.01);// Delay for 10 microseconds
                    sensorTriggerPin.low(); //Make trigger pin LOW
                
                    while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH
                        
                    }
                    long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                    while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW
                        
                    }
                    long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
                
                    System.out.println("Distance :"+((((endTime-startTime)/1e3)/2) / 29.1) +" cm"); //Printing out the distance in cm 
                    valor = (((endTime-startTime)/1e3)/2) / 29.1;
                    Thread.sleep(1000);
                    
                } catch (InterruptedException e) { e.printStackTrace(); }
                
                if (valor < 10) presencia = 1;
                else presencia = 0;

                LocalDateTime current_time = LocalDateTime.now();
                if (current_time.getHour() >= 0 && current_time.getHour() <= 5) current_supervisor = supervisores[0];
                if (current_time.getHour() >= 6 && current_time.getHour() <= 11) current_supervisor = supervisores[1];
                if (current_time.getHour() >= 12 && current_time.getHour() <= 17) current_supervisor = supervisores[2];
                if (current_time.getHour() >= 18 && current_time.getHour() <= 23) current_supervisor = supervisores[3];
                

                oos.writeObject("ID" + i + "$" + presencia + "$" + piso + "$" + x + "$" + y + "$" + current_supervisor + "$" + current_time);
                System.out.println("Mensaje enviado");
            
                //close resources
                oos.close();
                Thread.sleep(7000);
            }
        }
    }
}
