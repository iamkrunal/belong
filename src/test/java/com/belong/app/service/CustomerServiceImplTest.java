package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.Customer;
import com.belong.app.model.PhoneNumber;
import com.belong.app.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Should return phone numbers by customer id")
    public void shouldFindPhoneNumbersByCustomerId() throws EntityNotFoundException {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Spider");
        customer.setLastName("Man");
        PhoneNumber number1 = new PhoneNumber("0433222135", null, null);
        PhoneNumber number2 = new PhoneNumber("0433222136", null, null);
        List<PhoneNumber> expectedResponse = asList(number1, number2);
        customer.setPhoneNumbers(expectedResponse);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        List<PhoneNumber> actualResponse = customerService.getAllPhoneNumbersByCustomerId(1L);

        Assertions.assertThat(actualResponse.size()).isEqualTo(expectedResponse.size());
        Assertions.assertThat(actualResponse.get(0)).isEqualTo(expectedResponse.get(0));
        Assertions.assertThat(actualResponse.get(1)).isEqualTo(expectedResponse.get(1));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException")
    public void shouldThrowEntityNotFoundException() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getAllPhoneNumbersByCustomerId(1L));
    }
}