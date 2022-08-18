package com.belong.app.controller;

import com.belong.app.exception.ErrorResponse;
import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get all the phone numbers by a customer id", description = "Get all the phone numbers for a customer", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the phone numbers",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema (schema=  @Schema(implementation = PhoneNumber.class)))}
                ),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = {@Content(mediaType = "application/json",
                            schema=  @Schema(implementation = ErrorResponse.class))}
                ),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing request"),
    })
    @GetMapping("/{customerId}/phone-numbers")
    public ResponseEntity<List<PhoneNumber>>
        getAllPhoneNumbersByCustomerId(@PathVariable Long customerId)
            throws EntityNotFoundException {
        List<PhoneNumber> numbers = customerService.getAllPhoneNumbersByCustomerId(customerId);
        return new ResponseEntity<>(numbers, HttpStatus.OK);
    }
}
