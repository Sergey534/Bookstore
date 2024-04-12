package org.bookstore.dao;

import org.bookstore.dto.CustomerDTO;

public interface CustomerDAOInterface {

    CustomerDTO getById(int id);

    void add(CustomerDTO customerDTO);

    void update(CustomerDTO customerDTO);

    void delete(CustomerDTO customerDTO);
}