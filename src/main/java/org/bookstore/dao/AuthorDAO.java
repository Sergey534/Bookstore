package org.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.bookstore.dto.AuthorDTO;
import org.bookstore.model.Author;
import org.bookstore.model.Book;

@Getter
@Setter
public class AuthorDAO implements AuthorDAOInterface {
    Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    public AuthorDTO mapToDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBooks(author.getBooks());
        return authorDTO;
    }

    public AuthorDTO getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM authors WHERE id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Author author = new Author();
            if (resultSet.next()) {
                author.setId(id);
                author.setName(resultSet.getString("name"));
            }
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("SELECT id, name, price FROM books WHERE authors_id=?");
            preparedStatement2.setInt(1, id);
            List<Book> books = new ArrayList<>();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                String name = resultSet2.getString("name");
                int bookId = resultSet2.getInt("id");
                double price = resultSet2.getDouble("price");
                Book book = new Book(bookId, name, price, id);
                books.add(book);
            }
            author.setBooks(books);
            return mapToDTO(author);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(AuthorDTO authorDTO) {
        String insertAuthorSQL = "INSERT INTO authors (id,name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertAuthorSQL)) {
            statement.setInt(1, authorDTO.getId());
            statement.setString(2, authorDTO.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int authorId = authorDTO.getId();
        String insertBookSQL = "INSERT INTO books (id,name,price, authors_id) VALUES (?, ?, ?,?)";
        for (Book book : authorDTO.getBooks()) {
            try {
                try (PreparedStatement bookStatement = connection.prepareStatement(insertBookSQL)) {
                    bookStatement.setInt(1, book.getId());
                    bookStatement.setString(2, book.getName());
                    bookStatement.setDouble(3, book.getPrice());
                    bookStatement.setInt(4, authorId);
                    bookStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void update(AuthorDTO authorDTO) {
        String sql = "UPDATE authors SET name = ? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, authorDTO.getName());
            statement.setInt(2, authorDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String updateBookSql = "UPDATE books SET name = ?, price = ? WHERE id = ?";
        for (Book book : authorDTO.getBooks()) {
            try (PreparedStatement updateBookStmt =connection.prepareStatement(updateBookSql)) {
                updateBookStmt.setString(1, book.getName());
                updateBookStmt.setDouble(2, book.getPrice());
                updateBookStmt.setInt(3, book.getId());
                updateBookStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(AuthorDTO authorDTO) {
        String deleteBooksSQL = "DELETE FROM books WHERE authors_id = ?";
        try (PreparedStatement statement =connection.prepareStatement(deleteBooksSQL)) {
            statement.setInt(1, authorDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String deleteSQL = "DELETE FROM authors WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, authorDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}