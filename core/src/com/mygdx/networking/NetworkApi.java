package com.mygdx.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Wojciech on 2020-01-22.
 */
public class NetworkApi {
    public List<String> addresses;
    int port = 9021;

    public Address myAddress;

    public static NetworkApi manager;

    public static void create() {
        manager = new NetworkApi();
    }

    public NetworkApi() {
        addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }



        new Thread(new Runnable(){
            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                serverSocketHint.acceptTimeout = 0;
                ServerSocket serverSocket = null;
                while (true) {
                    try {
                        serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, port, serverSocketHint);
                        break;
                    } catch (Exception e) {
                        port++;
                    }
                }
                myAddress = new Address(addresses.get(1), port);
                for (String adr: addresses) {
                    Gdx.app.log("NetworkApi", "address: " + adr + ":" + port);
                }

                // Loop forever
                while(true){
                    Socket socket = serverSocket.accept(null);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        String message = buffer.readLine();
                        NetworkManager.networkManager.createEventsToHandle(message);
                        Gdx.app.log("NetworkApi", "received message: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); // And, start the thread running
    }

    public void send(String address, int port, String message) {
        message += "\n";
        Gdx.app.log("NetworkApi", "sending message - address: " + address + ", port: " + port + ", message: " + message);
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 4000;
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, address, port, socketHints);
        try {
            socket.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
