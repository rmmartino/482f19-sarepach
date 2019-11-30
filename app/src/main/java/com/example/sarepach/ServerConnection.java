/**
 * @author Patrick Sacchet
 * @version 1.0
*/

package com.example.sarepach;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import android.util.Log;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Class will represent a link to the studentvhost server
*/
public class ServerConnection {

    public int EstablishConnection(String baseaddress, int port) {
        try {
            InetAddress addr;
            InetAddress ip = InetAddress.getByName(new URL("http://sarepach.cs.loyola.edu").getHost());
            //Socket sock = new Socket(baseaddress, port);
            Socket sock = new Socket(baseaddress, port);
            addr = sock.getInetAddress();
            //System.out.println(addr);
            Log.w("ServerConnection", "ServerConnection established" + addr);
            sock.close();
            return 1;
        } catch (java.io.IOException e) {
            Log.w("ServerConnection", "ServerConnection failed" + e);
            //System.out.println(e);
            return 0;
        } catch(java.lang.SecurityException ee) {
            Log.w("ServerConnection", "Security Exception" + ee);
        }
    return 0;
    }


  
}

