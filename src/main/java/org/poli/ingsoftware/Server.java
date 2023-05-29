package org.poli.ingsoftware;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static final int PUERTO = 5000;
    public DataOutputStream out;
    public DataInputStream in;

    public ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        System.out.println("Servidor iniciado y contestando OK");
        boolean closeConnection= false;
        try {
            Socket cliente = this.serverSocket.accept();
            //System.out.println("cliente conectado");
            User user = writteMessageWelcome(cliente);

            while(!this.serverSocket.isClosed()){

                sendingMessage(user);

                String receiveMessage = receivingMessage(in, user);
                closeConnection = receiveMessage.equals("chao");
                if (closeConnection){
                    cliente.close();
                    System.out.println("El usuario abandono");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendingMessage(User user){
        try {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            out = new DataOutputStream(user.getSocket().getOutputStream() );
            out.writeUTF( "Server--> " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public User writteMessageWelcome(Socket cliente){
        try {
            in = new DataInputStream(cliente.getInputStream());

            User user = new User(in.readUTF(), cliente);
            System.out.println("Usuario \""+user.getName()+"\" conectado");
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(new ServerSocket(PUERTO));
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
