package org.bookstore.integration;

import org.bookstore.dao.AuthorDAO;
import org.bookstore.dao.BookDAO;
import org.bookstore.dao.CustomerDAO;
import org.bookstore.dto.AuthorDTO;
import org.bookstore.dto.BookDTO;
import org.bookstore.dto.CustomerDTO;
import org.bookstore.model.Book;
import org.bookstore.service.AuthorService;
import org.bookstore.service.BookService;
import org.bookstore.service.CustomerService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    static MyPostgreSQLContainer postgreSQLContainer = new MyPostgreSQLContainer();

    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        postgreSQLContainer.stop();
    }

    @Test
    @Order(1)
    void testAddAuthor() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(1);
            authorDTO.setName("Test Author");
            List<Book> books = new ArrayList<>();
            books.add(new Book(1, "Book 1", 10.0, 1));
            books.add(new Book(2, "Book 2", 15.0, 1));
            authorDTO.setBooks(books);
            authorService.add(authorDTO);
            AuthorDTO retrievedAuthorDTO = authorService.getById(authorDTO.getId());
            assertNotNull(retrievedAuthorDTO);
            assertEquals(authorDTO.getName(), retrievedAuthorDTO.getName());
            assertEquals(authorDTO.getId(), retrievedAuthorDTO.getId());
            assertEquals(authorDTO.getBooks(), retrievedAuthorDTO.getBooks());
        }
    }

    @Test
    @Order(2)
    void testGetAuthorById() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO retrievedAuthorDTO = authorService.getById(1);
            assertNotNull(retrievedAuthorDTO);
            assertEquals("Test Author", retrievedAuthorDTO.getName());
        }
    }

    @Test
    @Order(3)
    void testUpdateAuthor() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(1);
            authorDTO.setName("AuthorUpdated");
            List<Book> books = new ArrayList<>();
            books.add(new Book(1, "Book 3", 10.0, 1));
            books.add(new Book(2, "Book 4", 15.0, 1));
            authorDTO.setBooks(books);
            authorService.update(authorDTO);
            AuthorDTO retrievedAuthorDTO = authorService.getById(1);
            assertNotNull(retrievedAuthorDTO);
            assertEquals("AuthorUpdated", retrievedAuthorDTO.getName());
        }
    }


    @Test
    @Order(4)
    void testDeleteAuthor() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(1);
            authorDTO.setName("AuthorUpdated");
            List<Book> books = new ArrayList<>();
            books.add(new Book(1, "Book 3", 10.0, 1));
            books.add(new Book(2, "Book 4", 15.0, 1));
            authorDTO.setBooks(books);
            authorService.delete(authorDTO);
            AuthorDTO retrievedAuthorDTO = authorService.getById(1);
            assertNull(retrievedAuthorDTO);
        }
    }

    @Test
    @Order(5)
    void testAddBook() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(1);
            authorDTO.setName("Test Author");
            List<Book> books = new ArrayList<>();
            books.add(new Book(2, "Book 1", 10.0, 1));
            authorDTO.setBooks(books);
            authorService.add(authorDTO);
            BookDAO bookDAO = new BookDAO(connection);
            BookService bookService = new BookService();
            bookService.setBookDAO(bookDAO);
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthorId(1);
            bookDTO.setPrice(10.0);
            bookDTO.setId(1);
            bookDTO.setName("TestBook");
            bookService.add(bookDTO);
            BookDTO retrievedBookDTO = bookService.getById(bookDTO.getId());
            assertNotNull(retrievedBookDTO);
            assertEquals(bookDTO.getName(), retrievedBookDTO.getName());
            assertEquals(bookDTO.getId(), retrievedBookDTO.getId());
            assertEquals(bookDTO.getAuthorId(), retrievedBookDTO.getAuthorId());
            assertEquals(bookDTO.getPrice(), retrievedBookDTO.getPrice());
        }
    }

    @Test
    @Order(6)
    void testGetBookById() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            AuthorService authorService = new AuthorService();
            authorService.setAuthorDAO(authorDAO);
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(2);
            authorDTO.setName("TestAuthor2");
            List<Book> books = new ArrayList<>();
            books.add(new Book(3, "Book 3", 10.0, 2));
            books.add(new Book(4, "Book 4", 15.0, 2));
            authorDTO.setBooks(books);
            authorService.add(authorDTO);
            BookDAO bookDAO = new BookDAO(connection);
            BookService bookService = new BookService();
            bookService.setBookDAO(bookDAO);
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthorId(2);
            bookDTO.setPrice(10.0);
            bookDTO.setId(5);
            bookDTO.setName("TestBook5");
            bookService.add(bookDTO);
            BookDTO retrievedBookDTO = bookService.getById(bookDTO.getId());
            assertNotNull(retrievedBookDTO);
            assertEquals(bookDTO.getName(), retrievedBookDTO.getName());
            assertEquals(bookDTO.getId(), retrievedBookDTO.getId());
            assertEquals(bookDTO.getAuthorId(), retrievedBookDTO.getAuthorId());
            assertEquals(bookDTO.getPrice(), retrievedBookDTO.getPrice());
        }
    }

    @Test
    @Order(7)
    void testUpdateBook() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            BookDAO bookDAO = new BookDAO(connection);
            BookService bookService = new BookService();
            bookService.setBookDAO(bookDAO);
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthorId(1);
            bookDTO.setPrice(15.0);
            bookDTO.setId(1);
            bookDTO.setName("TestBook6");
            bookService.update(bookDTO);
            BookDTO retrievedBookDTO = bookService.getById(bookDTO.getId());
            assertNotNull(retrievedBookDTO);
            assertEquals(bookDTO.getName(), retrievedBookDTO.getName());
            assertEquals(bookDTO.getId(), retrievedBookDTO.getId());
            assertEquals(bookDTO.getAuthorId(), retrievedBookDTO.getAuthorId());
            assertEquals(bookDTO.getPrice(), retrievedBookDTO.getPrice());
        }
    }

    @Test
    @Order(8)
    void testDeleteBook() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            BookDAO bookDAO = new BookDAO(connection);
            BookService bookService = new BookService();
            bookService.setBookDAO(bookDAO);
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthorId(1);
            bookDTO.setPrice(15.0);
            bookDTO.setId(1);
            bookDTO.setName("TestBook6");
            bookService.delete(bookDTO);
            BookDTO retrievedBookDTO = bookService.getById(bookDTO.getId());
            assertNull(retrievedBookDTO);
        }
    }

    @Test
    @Order(9)
    void testAddCustomer() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            CustomerService customerService = new CustomerService();
            customerService.setCustomerDAO(customerDAO);
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(1);
            customerDTO.setName("Test Customer");
            List<Book> books = new ArrayList<>();
            books.add(new Book(5, "TestBook5", 10.0, 2));
            customerDTO.setBooks(books);
            customerService.add(customerDTO);
            CustomerDTO retrievedCustomerDTO = customerService.getById(customerDTO.getId());
            assertNotNull(retrievedCustomerDTO);
            assertEquals(customerDTO.getName(), retrievedCustomerDTO.getName());
            assertEquals(customerDTO.getId(), retrievedCustomerDTO.getId());
            assertEquals(customerDTO.getBooks(), retrievedCustomerDTO.getBooks());
        }
    }

    @Test
    @Order(10)
    void testGetCustomerById() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            CustomerService customerService = new CustomerService();
            customerService.setCustomerDAO(customerDAO);
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(1);
            customerDTO.setName("Test Customer");
            List<Book> books = new ArrayList<>();
            books.add(new Book(5, "TestBook5", 10.0, 2));
            customerDTO.setBooks(books);
            customerService.getById(customerDTO.getId());
            CustomerDTO retrievedCustomerDTO = customerService.getById(customerDTO.getId());
            assertNotNull(retrievedCustomerDTO);
            assertEquals(customerDTO.getName(), retrievedCustomerDTO.getName());
            assertEquals(customerDTO.getId(), retrievedCustomerDTO.getId());
            assertEquals(customerDTO.getBooks(), retrievedCustomerDTO.getBooks());
        }
    }

    @Test
    @Order(11)
    void testUpdateCustomerById() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            CustomerService customerService = new CustomerService();
            customerService.setCustomerDAO(customerDAO);
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(1);
            customerDTO.setName("Customer update");
            List<Book> books = new ArrayList<>();
            books.add(new Book(5, "TestBook5", 10.0, 2));
            customerDTO.setBooks(books);
            customerService.update(customerDTO);
            CustomerDTO retrievedCustomerDTO = customerService.getById(customerDTO.getId());
            assertNotNull(retrievedCustomerDTO);
            assertEquals(customerDTO.getName(), retrievedCustomerDTO.getName());
            assertEquals(customerDTO.getId(), retrievedCustomerDTO.getId());
            assertEquals(customerDTO.getBooks(), retrievedCustomerDTO.getBooks());
        }
    }

    @Test
    @Order(12)
    void testDeleteCustomer() throws SQLException {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            CustomerService customerService = new CustomerService();
            customerService.setCustomerDAO(customerDAO);
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(1);
            customerDTO.setName("Customer update");
            List<Book> books = new ArrayList<>();
            books.add(new Book(5, "TestBook5", 10.0, 2));
            customerDTO.setBooks(books);
            customerService.delete(customerDTO);
            CustomerDTO retrievedCustomerDTO = customerService.getById(customerDTO.getId());
            assertNull(retrievedCustomerDTO);
        }
    }
}