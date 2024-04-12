package org.bookstore.dao;

import org.bookstore.dto.CustomerDTO;
import org.bookstore.model.Book;
import org.bookstore.model.Customer;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerDAOTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        customerDAO = new CustomerDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void testGetById() throws SQLException {
        int customerId = 1;
        String customerName = "Test Customer";
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        CustomerDAO customerDAO = new CustomerDAO(connectionMock);
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", 10.0, 1));
        books.add(new Book(2, "Book 2", 15.0, 1));
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(customerId);
        Mockito.when(resultSetMock.getString("name")).thenReturn(customerName);
        CustomerDAO spyCustomerDAO = Mockito.spy(customerDAO);
        Mockito.doReturn(books).when(spyCustomerDAO).getBooksForCustomer(customerId);
        CustomerDTO actualCustomerDTO = spyCustomerDAO.getById(customerId);
        assertEquals(customerId, actualCustomerDTO.getId());
        assertEquals(customerName, actualCustomerDTO.getName());
    }

    @Test
    void testGetBooksForCustomer() throws SQLException {
        int customerId = 1;
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        Mockito.when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("name")).thenReturn("Book 1");
        Mockito.when(resultSetMock.getDouble("price")).thenReturn(10.0);
        Mockito.when(resultSetMock.getInt("authors_id")).thenReturn(1);
        CustomerDAO customerDAO = new CustomerDAO(connectionMock);
        List<Book> actualBooks = customerDAO.getBooksForCustomer(customerId);
        assertEquals(1, actualBooks.size());
        assertEquals(1, actualBooks.get(0).getId());
        assertEquals("Book 1", actualBooks.get(0).getName());
        assertEquals(10.0, actualBooks.get(0).getPrice());
        assertEquals(1, actualBooks.get(0).getAuthorId());
    }

    @Test
    void testAdd_Successful() throws SQLException {
        int customerId = 1;
        String customerName = "Test Customer";
        Connection connectionMock = mock(Connection.class);
        PreparedStatement customerStatementMock = mock(PreparedStatement.class);
        PreparedStatement bookStatementMock = mock(PreparedStatement.class);
        CustomerDAO customerDAO = new CustomerDAO(connectionMock);
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", 10.0, 1));
        books.add(new Book(2, "Book 2", 15.0, 1));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName(customerName);
        customerDTO.setBooks(books);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(customerStatementMock, bookStatementMock);
        customerDAO.add(customerDTO);
        verify(customerStatementMock).executeUpdate();
        verify(bookStatementMock, Mockito.times(books.size())).executeUpdate();
    }

    @Test
    void testAdd_SQLExceptionThrown() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement customerStatementMock = mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenThrow(SQLException.class);
        CustomerDAO customerDAO = new CustomerDAO(connectionMock);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDTO.setName("Test Customer");
        assertThrows(RuntimeException.class, () -> customerDAO.add(customerDTO));
        verify(customerStatementMock, Mockito.never()).executeUpdate();
    }

    @Test
    public void testUpdateCustomer() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDTO.setName("Test Customer");
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1);
        book.setName("Book 1");
        book.setPrice(10.0);
        book.setAuthorId(1);
        books.add(book);
        Book book2 = new Book();
        book2.setId(2);
        book2.setName("Book 2");
        book2.setPrice(15.0);
        book2.setAuthorId(2);
        books.add(book2);
        customerDTO.setBooks(books);
        customerDAO.update(customerDTO);
        verify(statement).setString(1, customerDTO.getName());
    }

    @Test
    public void testUpdateCustomerBooks() throws SQLException {
        PreparedStatement deleteStatement = mock(PreparedStatement.class);
        PreparedStatement insertStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(deleteStatement, insertStatement);
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Test Customer");
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1);
        book.setName("Book 1");
        book.setPrice(10.0);
        book.setAuthorId(1);
        books.add(book);
        Book book2 = new Book();
        book2.setId(2);
        book2.setName("Book 2");
        book2.setPrice(15.0);
        book2.setAuthorId(2);
        books.add(book2);
        customer.setBooks(books);
        customerDAO.updateCustomerBooks(customerDAO.mapToDTO(customer));
        verify(deleteStatement, times(1)).setInt(1, customer.getId());
        verify(deleteStatement, times(1)).executeUpdate();
        verify(insertStatement, times(2)).setInt(eq(1), eq(customer.getId()));
        verify(insertStatement, times(1)).setInt(eq(2), eq(book.getId())); // Испо
        verify(insertStatement, times(2)).executeUpdate();
    }

    @Test
    public void testDeleteById() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement deleteCustomerStatement = mock(PreparedStatement.class);
        PreparedStatement deleteCustomerBooksStatement = mock(PreparedStatement.class);
        CustomerDAO customerDAO = new CustomerDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(deleteCustomerStatement, deleteCustomerBooksStatement);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDAO.delete(customerDTO);
        verify(deleteCustomerBooksStatement).setInt(1, customerDTO.getId());
        verify(deleteCustomerBooksStatement).executeUpdate();
        verify(deleteCustomerStatement).setInt(1, customerDTO.getId());
        verify(deleteCustomerStatement).executeUpdate();
    }
}