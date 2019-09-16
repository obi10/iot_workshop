# iot_workshop
IoT Workshop - IoTDay - Oracle

Use case: Agribusiness
Techologies: Java, Sockets (WiFi), Docker, Oracle Database, Oracle Data Visualization, Raspberry Pi and Electronics


## Getting Started

This project was made

### Prerequisites

1. Computer with internet access and an SSH tools installed (putty, puttygen)
2. Raspberry Pi with WiFi module
3. OCI account (could be a trial)
4. Humidity and temperature sensors plus some electronic components

### Setup - Running the test

Arquitecture of the solution
[![arquitecture Screen Shot][arqui-screenshot]]

Raspberry Pi - sensors
[![raspberry Screen Shot][raspi-img]]

Raspberry Pi
1. Clone the repository `git clone https://github.com/obi10/iot_workshop.git`
2. Install java JDK, JRE (jdk-8u211-linux-x64.tar.gz)
3. Run the java program 'socket_client.java' specifying the number of connections `java socket_client <number_conn>`

Computer
1. Sign in to [OCI account](https://cloud.oracle.com)
2. Create a compute instance and enter the terminal (use putty with the respective private key)
3. Download the compressed Docker image'oracledb.tar' `wget ... oradocs-corp`
4. Install Docker `apt install docker.io`
5. Load the Docker image from the tar file `docker load < oracledb.tar`
6. Go to 'Analytics Cloud Service' in order to visualize the data derived from the raspberry.



[raspi-img]: images/raspi.png
