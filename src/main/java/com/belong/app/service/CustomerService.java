package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;

import java.util.List;

public interface CustomerService {
    public List<PhoneNumber> getAllPhoneNumbersByCustomerId(Long customerId) throws EntityNotFoundException;
}
