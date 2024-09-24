package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.model.dto.request.product.ProductCreateRequest;
import com.enigma.TokoSayaApi.model.dto.request.product.ProductUpdateRequest;
import com.enigma.TokoSayaApi.model.dto.response.ProductResponse;
import com.enigma.TokoSayaApi.model.entity.Merchant;
import com.enigma.TokoSayaApi.model.entity.Product;
import com.enigma.TokoSayaApi.model.entity.User;
import com.enigma.TokoSayaApi.repository.ProductRepository;
import com.enigma.TokoSayaApi.service.MerchantService;
import com.enigma.TokoSayaApi.service.ProductService;
import com.enigma.TokoSayaApi.service.UserService;
import com.enigma.TokoSayaApi.utils.exceptions.AuthenticationException;
import com.enigma.TokoSayaApi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final MerchantService merchantService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::toProductResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(String id) {
        return toProductResponse(findByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductByIdForTrx(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponse addProduct(ProductCreateRequest request) {
        User userContext = userService.getUserByTokenForTsx();
        Merchant merchantContext = merchantService.getMerchantByUserId(userContext.getId());
        Product product = Product.builder()
                .name(request.getName())
                .merchant(merchantContext)
                .description(request.getDescription())
                .price(request.getPrice())
                .createdAt(System.currentTimeMillis())
                .build();
        return toProductResponse(productRepository.saveAndFlush(product));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponse updateProduct(ProductUpdateRequest request) {
        User userContext = userService.getUserByTokenForTsx();
        Merchant merchantContext = merchantService.getMerchantByUserId(userContext.getId());
        Product product = findByIdOrThrow(request.getId());

        if(!Objects.equals(merchantContext.getId(), product.getMerchant().getId())) {
            throw new AuthenticationException("You don't have permission to update this product");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUpdatedAt(System.currentTimeMillis());

        return toProductResponse(productRepository.saveAndFlush(product));
    }

    @Override
    public void deleteProduct(String id) {
        Product product = findByIdOrThrow(id);
        productRepository.delete(product);
    }

    private Product findByIdOrThrow(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .merchantId(product.getMerchant().getId())
                .merchantName(product.getMerchant().getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
