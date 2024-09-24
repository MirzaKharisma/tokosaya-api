package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.dto.request.product.ProductCreateRequest;
import com.enigma.TokoSayaApi.model.dto.request.product.ProductUpdateRequest;
import com.enigma.TokoSayaApi.model.dto.response.ProductResponse;
import com.enigma.TokoSayaApi.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(String id);
    Product getProductByIdForTrx(String id);
    ProductResponse addProduct(ProductCreateRequest request);
    ProductResponse updateProduct(ProductUpdateRequest product);
    void deleteProduct(String id);
}
