package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.dto.request.CustomerRequest;
import com.enigma.TokoSayaApi.model.dto.response.CustomerResponse;
import com.enigma.TokoSayaApi.model.entity.Customer;

public interface CustomerService {
    Customer getCustomerByUserId(String userId);
    CustomerResponse createCustomer(CustomerRequest customerRequest);
}
