package com.dogcue.customer.create;

import com.dogcue.customer.CustomerPostApplication;
import com.dogcue.customer.controller.CustomerRestController;
import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import com.dogcue.customer.repository.CustomerRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Mario
 */
@ContextConfiguration(classes = CustomerPostApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerApiCreateTest {

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
        assertThat(repository).isNotNull();
    }

    @Test
    public void createCustomer() {
        try {
            ResponseEntity<Customer> response = controller.post(customerA);

            assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createExistingCustomer() {
        try {
            customerA.setId(1);
            customerB.setId(1);
            
            ResponseEntity<Customer> responseA = controller.post(customerA);
            ResponseEntity<Customer> responseB = controller.post(customerB);

            assertAll("Customer mail exists response as expected",
                    () -> assertTrue(responseA.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(responseB.getStatusCode() == HttpStatus.CREATED)
            );
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createCustomerJustRequiredFields() {
        try {
            customerA = new Customer();
            customerA.setName("Pepe");
            customerA.setEmail("p@mail.com");

            ResponseEntity<Customer> response = controller.post(customerA);

            assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createCustomerWithoutName() {
        try {
            customerA = new Customer();
            customerA.setEmail("p@mail.com");

            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.post(customerA),
                    "Expected post() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer mail exists response as expected",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY),
                    () -> assertTrue(thrown.getCode() == 1001)
            );
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createCustomerWithoutEmail() {
        try {
            customerA = new Customer();
            customerA.setName("Pepe");

            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.post(customerA),
                    "Expected post() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer mail exists response as expected",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY),
                    () -> assertTrue(thrown.getCode() == 1001)
            );
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createExistingEmail() {
        ResponseEntity<Customer> responseA;
        ResponseEntity<Customer> responseB;
        try {
            customerB.setEmail(customerA.getEmail());

            responseA = controller.post(customerA);

            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.post(customerB),
                    "Expected post() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer mail exists response as expected",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.CONFLICT),
                    () -> assertTrue(thrown.getCode() == 1011)
            );
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void createExistingPhone() {
        ResponseEntity<Customer> responseA;
        ResponseEntity<Customer> responseB;
        try {
            customerB.setPhone(customerA.getPhone());

            responseA = controller.post(customerA);

            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.post(customerB),
                    "Expected post() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer phone exists response as expected",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.CONFLICT),
                    () -> assertTrue(thrown.getCode() == 1021)
            );
        } catch (Exception e) {
            Logger.getLogger(CustomerApiCreateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

}
