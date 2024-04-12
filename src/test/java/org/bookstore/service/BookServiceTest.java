package org.bookstore.service;

import org.bookstore.dao.BookDAO;
import org.bookstore.dto.BookDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BookServiceTest {

    @Test
    public void testGetById() {
        BookDAO bookDAOMock = mock(BookDAO.class);
        BookService bookService = new BookService();
        bookService.setBookDAO(bookDAOMock);
        int bookId = 1;
        bookService.getById(bookId);
        verify(bookDAOMock).getById(bookId);
    }

    @Test
    public void testAdd() {
        BookDAO bookDAOMock = mock(BookDAO.class);
        BookService bookService = new BookService();
        bookService.setBookDAO(bookDAOMock);
        BookDTO bookDTO = new BookDTO();
        bookService.add(bookDTO);
        verify(bookDAOMock).add(bookDTO);
    }

    @Test
    public void testUpdate() {
        BookDAO bookDAOMock = mock(BookDAO.class);
        BookService bookService = new BookService();
        bookService.setBookDAO(bookDAOMock);
        BookDTO bookDTO = new BookDTO();
        bookService.update(bookDTO);
        verify(bookDAOMock).update(bookDTO);
    }

    @Test
    public void testDelete() {
        BookDAO bookDAOMock = mock(BookDAO.class);
        BookService bookService = new BookService();
        bookService.setBookDAO(bookDAOMock);
        BookDTO bookDTO = new BookDTO();
        bookService.delete(bookDTO);
        verify(bookDAOMock).delete(bookDTO);
    }
}