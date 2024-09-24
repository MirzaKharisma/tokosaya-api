package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.dto.request.MerchantRequest;
import com.enigma.TokoSayaApi.model.dto.response.MerchantResponse;
import com.enigma.TokoSayaApi.model.entity.Merchant;

public interface MerchantService {
    Merchant getMerchantByUserId(String userId);
    MerchantResponse createMerchant(MerchantRequest merchantRequest);
}
