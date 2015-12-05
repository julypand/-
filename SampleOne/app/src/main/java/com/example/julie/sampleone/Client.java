package com.example.julie.sampleone;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Julie on 15.11.2015.
 */
public class Client implements Runnable {

    Client(){
        run();
    }
    @Override
    public void run() {
        try{
            byte[] receiveData = new byte[1024];
            String msg = "Hello Julie!";
            int server_port = 9999;//8600;
            DatagramSocket socket = new DatagramSocket();
            InetAddress local = InetAddress.getByName("192.168.1.5");
            int msg_length = msg.length();
            byte[] message = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(message, msg_length, local, server_port);
            socket.send(packet);

            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                System.out.println ("From: " + IPAddress + ":" + port);
                System.out.println ("Message: " + sentence.substring(0, receivePacket.getLength()-1));
            }


        } catch (Exception e){
        }
    }
}
