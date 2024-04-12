package org.bookstore.service;

import org.bookstore.dao.CustomerDAO;
import org.bookstore.dto.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CustomerServiceTest {

    @Test
    public void testGetById() {
        CustomerDAO customerDAOMock = mock(CustomerDAO.class);
        CustomerService customerService = new CustomerService();
        customerService.setCustomerDAO(customerDAOMock);
        int customerId = 1;
        customerService.getById(customerId);
        verify(customerDAOMock).getById(customerId);
    }

    @Test
    public void testAdd() {
        CustomerDAO customerDAOMock = mock(CustomerDAO.class);
        CustomerService customerService = new CustomerService();
        customerService.setCustomerDAO(customerDAOMock);
        CustomerDTO customerDTO = new CustomerDTO();
        customerService.add(customerDTO);
        verify(customerDAOMock).add(customerDTO);
    }

    @Test
    public void testUpdate() {
        CustomerDAO customerDAOMock = mock(CustomerDAO.class);
        CustomerService customerService = new CustomerService();
        customerService.setCustomerDAO(customerDAOMock);
        CustomerDTO customerDTO = new CustomerDTO();
        customerService.update(customerDTO);
        verify(customerDAOMock).update(customerDTO);
    }

    @Test
    public void testDelete() {
        CustomerDAO customerDAOMock = mock(CustomerDAO.class);
        CustomerService customerService = new CustomerService();
        customerService.setCustomerDAO(customerDAOMock);
        CustomerDTO customerDTO = new CustomerDTO();
        customerService.delete(customerDTO);
        verify(customerDAOMock).delete(customerDTO);
    }
}