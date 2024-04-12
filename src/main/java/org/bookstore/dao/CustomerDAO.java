package org.bookstore.dao;

import org.bookstore.dto.CustomerDTO;
import org.bookstore.model.Book;
import org.bookstore.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements CustomerDAOInterface {
    Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public CustomerDTO getById(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        Customer customer = new Customer();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        customer.setId(resultSet.getInt("id"));
                        customer.setName(resultSet.getString("name"));
                        customer.setBooks(getBooksForCustomer(id));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mapToDTO(customer);
    }

    public List<Book> getBooksForCustomer(int customerId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books JOIN customers_books ON books.id = customers_books.book_id WHERE customer_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = new Book();
                        book.setId(resultSet.getInt("id"));
                        book.setName(resultSet.getString("name"));
                        book.setPrice(resultSet.getDouble("price"));
                        book.setAuthorId(resultSet.getInt("authors_id"));
                        books.add(book);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    public void add(CustomerDTO customerDTO) {
        String customerQuery = "INSERT INTO customers (id,name) VALUES (?,?)";
        String customerBookQuery = "INSERT INTO customers_books (customer_id, book_id) VALUES (?, ?)";
        try {
            PreparedStatement customerStatement = connection.prepareStatement(customerQuery);
            PreparedStatement customerBookStatement = connection.prepareStatement(customerBookQuery);
            {
                customerStatement.setInt(1, customerDTO.getId());
                customerStatement.setString(2, customerDTO.getName());
                customerStatement.executeUpdate();
                int customerId = customerDTO.getId();
                List<Book> books = customerDTO.getBooks();
                for (Book book : books) {
                    int bookId = book.getId();
                    customerBookStatement.setInt(1, customerId);
                    customerBookStatement.setInt(2, bookId);
                    customerBookStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void update(CustomerDTO customerDTO) {
        String query = "UPDATE customers SET name = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customerDTO.getName());
            statement.setInt(2, customerDTO.getId());
            statement.executeUpdate();
            updateCustomerBooks(customerDTO);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCustomerBooks(CustomerDTO customerDTO) {
        String deleteQuery = "DELETE FROM customers_books WHERE customer_id = ?";
        String insertQuery = "INSERT INTO customers_books (customer_id, book_id) VALUES (?, ?)";
        try {
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            {
                deleteStatement.setInt(1, customerDTO.getId());
                deleteStatement.executeUpdate();
                for (Book book : customerDTO.getBooks()) {
                    insertStatement.setInt(1, customerDTO.getId());
                    insertStatement.setInt(2, book.getId());
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(CustomerDTO customerDTO) {
        String deleteCustomerQuery = "DELETE FROM customers WHERE id = ?";
        String deleteCustomerBooksQuery = "DELETE FROM customers_books WHERE customer_id = ?";
        try {
            PreparedStatement deleteCustomerStatement = connection.prepareStatement(deleteCustomerQuery);
            PreparedStatement deleteCustomerBooksStatement = connection.prepareStatement(deleteCustomerBooksQuery);
            deleteCustomerBooksStatement.setInt(1, customerDTO.getId());
            deleteCustomerBooksStatement.executeUpdate();
            deleteCustomerStatement.setInt(1, customerDTO.getId());
            deleteCustomerStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setBooks(customer.getBooks());
        return customerDTO;
    }
}