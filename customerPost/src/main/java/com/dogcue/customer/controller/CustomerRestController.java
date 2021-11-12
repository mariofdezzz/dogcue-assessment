package com.dogcue.customer.controller;

import com.dogcue.customer.business.BusinessCustomer;
import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import com.dogcue.customer.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mario
 */
@RestController
@RequestMapping("/customer")
@Api(tags = "Customer Create/Update API")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BusinessCustomer businessCustomer;

    @ApiOperation(value = "Create customer", notes = "Return 422 if required fields are missing. \nEmail and phone provided must not be used. Return 409 if email or phone is already registered.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created customer successfully"),
        @ApiResponse(code = 422, message = "Required fields are missing"),
        @ApiResponse(code = 409, message = "Email or phone is already registered")
    })
    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer customer) throws BusinessCustomerException {
        Customer save = businessCustomer.save(customer);

        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update customer", notes = "Return 422 if required fields are missing. \nEmail and phone provided must not be used. Return 409 if email or phone is already registered.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Updated customer successfully"),
        @ApiResponse(code = 422, message = "Required fields are missing"),
        @ApiResponse(code = 409, message = "Email or phone is already registered")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> put(@PathVariable long id, @RequestBody Customer customer) throws BusinessCustomerException {
        Customer saved = businessCustomer.update(id, customer);

        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
