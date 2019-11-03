package com.example.sarepach;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import android.util.Log;
import java.sql.Connection;

/**
 * Database class, parent class of all types of databases
 * @author Patrick Sacchet
 * @version - 1.0 - 11/1/19
 */
public class Database {

    /**
     * Each connection to a database will require a hostname that will be kept as a string
     */
    protected String Hostname;

    /**
     * Each connection will require a username to login
     */
    protected String Username;

    /**
     * Each connection will require a password to login
     */
    protected String Password;

    /**
     * Database constructor
     * @param hostname - hostname for the connection
     * @param username - username for the connection
     * @param password - password for the connection
     */
    Database(String hostname, String username, String password) {
        this.Hostname = hostname;
        this.Username = username;
        this.Password = password;
    }

    /**
     * Establishes connection with live database, will write to log for errors
     * @return con - connection database object
     */
    public Connection EstablishConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.Hostname, this.Username, this.Password);
        }
        catch (SQLException err) {
            Log.w("Database", "Database connection error " + err);
        }
        return conn;
    }

}
