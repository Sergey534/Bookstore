package org.bookstore.servlet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bookstore.dto.AuthorDTO;
import org.bookstore.service.AuthorService;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {

    private AuthorService authorService = new AuthorService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        AuthorDTO authorDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        try {
            authorDTO = authorService.getById(Integer.parseInt(id));
            if (authorDTO == null) {
                throw new NullPointerException("author is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        objectMapper.writeValue(resp.getWriter(), authorDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
            authorService.add(authorDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
            authorService.update(authorDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
            authorService.delete(authorDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (JsonGenerationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}