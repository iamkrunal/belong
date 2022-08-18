package com.belong.app.controller;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.service.PhoneNumberService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = PhoneNumberController.class)
class PhoneNumberControllerTest {
    @MockBean
    private PhoneNumberService phoneNumberService;

    @Autowired
    private MockMvc mockMvc;

    /*
    Test list phone numbers
     */

    @Test
    @DisplayName(
            "Should list all phone numbers when making GET request to " +
                    "endpoint - /api/v1/phone-numbers")
    public void shouldListAllPhoneNumbers() throws Exception {

        PhoneNumber number1 = new PhoneNumber("0433222135", null, null);
        PhoneNumber number2 = new PhoneNumber("0433222136", null, null);

        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(asList(number1, number2));

        mockMvc.perform(get("/api/v1/phone-numbers"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].phoneNumber", Matchers.is("0433222135")))
                .andExpect(jsonPath("$[1].phoneNumber", Matchers.is("0433222136")));
    }

    @Test
    @DisplayName(
            "Should return empty list when making GET request to " +
                    "endpoint - /api/v1/phone-numbers")
    public void shouldReturnEmptyList() throws Exception {

        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(asList());

        mockMvc.perform(get("/api/v1/phone-numbers"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

    @Test
    @DisplayName(
            "Should return error response when making GET request to " +
                    "endpoint - /api/v1/phone-numbers")
    public void shouldThrowArrayIndexOutOfBoundsException() throws Exception {

        when(phoneNumberService.getAllPhoneNumbers())
                .thenThrow(new ArrayIndexOutOfBoundsException("Index is not right"));

        mockMvc.perform(get("/api/v1/phone-numbers"))
                .andExpect(status().is(500))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("Index is not right")))
                .andExpect(jsonPath("$.details", Matchers.is("uri=/api/v1/phone-numbers")));
    }

    /*
    Test activate method
     */
    @Test
    @DisplayName(
            "Should activate phone number when making PATCH request to " +
                    "endpoint - /api/v1/phone-numbers/0433222134/activate")
    public void shouldActivatePhoneNumber() throws Exception {
        mockMvc.perform(patch("/api/v1/phone-numbers/0433222134/activate"))
                .andExpect(status().is(204));
    }

    @Test
    @DisplayName(
            "Should return phone number not found error response when making PATCH request to " +
                    "endpoint - /api/v1/phone-numbers/323412/activate")
    public void shouldThrowPhoneNumberNotFoundException() throws Exception {

        doThrow(new EntityNotFoundException("Phone number not found"))
                .when(phoneNumberService).activatePhoneNumber(any());

        mockMvc.perform(patch("/api/v1/phone-numbers/323412/activate"))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("Phone number not found")))
                .andExpect(jsonPath("$.details", Matchers.is("uri=/api/v1/phone-numbers/323412/activate")));
    }

    @Test
    @DisplayName(
            "Should return null pointer error response when making PATCH request to " +
                    "endpoint - /api/v1/phone-numbers/323412/activate")
    public void shouldThrowNullPointerException() throws Exception {

        doThrow(new NullPointerException("Phone number is null"))
                .when(phoneNumberService).activatePhoneNumber(any());

        mockMvc.perform(patch("/api/v1/phone-numbers/null/activate"))
                .andExpect(status().is(500))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.is("Phone number is null")))
                .andExpect(jsonPath("$.details", Matchers.is("uri=/api/v1/phone-numbers/null/activate")));
    }

}