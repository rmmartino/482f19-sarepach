package com.example.sarepach;

import org.junit.Assert.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for Database class
 * @author - Patrick Sacchet
 * @version 1.0 - 11/1/19
 */

public class TestDatabase {

    @Test
    public void TestDatabaseCreation() {
        Database db = new Database("cs-database.cs.loyola.edu", "pjsacchet", "1737480");
        assertEquals(db.Hostname, "cs-database.cs.loyola.edu");
        assertEquals(db.Username, "pjsacchet");
        assertEquals(db.Password, "1737480");
    }




}
