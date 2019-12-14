/**
 * @author Patrick Sacchet
 * @version 1.0
 */
// Package dependency
package com.example.sarepach;

// Testing dependencies
import org.junit.Test;
import static org.junit.Assert.assertEquals;

// ServeConnect dependencies
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class will serve to implement unit tests for all aspects of a ServerConnection class
 */
public class TestServerConnection {

    /**
     * Testing ServerConnect ability to grab ip address of our host server
     */
    @Test
    public void TestGetIPHost() {
        ServerConnection serverconnect = new ServerConnection();
        InetAddress ip = serverconnect.GetIPHost();
        String hostaddress = ip.getHostAddress();
        assertEquals(hostaddress, "144.126.12.148");
    }

    /**
     * Testing ServerConnect ability to establish a connection to our host via ip address and port confirmation
     */
    @Test
    public void TestEstablishConnection() {
        ServerConnection serverconnect = new ServerConnection();
        Socket sock = serverconnect.EstablishConnection();
        assertEquals(sock.getInetAddress().getHostAddress(), "144.126.12.148");
        assertEquals(sock.getPort(), 80);
    }

    /**
     * Testing ServerConnect ability to send message successfully to client
     */
    @Test
    public void TestSendMessage() {
        ServerConnection serverconnect = new ServerConnection();
        Socket sock = serverconnect.EstablishConnection();
        serverconnect.SendMessage("test", sock);
    }


}
