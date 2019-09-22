# iot_workshop
IoT Workshop - IoTDay - Oracle

Use case: Agribusiness
Techologies: Java, Sockets (WiFi), Docker, Oracle Database, Oracle Data Visualization, Raspberry Pi and Electronics


## Getting Started

This project was made to demonstrate how easy is to collect data (humidity and temperature) and transmit it a remote server through internet.

### Prerequisites

1. Computer with internet access and SSH tools installed (putty, puttygen)
2. Raspberry Pi with WiFi module
3. OCI account (could be a trial)
4. Humidity and temperature sensors plus some electronic components

### Setup - Running the test

__Arquitecture of the solution__
![arquitecture Screen Shot](http://images/arquitectura_solucion.png)

__Raspberry Pi - sensors__
![raspberry Screen Shot](http://images/raspi.png)

__Raspberry Pi__
1. Clone the repository `git clone https://github.com/obi10/iot_workshop.git`
2. Install java JDK, JRE (jdk-8u211-linux-x64.tar.gz)
3. Go to raspi_cliente folder and run the java program 'socket_client.java' specifying the number of connections `java socket_client <number_conn>`

__Computer__
1. Sign in to [OCI account](https://cloud.oracle.com)
2. Enter to Oracle Data Visualization Cloud Service
