package org.bookstore.service;

import org.bookstore.dao.AuthorDAO;
import org.bookstore.dto.AuthorDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Test
    public void testGetById() {
        AuthorDAO authorDAOMock = mock(AuthorDAO.class);
        AuthorService authorService = new AuthorService();
        authorService.setAuthorDAO(authorDAOMock);
        int authorId = 1;
        authorService.getById(authorId);
        verify(authorDAOMock).getById(authorId);
    }

    @Test
    public void testAdd() {
        AuthorDAO authorDAOMock = mock(AuthorDAO.class);
        AuthorService authorService = new AuthorService();
        authorService.setAuthorDAO(authorDAOMock);
        AuthorDTO authorDTO = new AuthorDTO();
        authorService.add(authorDTO);
        verify(authorDAOMock).add(authorDTO);
    }

    @Test
    public void testUpdate() {
        AuthorDAO authorDAOMock = mock(AuthorDAO.class);
        AuthorService authorService = new AuthorService();
        authorService.setAuthorDAO(authorDAOMock);
        AuthorDTO authorDTO = new AuthorDTO();
        authorService.update(authorDTO);
        verify(authorDAOMock).update(authorDTO);
    }

    @Test
    public void testDelete() {
        AuthorDAO authorDAOMock = mock(AuthorDAO.class);
        AuthorService authorService = new AuthorService();
        authorService.setAuthorDAO(authorDAOMock);
        AuthorDTO authorDTO = new AuthorDTO();
        authorService.delete(authorDTO);
        verify(authorDAOMock).delete(authorDTO);
    }
}