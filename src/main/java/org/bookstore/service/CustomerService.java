package org.bookstore.service;

import org.bookstore.DbConnection;
import org.bookstore.dao.CustomerDAO;
import org.bookstore.dto.CustomerDTO;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO(new DbConnection().getConnection());

    public CustomerDTO getById(int id) {
        return customerDAO.getById(id);
    }

    public void add(CustomerDTO customerDTO) {
        customerDAO.add(customerDTO);
    }

    public void update(CustomerDTO customerDTO) {
        customerDAO.update(customerDTO);
    }

    public void delete(CustomerDTO customerDTO) {
        customerDAO.delete(customerDTO);
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}