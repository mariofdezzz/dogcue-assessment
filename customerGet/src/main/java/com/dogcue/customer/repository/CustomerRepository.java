package com.dogcue.customer.repository;

import com.dogcue.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Mario
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT customer FROM Customer customer WHERE customer.email = ?1")
    public Customer findByEmail(String code);
    
    @Query("SELECT customer FROM Customer customer WHERE customer.phone = ?1")
    public Customer findByPhone(String code);
}
