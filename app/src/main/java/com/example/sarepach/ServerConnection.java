/**
 * @author Patrick Sacchet
 * @version 1.0
*/
// Package dependency
package com.example.sarepach;

// Dependencies for socket protocol and logging
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import android.util.Log;
import java.net.InetAddress;
import java.net.Socket;

// Separate additions for AsyncTask connection to server
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

/**
 * Class will represent a link to the studentvhost server
*/
public class ServerConnection {

    /**
     * Temporary additions for testing purposes
     */
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    TextView textPHP;

    /**
     * Each ServerConnection will have the baseaddress for our studentvhost2 server
     */
    protected final String BaseAddress = "http://studentvhost2.cs.loyola.edu";

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

    /**
     * Method allows us to send specific messages to connected server
     * @param message - message to be semt to client
     * @param sock - established sock connection with client
     */
    public void SendMessage(String message, Socket sock) {
        try {
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject("test");
            ois = new ObjectInputStream(sock.getInputStream());
            String messageout = (String) ois.readObject();
            Log.w("ServerConnection", "Message received: " + messageout);
            ois.close();
            oos.close();
        } catch(java.io.IOException | java.lang.ClassNotFoundException e ) {
            Log.w("ServerConnection", "Error with message transmit" + e);
        }

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textPHP = (TextView) findViewById(R.id.textPHP);
//        //Make call to AsyncRetrieve
//        new AsyncRetrieve().execute();
 //   }

    public class AsyncRetrieve extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        public String doInBackground() {
            try {
                // Only testing admin code for now (will execute client code instead)
                url = new URL("sarepach.cs.loyola.edu/Administrators/viewItem.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

    }

}

