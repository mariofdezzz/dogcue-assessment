package com.dogcue.customer.update;

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
public class CustomerApiUpdateTest {

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
    public void updateExistingCustomer() {
        try {
            ResponseEntity<Customer> createResponse = controller.post(customerA);

            Customer savedCustomer = createResponse.getBody();

            ResponseEntity<Customer> updateResponse = controller.put(savedCustomer.getId(), customerB);
            Customer updatedCustomer = updateResponse.getBody();

            assertAll("Customer creation and update",
                    () -> assertTrue(createResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(updateResponse.getStatusCode() == HttpStatus.OK),
                    () -> assertTrue(updatedCustomer.getId() == savedCustomer.getId()),
                    () -> assertTrue(updatedCustomer.getName().equals(customerB.getName())),
                    () -> assertTrue(updatedCustomer.getEmail().equals(customerB.getEmail())),
                    () -> assertTrue(updatedCustomer.getPhone().equals(customerB.getPhone())),
                    () -> assertTrue(updatedCustomer.getAddress().equals(customerB.getAddress()))
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerApiUpdateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void updateCustomerWithoutChanges() {
        try {
            ResponseEntity<Customer> createResponse = controller.post(customerA);

            Customer savedCustomer = createResponse.getBody();

            ResponseEntity<Customer> updateResponse = controller.put(savedCustomer.getId(), customerA);
            Customer updatedCustomer = updateResponse.getBody();

            assertAll("Customer creation and update",
                    () -> assertTrue(createResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(updateResponse.getStatusCode() == HttpStatus.OK),
                    () -> assertTrue(updatedCustomer.getId() == savedCustomer.getId()),
                    () -> assertTrue(updatedCustomer.getName().equals(customerA.getName())),
                    () -> assertTrue(updatedCustomer.getEmail().equals(customerA.getEmail())),
                    () -> assertTrue(updatedCustomer.getPhone().equals(customerA.getPhone())),
                    () -> assertTrue(updatedCustomer.getAddress().equals(customerA.getAddress()))
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerApiUpdateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void updateNonExistingCustomer() {
        try {
            
            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.put(1, customerB),
                    "Expected put() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer creation and update",
                    () -> assertTrue(thrown.getStatus() == HttpStatus.NOT_FOUND)
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerApiUpdateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void updateCustomerExistingEmail() {
        try {
            ResponseEntity<Customer> createAResponse = controller.post(customerA);
            ResponseEntity<Customer> createBResponse = controller.post(customerB);
            
            customerB.setEmail(customerA.getEmail());
            
            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.put(createBResponse.getBody().getId(), customerB),
                    "Expected put() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer creation and update",
                    () -> assertTrue(createAResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(createBResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(thrown.getStatus() == HttpStatus.CONFLICT)
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerApiUpdateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }

    @Test
    public void updateCustomerExistingPhone() {
        try {
            ResponseEntity<Customer> createAResponse = controller.post(customerA);
            ResponseEntity<Customer> createBResponse = controller.post(customerB);
            
            customerB.setPhone(customerA.getPhone());
            
            BusinessCustomerException thrown = assertThrows(BusinessCustomerException.class,
                    () -> controller.put(createBResponse.getBody().getId(), customerB),
                    "Expected put() to throw BusinessCustomerException, but it didn't");

            assertAll("Customer creation and update",
                    () -> assertTrue(createAResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(createBResponse.getStatusCode() == HttpStatus.CREATED),
                    () -> assertTrue(thrown.getStatus() == HttpStatus.CONFLICT)
            );

        } catch (Exception e) {
            Logger.getLogger(CustomerApiUpdateTest.class.getName()).log(Level.SEVERE, null, e);

            fail(e.getMessage());
        }
    }
    
}
