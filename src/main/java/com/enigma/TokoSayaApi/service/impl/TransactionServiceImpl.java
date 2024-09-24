package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.model.dto.request.TransactionRequest;
import com.enigma.TokoSayaApi.model.dto.response.TransactionDetailResponse;
import com.enigma.TokoSayaApi.model.dto.response.TransactionResponse;
import com.enigma.TokoSayaApi.model.entity.*;
import com.enigma.TokoSayaApi.repository.TransactionDetailRepository;
import com.enigma.TokoSayaApi.repository.TransactionRepository;
import com.enigma.TokoSayaApi.service.CustomerService;
import com.enigma.TokoSayaApi.service.ProductService;
import com.enigma.TokoSayaApi.service.TransactionService;
import com.enigma.TokoSayaApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final UserService userService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        User userContext = userService.getUserByTokenForTsx();
        Customer customerContext = customerService.getCustomerByUserId(userContext.getId());
        Transaction transaction = Transaction.builder()
                .customer(customerContext)
                .createdAt(System.currentTimeMillis())
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetail = transactionRequest.getTransactionDetailRequests().stream().map(detailRequest -> {
           Product product = productService.getProductByIdForTrx(detailRequest.getProductId());

            TransactionDetail trxDetail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .quantity(detailRequest.getQuantity())
                    .build();

            totalPayment.updateAndGet(v -> v + detailRequest.getQuantity() * product.getPrice());
            transactionDetailRepository.save(trxDetail);
            return trxDetail;
        }).collect(Collectors.toList());

        transaction.setTransactionDetails(transactionDetail);
        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(savedTransaction, totalPayment.get());
    }

    private TransactionResponse toTransactionResponse(Transaction transaction, Long totalPayment) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .customerName(transaction.getCustomer().getName())
                .transactionDetailsResponses(transaction.getTransactionDetails().stream().map(this::toTransactionDetailResponse).toList())
                .totalPayment(totalPayment)
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    private TransactionDetailResponse toTransactionDetailResponse(TransactionDetail transactionDetail) {
        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .productId(transactionDetail.getProduct().getId())
                .productName(transactionDetail.getProduct().getName())
                .productDescription(transactionDetail.getProduct().getDescription())
                .productPrice(transactionDetail.getProduct().getPrice())
                .quantity(transactionDetail.getQuantity())
                .build();
    }
}
