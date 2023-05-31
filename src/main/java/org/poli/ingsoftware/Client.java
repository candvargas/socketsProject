package org.poli.ingsoftware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class works like a client
 * @author subgrupo_13
 */
public class Client {
    private final String HOST = "127.0.0.1";
    private final int PORT = 5000;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    /**
     * Method sending messages to server
     * @param user
     * @return
     */
    public String sendingMessage(User user){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.printf(user.getName()+"--> ");
            String message = sc.nextLine();
            out = new DataOutputStream(user.getSocket().getOutputStream() );
            out.writeUTF( message);
            return message;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method receiving message from server
     * @param in
     */
    public void receivingMessage(DataInputStream in){
        String receiveMessage = null;
        try {
            receiveMessage = in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(receiveMessage);
    }

    /**
     * Method start socket and it is the key method
     */
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

    /**
     * This method close socket
     * @param socket
     */
    public void endingSession(Socket socket){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Begin client
     * @param args
     */
    public static void main(String[] args){
        Client client = new Client();
        client.startSession();

    }
}