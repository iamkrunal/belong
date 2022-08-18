package com.belong.app.controller;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName(
            "Should list all phone numbers when making GET request to " +
                    "endpoint - /api/v1/customers/{customerId}/phone-numbers")
    public void shouldListAllPhoneNumbers() throws Exception {

        PhoneNumber number1 = new PhoneNumber("0433222135", null, null);
        PhoneNumber number2 = new PhoneNumber("0433222136", null, null);

        when(customerService.getAllPhoneNumbersByCustomerId(any())).thenReturn(asList(number1, number2));

        mockMvc.perform(get("/api/v1/customers/1/phone-numbers"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].phoneNumber", Matchers.is("0433222135")))
                .andExpect(jsonPath("$[1].phoneNumber", Matchers.is("0433222136")));
    }

    @Test
    @DisplayName(
            "Should return empty list when making GET request to " +
                    "endpoint - /api/v1/customers/{customerId}/phone-numbers")
    public void shouldReturnEmptyList() throws Exception {

        when(customerService.getAllPhoneNumbersByCustomerId(any())).thenReturn(asList());

        mockMvc.perform(get("/api/v1/customers/1/phone-numbers"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName(
            "Should return error response when making GET request to " +
                    "endpoint - /api/v1/customers/{customerId}/phone-numbers")
    public void shouldThrowCustomerNotFoundException() throws Exception {

        when(customerService.getAllPhoneNumbersByCustomerId(any()))
                .thenThrow(new EntityNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/v1/customers/1/phone-numbers"))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("Customer not found")))
                .andExpect(jsonPath("$.details", Matchers.is("uri=/api/v1/customers/1/phone-numbers")));
    }

    @Test
    @DisplayName(
            "Should return error response when making GET request to " +
                    "endpoint - /api/v1/customers/{customerId}/phone-numbers")
    public void shouldThrowNullPointerException() throws Exception {

        when(customerService.getAllPhoneNumbersByCustomerId(any()))
                .thenThrow(new NullPointerException("Customer ID is null"));

        mockMvc.perform(get("/api/v1/customers/1/phone-numbers"))
                .andExpect(status().is(500))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("Customer ID is null")))
                .andExpect(jsonPath("$.details", Matchers.is("uri=/api/v1/customers/1/phone-numbers")));
    }

    @Test
    @DisplayName(
            "Should return error response when making GET request to " +
                    "unknown endpoint - /api/v1/customers/{customerId}/something-else")
    public void shouldThrowResourceNotFoundException() throws Exception {

        mockMvc.perform(get("/api/v1/customers/1/something-else"))
                .andExpect(status().is(404));
    }
}