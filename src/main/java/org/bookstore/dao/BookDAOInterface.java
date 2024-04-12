package org.bookstore.dao;

import org.bookstore.dto.BookDTO;

public interface BookDAOInterface {

    BookDTO getById(int id);

    void add(BookDTO bookDTO);

    void update(BookDTO bookDTO);

    void delete(BookDTO bookDTO);
}