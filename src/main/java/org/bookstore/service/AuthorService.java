package org.bookstore.service;

import org.bookstore.DbConnection;
import org.bookstore.dao.AuthorDAO;
import org.bookstore.dto.AuthorDTO;

public class AuthorService {
    private AuthorDAO authorDAO = new AuthorDAO(new DbConnection().getConnection());

    public AuthorDTO getById(int id) {
        return authorDAO.getById(id);
    }

    public void add(AuthorDTO authorDTO) {
        authorDAO.add(authorDTO);
    }

    public void update(AuthorDTO authorDTO) {
        authorDAO.update(authorDTO);
    }

    public void delete(AuthorDTO authorDTO) {
        authorDAO.delete(authorDTO);
    }

    public void setAuthorDAO(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }
}