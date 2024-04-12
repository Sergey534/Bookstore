package org.bookstore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DbConnectionTest {

    @Test
    public void testGetConnection() throws SQLException {
        Connection mockConnection = Mockito.mock(Connection.class);
        DbConnection dbConnection = Mockito.spy(new DbConnection());
        when(dbConnection.getConnection()).thenReturn(mockConnection);
        Connection connection = dbConnection.getConnection();
        assertNotNull(connection);
    }
}