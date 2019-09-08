#include <ESP8266WiFi.h>

const char* ssid     = "JUNIOR";
const char* password = "09494188";

//const char* host = "192.168.43.141"; //IPv4 de la PC (servidor) (ipconfig)
const char* host = "10.100.80.36"; //IPv4 de la PC (servidor) (ipconfig)
const int port = 27015; //asegurarse que el puerto este libre (netstat -ano)
 
const int led = 2;

// Creamos una instancia de WIFICLIENT 
WiFiClient client; //client->objeto (Nodemcu)

void setup() {  
  Serial.begin(115200); //puerto serial, donde se visualizaran los mensajes
  delay(10);

  //Inicializar el led
  pinMode(led, OUTPUT);
  digitalWrite(led, LOW);
  
  //---------------------------------------------------------------------------------------------------
  // Nos conectamos a nuestro wifi
  Serial.println();
  Serial.println();
  Serial.print("Conectandose a ");
  Serial.println(ssid);

  //WiFi.config(ip,gateway,subnet,DNS);
  delay(100);
  WiFi.mode(WIFI_STA); //station mode
  WiFi.disconnect(); //se desconecta de cualquier otra red wifi
  WiFi.begin(ssid, password); //se inicia la conexion a la red Wifi

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi conectado");  
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP()); //Ip que se le asignara a NodeMCU
  //----------------------------------------------------------------------------------------------------

/*
  //----------------------------------------------------------------------------------------------------
  //conexion con el servidor 
  Serial.print("conectandose a ");
  Serial.print(host);
  if (!client.connect(host, port)) {
    Serial.println("   conexion fallida");
    return;
  }
  Serial.println("   Se logro la conexion!!");
  //----------------------------------------------------------------------------------------------------
*/
}


String line;

void loop() {

  while(client.available()){ //client.available() cuenta el numero de bytes que ha llegado (espera de envio mensajes) //se deberia quedar aqui, ya que el servidor no enviara nada
  line = client.readStringUntil('\0'); //'\0' implicit null terminator is always added 
  Serial.println(line);
    if (line.equals("inf")){
      client.println("soy el nodemcu"); //se envia un mensaje de identificacion
    }
    else{//recepcion de la ruta

    }//fin del else
  }//while(client.available())
}
