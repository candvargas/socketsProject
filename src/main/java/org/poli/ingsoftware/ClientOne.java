package org.poli.ingsoftware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientOne {

    public static void main(String[] args){
        final String HOST = "127.0.0.1";
        final int PORT = 5000;
        DataInputStream in = null;
        DataOutputStream out = null;
        User user;

        try {
            Socket socket = new Socket(HOST, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Escribe tu nombre de usuario");
            //Insert name user
            Scanner sc = new Scanner(System.in);
            String userName = sc.nextLine();
            user = new User(userName, socket);
            // Send your name user
            out.writeUTF(user.getName());

            boolean isConexionActive = true;
            while(isConexionActive){
                // Receive message from server
                String receiveMessage = in.readUTF();
                System.out.println(receiveMessage);
                String postMessage = sc.nextLine();
                out.writeUTF(postMessage);
                isConexionActive = !postMessage.equals("chao");
                System.out.println("isConexionActive-> " + isConexionActive);
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}