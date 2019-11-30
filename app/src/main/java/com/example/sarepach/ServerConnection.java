/**
 * @author Patrick Sacchet
 * @version 1.0
*/
// Package dependency
package com.example.sarepach;

// Dependencies for socket protocol and logging
import java.net.URL;
import android.util.Log;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class will represent a link to the studentvhost server
*/
public class ServerConnection {

    /**
     * Each ServerConnection will have the baseaddress for our studentvhost2 server
     */
    protected final String BaseAddress = "http://sarepach.cs.loyola.edu";

    /**
     * Each ServerConnection will have the port the server is hosted on
     */
    protected final int Port = 80;

    /**
     * Method will get the current ip address of the base name server
     * @return ip - InetAddress object with which we can establish a connection
     */
    public InetAddress GetIPHost() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(new URL(this.BaseAddress).getHost());
        } catch ( java.net.MalformedURLException | java.net.UnknownHostException e) {
            Log.w("ServerConnection", "Port gathering failed" + e);
            return ip;
        }
        return ip;
    }

    /**
     * Method will establish a connection with a InetAddress object passed to it
     * @return 1 on success, 0 otherwise
     */
    public Socket EstablishConnection() {
        Socket sock = null;
        try {
            // Get the ip address via a InetAddress object
            InetAddress ip = this.GetIPHost();
            // Open a socket with the address and known port
            sock = new Socket(ip.getHostAddress(), this.Port);
            Log.w("ServerConnection", "ServerConnection established" + ip.getHostName() + ip.getHostAddress());
        } catch (java.io.IOException e) {
            Log.w("ServerConnection", "ServerConnection failed" + e);
            return sock;
        } catch(java.lang.SecurityException ee) {
            Log.w("ServerConnection", "Security Exception" + ee);
            return sock;
        }
    return sock;
    }


  
}

