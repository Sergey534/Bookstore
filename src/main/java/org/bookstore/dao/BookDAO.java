package org.bookstore.dao;

import org.bookstore.dto.BookDTO;
import org.bookstore.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO implements BookDAOInterface {
    Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public BookDTO getById(int id) {
        Book book = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, price, authors_id FROM books WHERE id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            book = new Book();
            if (resultSet.next()) {
                book.setId(id);
                book.setPrice(resultSet.getDouble("price"));
                book.setName(resultSet.getString("name"));
                book.setAuthorId(resultSet.getInt("authors_id"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mapToDTO(book);
    }

    public void add(BookDTO bookDTO) {
        String sql = "INSERT INTO books (id, name, price, authors_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookDTO.getId());
            statement.setString(2, bookDTO.getName());
            statement.setDouble(3, bookDTO.getPrice());
            statement.setInt(4, bookDTO.getAuthorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public void update(BookDTO bookDTO) {
        String sql = "UPDATE books SET name = ?, price = ?, authors_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookDTO.getName());
            statement.setDouble(2, bookDTO.getPrice());
            statement.setInt(3, bookDTO.getAuthorId());
            statement.setInt(4, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void delete(BookDTO bookDTO) {
        String deleteBooksSQL = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteBooksSQL)) {
            statement.setInt(1, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public BookDTO mapToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setAuthorId(book.getAuthorId());
        return bookDTO;
    }
}