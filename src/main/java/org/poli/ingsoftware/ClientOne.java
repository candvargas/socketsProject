package org.poli.ingsoftware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientOne {
    private final String HOST = "127.0.0.1";
    private final int PORT = 5000;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public String sendingMessage(User user){
        try {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            out = new DataOutputStream(user.getSocket().getOutputStream() );
            out.writeUTF( message);
            return message;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receivingMessage(DataInputStream in){
        String receiveMessage = null;
        try {
            receiveMessage = in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(receiveMessage);
    }

    public void startSession(){
        try {
            Socket socket = new Socket(HOST, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Escribe tu nombre de usuario");
            //Insert name user
            Scanner sc = new Scanner(System.in);
            // Send name user
            User user = new User(sc.nextLine(), socket);
            out.writeUTF(user.getName());

            boolean isConexionActive = true;
            while(isConexionActive){
                // Receive message from server
                receivingMessage(in);

                String postMessage = sendingMessage(user);
                isConexionActive = !postMessage.equals("chao");
                if (!isConexionActive){
                    endingSession(socket);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endingSession(Socket socket){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        ClientOne clientOne = new ClientOne();
        clientOne.startSession();

    }
}