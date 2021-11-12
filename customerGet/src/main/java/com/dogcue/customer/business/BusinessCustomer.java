package com.dogcue.customer.business;

import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import com.dogcue.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario
 *
 * BusinessCustomerException codes:     code - error - status
 * 
 * - 1XXX: Business customer exception 
 *   - 11XX: Read error
 *     - 1101: Id not found     (404)
 */
@Service
public class BusinessCustomer {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> findAll() throws BusinessCustomerException {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList == null || customerList.isEmpty()) {
            throw new BusinessCustomerException(HttpStatus.NO_CONTENT);
        }

        return customerList;
    }

    public Customer findById(long id) throws BusinessCustomerException {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer == null || customer.isEmpty()) {
            throw new BusinessCustomerException(1101, "Id not found", HttpStatus.NOT_FOUND);
        }

        return customer.get();
    }
}
