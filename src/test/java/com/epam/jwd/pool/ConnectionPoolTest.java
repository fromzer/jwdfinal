package com.epam.jwd.pool;

import com.epam.jwd.context.DatabaseProperties;
import com.epam.jwd.util.PropertyReaderUtil;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private List<ProxyConnection> connections = new ArrayList<>();

    @Test
    public void testGetConnection() {
        DatabaseProperties properties = PropertyReaderUtil.readDBProperties();
        int poolSize = properties.getDbPoolSize();
        for (int i = 0; i < poolSize; i++) {
            connections.add(connectionPool.getConnection());
        }
        ProxyConnection connectionFromList = connections.get(0);
        connectionPool.returnConnection(connectionFromList);
        ProxyConnection connectionFromPool = connectionPool.getConnection();
        assertEquals(connectionFromPool, connectionFromList);
    }
}