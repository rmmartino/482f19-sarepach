package com.example.sarepach;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

}
