package org.poli.ingsoftware;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class works like a server
 * @author subgrupo_13
 */
public class Server {
    public static final int PUERTO = 5000;
    public DataOutputStream out;
    public DataInputStream in;

    public ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Method to start server, it is the key method
     */
    public void startServer(){
        System.out.println("Servidor iniciado y contestando OK");
        boolean closeConnection= false;
        try {
            Socket cliente = this.serverSocket.accept();
            User user = writeMessageWelcome(cliente);

            while(!this.serverSocket.isClosed()){

                sendingMessage(user);

                String receiveMessage = receivingMessage(in, user);
                closeConnection = receiveMessage.equals("chao");
                if (closeConnection){
                    this.serverSocket.close();
                    System.out.println("El usuario abandono");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method works to send messages
     * @param user
     */
    public void sendingMessage(User user){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.printf("Server--> ");
            String message = sc.nextLine();
            out = new DataOutputStream(user.getSocket().getOutputStream() );
            out.writeUTF( "Server--> " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method works to receive messages
     * @param in
     * @param user
     * @return
     */
    public String receivingMessage(DataInputStream in, User user){
        String receiveMessage = null;
        try {
            receiveMessage = in.readUTF();
            System.out.println(user.getName()+ "--> " + receiveMessage);
            return receiveMessage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method write welcome message
     * @param cliente
     * @return
     */
    public User writeMessageWelcome(Socket cliente){
        try {
            in = new DataInputStream(cliente.getInputStream());

            User user = new User(in.readUTF(), cliente);
            System.out.println("Usuario \""+user.getName()+"\" conectado");
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Begin server
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(new ServerSocket(PUERTO));
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
