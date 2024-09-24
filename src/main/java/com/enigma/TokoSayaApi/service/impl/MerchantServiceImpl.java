package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.model.dto.request.MerchantRequest;
import com.enigma.TokoSayaApi.model.dto.response.MerchantResponse;
import com.enigma.TokoSayaApi.model.entity.Merchant;
import com.enigma.TokoSayaApi.repository.MerchantRepository;
import com.enigma.TokoSayaApi.service.MerchantService;
import com.enigma.TokoSayaApi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional(readOnly = true)
    public Merchant getMerchantByUserId(String userId) {
        return merchantRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));
    }

    @Override
    public MerchantResponse createMerchant(MerchantRequest merchantRequest) {
        Merchant merchant = Merchant.builder()
                .name(merchantRequest.getName())
                .address(merchantRequest.getAddress())
                .phoneNumber(merchantRequest.getPhoneNumber())
                .user(merchantRequest.getUser())
                .createdAt(System.currentTimeMillis())
                .build();
        return toResponse(merchantRepository.saveAndFlush(merchant));
    }

    private MerchantResponse toResponse(Merchant merchant) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .address(merchant.getAddress())
                .phoneNumber(merchant.getPhoneNumber())
                .createdAt(System.currentTimeMillis())
                .build();
    }
}
