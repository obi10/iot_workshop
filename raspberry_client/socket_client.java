import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

        for(int i=1; i<12; i++){
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876); //se usa el puerto 9876
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());

            if(i==11) {
                oos.writeObject("exit");
                System.out.println("Mensaje enviado");
                oos.close();
            }
            else {
                oos.writeObject("A" + i + "$15$21$2019-09-09 17:48:46.503000000");
                System.out.println("Mensaje enviado");
            
                //close resources
                oos.close();
                Thread.sleep(7000);
            }
        }
    }
}