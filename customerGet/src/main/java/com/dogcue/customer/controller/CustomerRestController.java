package com.dogcue.customer.controller;

import com.dogcue.customer.business.BusinessCustomer;
import com.dogcue.customer.entities.Customer;
import com.dogcue.customer.exception.BusinessCustomerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mario
 */
@RestController
@RequestMapping("/customer")
@Api(tags="Customer Retrieve API")
public class CustomerRestController {

    @Autowired
    BusinessCustomer businessCustomer;

    @ApiOperation(value = "Return specific customer", notes = "Return 404 if could not be found")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Could not found customer")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable long id) throws BusinessCustomerException {
        Customer customer = businessCustomer.findById(id);

        return ResponseEntity.ok(customer);

    }

    @ApiOperation(value = "Return all customers", notes = "Return 204 if customer list is empty")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "There are no customers")
    })
    @GetMapping()
    public ResponseEntity<List<Customer>> list() throws BusinessCustomerException {
        List<Customer> customerList = businessCustomer.findAll();

        return ResponseEntity.ok(customerList);
    }
}
