package org.bookstore.service;

import org.bookstore.DbConnection;
import org.bookstore.dao.BookDAO;
import org.bookstore.dto.BookDTO;

public class BookService {
    private BookDAO bookDAO = new BookDAO(new DbConnection().getConnection());

    public BookDTO getById(int id) {
        return bookDAO.getById(id);
    }

    public void add(BookDTO bookDTO) {
        bookDAO.add(bookDTO);
    }

    public void update(BookDTO bookDTO) {
        bookDAO.update(bookDTO);
    }

    public void delete(BookDTO bookDTO) {
        bookDAO.delete(bookDTO);
    }

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
}