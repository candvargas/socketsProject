package org.poli.ingsoftware;

import java.net.Socket;

/**
 * This class works to abstract users
 * @author subgrupo_13
 */
public class User {
    private String name;
    private Socket socket;

    public User(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", socket=" + socket +
                '}';
    }
}
