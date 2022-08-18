package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {
    public List<PhoneNumber> getAllPhoneNumbers();
    public void activatePhoneNumber(String phoneNumber) throws EntityNotFoundException;
}
