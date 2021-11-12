package com.dogcue.customer.business;

import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import com.dogcue.customer.repository.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mario
 *
 * BusinessCustomerException codes: code - error - status
 *
 *   - 1XXX: Business customer exception 
 *     - 100X: Field validation exception 
 *       - 1001: Required fields are missing    (422)
 *
 *     - 101X: Email validation exception 
 *       - 1011: Email is already in use        (409)
 *
 *     - 102X: Phone validation exception 
 *       - 1021: Phone is already in use        (409)
 *
 *     - 103X: Update exception 
 *       - 1031: Customer not found             (404)
 */
@Service
public class BusinessCustomer {

    @Autowired
    CustomerRepository customerRepository;

    public Customer save(Customer customer) throws BusinessCustomerException {
        customer.setId(0);

        validRequiredFields(customer);
        validEmail(customer);
        validPhoneNumber(customer);

        return customerRepository.save(customer);
    }

    public Customer update(long id, Customer customer) throws BusinessCustomerException {
        customer.setId(id);

        validId(customer);
        validRequiredFields(customer);
        validEmail(customer);
        validPhoneNumber(customer);

        return customerRepository.save(customer);
    }

    private void validRequiredFields(Customer customer) throws BusinessCustomerException {
        if (customer.getName() == null || customer.getEmail() == null) {
            throw new BusinessCustomerException(1001, "Create error. Required fields are missing.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void validEmail(Customer customer) throws BusinessCustomerException {
        Customer foundCustomer = customerRepository.findByEmail(customer.getEmail());

        if (foundCustomer != null && foundCustomer.getId() != customer.getId()) {
            throw new BusinessCustomerException(1011, "Create error. Email is already in use.", HttpStatus.CONFLICT);
        }
    }

    private void validPhoneNumber(Customer customer) throws BusinessCustomerException {
        Customer foundCustomer = customerRepository.findByPhone(customer.getPhone());

        if (foundCustomer != null && foundCustomer.getId() != customer.getId()) {
            throw new BusinessCustomerException(1021, "Create error. Phone is already in use.", HttpStatus.CONFLICT);
        }
    }

    private void validId(Customer customer) throws BusinessCustomerException {
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());

        if (foundCustomer == null || foundCustomer.isEmpty()) {
            throw new BusinessCustomerException(1031, "Update error. Customer does not exists.", HttpStatus.NOT_FOUND);
        }
    }
}
