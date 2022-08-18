package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll();
    }

    @Override
    public void activatePhoneNumber(String phoneNumber) throws EntityNotFoundException {
        PhoneNumber number = phoneNumberRepository.findById(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Phone number not found"));

        number.setActivated(true);
        number.setDateActivated(new Date());
        phoneNumberRepository.save(number);
    }
}
