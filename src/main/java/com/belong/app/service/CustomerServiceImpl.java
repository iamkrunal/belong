package com.belong.app.service;

import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<PhoneNumber> getAllPhoneNumbersByCustomerId(Long customerId) throws EntityNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"))
                .getPhoneNumbers();
    }
}
