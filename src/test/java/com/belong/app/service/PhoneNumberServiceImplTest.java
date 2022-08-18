package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.repository.PhoneNumberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceImplTest {
    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @InjectMocks
    private PhoneNumberServiceImpl phoneNumberService;

    @Captor
    private ArgumentCaptor<PhoneNumber> numberArgumentCaptor;

    /*
    Test getAllPhoneNumbers function
     */
    @Test
    @DisplayName("Should return all phone numbers")
    public void shouldReturnAllPhoneNumbers() {

        PhoneNumber number1 = new PhoneNumber("0433222135", null, null);
        PhoneNumber number2 = new PhoneNumber("0433222136", null, null);
        List<PhoneNumber> expectedResponse = asList(number1, number2);

        when(phoneNumberRepository.findAll()).thenReturn(expectedResponse);

        List<PhoneNumber> actualResponse = phoneNumberService.getAllPhoneNumbers();

        assertThat(actualResponse.size()).isEqualTo(expectedResponse.size());
        assertThat(actualResponse.get(0)).isEqualTo(expectedResponse.get(0));
        assertThat(actualResponse.get(1)).isEqualTo(expectedResponse.get(1));
    }

    @Test
    @DisplayName("Should return empty list")
    public void shouldEmptyPhoneNumberList() {

        List<PhoneNumber> expectedResponse = asList();

        when(phoneNumberRepository.findAll()).thenReturn(expectedResponse);

        List<PhoneNumber> actualResponse = phoneNumberService.getAllPhoneNumbers();

        assertThat(actualResponse.size()).isEqualTo(expectedResponse.size());
        assertThat(actualResponse.size()).isEqualTo(0);
    }

    /*
    Test activatePhoneNumber
     */
    @Test
    @DisplayName("Activate phone number")
    public void shouldActivatePhoneNumber() throws EntityNotFoundException {

        PhoneNumber numberToSave = new PhoneNumber("23452345", null, null);
        PhoneNumber numberSaved = new PhoneNumber();
        numberSaved.setPhoneNumber("23452345");
        numberSaved.setActivated(true);
        numberSaved.setDateActivated(new Date());

        doReturn(numberSaved).when(phoneNumberRepository).save(numberToSave);
        when(phoneNumberRepository.findById(anyString())).thenReturn(Optional.of(numberToSave));

        phoneNumberService.activatePhoneNumber("23452345");

        verify(phoneNumberRepository, times(1)).save(numberArgumentCaptor.capture());

        assertThat(numberArgumentCaptor.getValue().getPhoneNumber()).isEqualTo("23452345");
        assertThat(numberArgumentCaptor.getValue().getActivated()).isEqualTo(true);
    }

    @Test
    @DisplayName("Should throw phone number not found when activate the number")
    public void shouldThrowEntityNotFoundException() throws EntityNotFoundException {
        when(phoneNumberRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> phoneNumberService.activatePhoneNumber("3452345"));
    }

}