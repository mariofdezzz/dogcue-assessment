package com.dogcue.customer;

import com.dogcue.customer.CustomerGetApplication;
import com.dogcue.customer.controller.CustomerRestController;
import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import com.dogcue.customer.repository.CustomerRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Mario
 */
@ContextConfiguration(classes = CustomerGetApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerGetApiTest {

    Customer customerA;
    Customer customerB;

    @Autowired
    private CustomerRestController controller;

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    public void init() {
        customerA = new Customer();
        customerA.setName("Pepe");
        customerA.setAddress("C/Maria Dolores");
        customerA.setPhone("123456789");
        customerA.setEmail("p@mail.com");

        customerB = new Customer();
        customerB.setName("Juan");
        customerB.setAddress("C/Rocha Perez");
        customerB.setPhone("111222333");
        customerB.setEmail("j@mail.com");
    }

    @AfterEach
    public void purgeDB() {
        repository.deleteAll();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getCustomer() {
        try {
            // Create
            Customer saved = repository.save(customerA);

            // Get
            ResponseEntity<Customer> response = controller.get(saved.getId());
            Customer getted = response.getBody();

            assertAll("Customer created and getted are equal",
                    () -> assertTrue(saved.getId() == getted.getId()),
                    () -> assertTrue(saved.getPhone() == getted.getPhone()),
                    () -> assertTrue(saved.getEmail() == getted.getEmail()),
                    () -> assertTrue(saved.getAddress() == getted.getAddress())
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerGetApiTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void getNonExistingCustomer() {
        try {
            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.get(1),
                    "Expected get() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer not found response as expected",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.NOT_FOUND),
                    () -> assertTrue(thrown.getCode() == 1101)
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerGetApiTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    // = Optional list method tests =
    @Test
    public void listCustomers() {
        try {
            // Create
            Customer savedCustomerA = repository.save(customerA);
            Customer savedCustomerB = repository.save(customerB);

            // Get
            ResponseEntity<List<Customer>> response = controller.list();
            List<Customer> customersList = response.getBody();

            assertAll("Customer not found response as expected",
                    () -> assertTrue(response.getStatusCode() == HttpStatus.OK),
                    () -> assertTrue(customersList.size() == 2),
                    () -> customersList.forEach(
                            (customer) -> assertTrue(
                                    customer.getId() == customerA.getId()
                                    || customer.getId() == customerB.getId()
                            )
                    ),
                    () -> assertTrue(customersList.size() == 2)
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerGetApiTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void listEmptyCustomersList() {
        try {
            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.list(),
                    "Expected list() to throw BusinessCustomerException, but it didn't");

            assertTrue(thrown.getStatus() == HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            Logger.getLogger(CustomerGetApiTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }
}
