package com.example.sarepach;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestServerConnection {

    @Test
    public void TestEstablishConnection() {
        ServerConnection serverconnect = new ServerConnection();
        assertEquals(serverconnect.EstablishConnection("144.126.12.148", 80), 1);
    }
}
