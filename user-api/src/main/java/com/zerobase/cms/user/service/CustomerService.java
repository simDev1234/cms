package com.zerobase.cms.user.service;

import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public final CustomerRepository customerRepository;

    public Optional<Customer> findByIdAndEmail(Long id, String email) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findValidCustomer(String email, String password) {

        return customerRepository.findByEmail(email).filter(
                customer -> customer.getPassword().equals(password) && customer.isVerify()
        );

    }

}
