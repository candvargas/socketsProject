package org.poli.ingsoftware;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            while(!this.serverSocket.isClosed()){
                Socket cliente = this.serverSocket.accept();
                //System.out.println("cliente conectado");
                writteMessageWelcome(cliente);

//                out = new DataOutputStream( cliente.getOutputStream() );
//                out.writeUTF( "Hola cliente care care" );

                closeConnection = in.readUTF().equals("chao");
                if (closeConnection){
                    cliente.close();
                    System.out.println("El usuario abandono");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writteMessageWelcome(Socket cliente){
        try {
            in = new DataInputStream(cliente.getInputStream());
            String nameUser = in.readUTF();

            System.out.println("Usuario \""+nameUser+"\" conectado");
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
