package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.model.dto.request.CustomerRequest;
import com.enigma.TokoSayaApi.model.dto.response.CustomerResponse;
import com.enigma.TokoSayaApi.model.entity.Customer;
import com.enigma.TokoSayaApi.repository.CustomerRepository;
import com.enigma.TokoSayaApi.service.CustomerService;
import com.enigma.TokoSayaApi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerByUserId(String userId) {
        return customerRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .phoneNumber(String.valueOf(customerRequest.getPhoneNumber()))
                .user(customerRequest.getUser())
                .point(0L)
                .createdAt(System.currentTimeMillis())
                .build();
        return toResponse(customerRepository.saveAndFlush(customer));
    }

    private CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .point(customer.getPoint())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
