package org.bookstore.dao;

import org.bookstore.dto.AuthorDTO;
import org.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorDAOTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        authorDAO = new AuthorDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void getById_Successful() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementAuthorsMock = Mockito.mock(PreparedStatement.class);
        PreparedStatement preparedStatementBooksMock = Mockito.mock(PreparedStatement.class);
        ResultSet resultSetAuthorsMock = Mockito.mock(ResultSet.class);
        ResultSet resultSetBooksMock = Mockito.mock(ResultSet.class);
        Mockito.when(connectionMock.prepareStatement("SELECT name FROM authors WHERE id =?"))
                .thenReturn(preparedStatementAuthorsMock);
        Mockito.when(preparedStatementAuthorsMock.executeQuery()).thenReturn(resultSetAuthorsMock);
        Mockito.when(resultSetAuthorsMock.next()).thenReturn(true);
        Mockito.when(resultSetAuthorsMock.getString("name")).thenReturn("Test Author");
        Mockito.when(connectionMock.prepareStatement("SELECT id, name, price FROM books WHERE authors_id=?"))
                .thenReturn(preparedStatementBooksMock);
        Mockito.when(preparedStatementBooksMock.executeQuery()).thenReturn(resultSetBooksMock);
        Mockito.when(resultSetBooksMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetBooksMock.getString("name")).thenReturn("Test Book");
        AuthorDAO authorDAO = new AuthorDAO(connectionMock);
        AuthorDTO actualAuthorDTO = authorDAO.getById(1);
        Mockito.verify(preparedStatementAuthorsMock).setInt(1, 1);
        Mockito.verify(preparedStatementAuthorsMock).executeQuery();
        assertNotNull(actualAuthorDTO);
        assertEquals("Test Author", actualAuthorDTO.getName());
        assertEquals(1, actualAuthorDTO.getId());
        assertEquals(1, actualAuthorDTO.getBooks().size());
        assertEquals("Test Book", actualAuthorDTO.getBooks().get(0).getName());
    }

    @Test
    void delete_Successful() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        AuthorDAO authorDAO = new AuthorDAO(connectionMock);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Test Author");
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", 10.0, 1));
        books.add(new Book(2, "Book 2", 15.0, 1));
        authorDTO.setBooks(books);
        authorDAO.delete(authorDTO);
        Mockito.verify(preparedStatementMock, Mockito.times(2)).setInt(anyInt(), anyInt());
        Mockito.verify(preparedStatementMock, Mockito.times(2)).executeUpdate();
    }

    @Test
    void update_WithoutBooks_Successful() throws SQLException {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Updated Author");
        authorDTO.setBooks(Collections.emptyList());
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.doNothing().when(preparedStatementMock).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.doNothing().when(preparedStatementMock).setInt(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(preparedStatementMock.executeUpdate()).thenReturn(1);
        AuthorDAO authorDAO = new AuthorDAO(connectionMock);
        authorDAO.update(authorDTO);
        Mockito.verify(connectionMock).prepareStatement("UPDATE authors SET name = ? WHERE id=?");
        Mockito.verify(preparedStatementMock).setString(1, "Updated Author");
        Mockito.verify(preparedStatementMock).setInt(2, 1);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).executeUpdate();
    }

    @Test
    void update_WithBooks_Successful() throws SQLException {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Updated Author");
        Book book1 = new Book(1, "Book 1", 10.0, 1);
        Book book2 = new Book(2, "Book 2", 15.0, 2);
        authorDTO.setBooks(Arrays.asList(book1, book2));
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.doNothing().when(preparedStatementMock).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.doNothing().when(preparedStatementMock).setInt(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(preparedStatementMock.executeUpdate()).thenReturn(1);
        AuthorDAO authorDAO = new AuthorDAO(connectionMock);
        authorDAO.update(authorDTO);
        Mockito.verify(preparedStatementMock, Mockito.times(3)).executeUpdate();
    }

    @Test
    void add_Successful() throws SQLException {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("New Author");
        Book book1 = new Book(1, "Book 1", 10.0, 1);
        Book book2 = new Book(2, "Book 2", 15.0, 1);
        authorDTO.setBooks(Arrays.asList(book1, book2));
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.doNothing().when(preparedStatementMock).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.doNothing().when(preparedStatementMock).setInt(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(preparedStatementMock.executeUpdate()).thenReturn(1);
        AuthorDAO authorDAO = new AuthorDAO(connectionMock);
        authorDAO.add(authorDTO);
        Mockito.verify(preparedStatementMock, Mockito.times(3)).executeUpdate();
    }

    @Test
    public void testConstructor() {
        assertEquals(connection, authorDAO.getConnection());
    }
}