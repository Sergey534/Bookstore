package org.bookstore.dao;

import org.bookstore.dto.BookDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookDAOTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        bookDAO = new BookDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void getById_Successful() throws SQLException {
        int bookId = 1;
        String bookName = "Test Book";
        double bookPrice = 10.99;
        int authorId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(bookId);
        when(resultSet.getString("name")).thenReturn(bookName);
        when(resultSet.getDouble("price")).thenReturn(bookPrice);
        when(resultSet.getInt("authors_id")).thenReturn(authorId);
        BookDTO bookDTO = bookDAO.getById(bookId);
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, bookId);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("name");
        verify(resultSet).getDouble("price");
        verify(resultSet).getInt("authors_id");
        assert bookDTO != null;
        assert bookDTO.getId() == bookId;
        assert bookDTO.getName().equals(bookName);
        assert bookDTO.getPrice() == bookPrice;
        assert bookDTO.getAuthorId() == authorId;
    }

    @Test
    void getById_SQLException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        BookDAO bookDAO = new BookDAO(connection);
        BookDTO result = bookDAO.getById(1);
        assertNull(result);
    }

    @Test
    void add_Successful() throws SQLException {
        Book book = new Book(1, "Test Book", 10.0, 1);
        BookDTO bookDTO = bookDAO.mapToDTO(book);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        bookDAO.add(bookDTO);
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, bookDTO.getId());
        verify(preparedStatement).setString(2, bookDTO.getName());
        verify(preparedStatement).setDouble(3, bookDTO.getPrice());
        verify(preparedStatement).setInt(4, bookDTO.getAuthorId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void add_SQLException() throws SQLException {
        Book book = new Book(1, "Test Book", 10.0, 1);
        BookDTO bookDTO = bookDAO.mapToDTO(book);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, () -> bookDAO.add(bookDTO));
    }

    @Test
    void update_Successful() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1);
        bookDTO.setName("New Book Name");
        bookDTO.setPrice(20.0);
        bookDTO.setAuthorId(1);
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        BookDAO bookDAO = new BookDAO(connectionMock);
        bookDAO.update(bookDTO);
        Mockito.verify(preparedStatementMock).setString(1, "New Book Name");
        Mockito.verify(preparedStatementMock).setDouble(2, 20.0);
        Mockito.verify(preparedStatementMock).setInt(3, 1);
        Mockito.verify(preparedStatementMock).executeUpdate();
    }

    @Test
    void update_SQLException() throws SQLException {
        Book book = new Book(1, "Test Book", 10.0, 1);
        BookDTO bookDTO = bookDAO.mapToDTO(book);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, () -> bookDAO.update(bookDTO));
    }

    @Test
    void delete_Successful() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        BookDAO bookDAO = new BookDAO(connectionMock);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1);
        bookDAO.delete(bookDTO);
        Mockito.verify(preparedStatementMock).setInt(1, 1);
        Mockito.verify(preparedStatementMock).executeUpdate();
    }

    @Test
    void delete_SQLException() throws SQLException {
        Book book = new Book(1, "Test Book", 10.0, 1);
        BookDTO bookDTO = bookDAO.mapToDTO(book);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, () -> bookDAO.delete(bookDTO));
    }
}