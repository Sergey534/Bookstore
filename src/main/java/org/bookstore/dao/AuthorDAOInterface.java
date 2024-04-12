package org.bookstore.dao;

import org.bookstore.dto.AuthorDTO;

public interface AuthorDAOInterface {

    AuthorDTO getById(int id);

    void add(AuthorDTO authorDTO);

    void update(AuthorDTO authorDTO);

    void delete(AuthorDTO authorDTO);
}
