package com.enigma.TokoSayaApi.controller;

import com.enigma.TokoSayaApi.constant.APIUrl;
import com.enigma.TokoSayaApi.model.dto.request.product.ProductCreateRequest;
import com.enigma.TokoSayaApi.model.dto.request.product.ProductUpdateRequest;
import com.enigma.TokoSayaApi.model.dto.response.CommonResponse;
import com.enigma.TokoSayaApi.model.dto.response.ProductResponse;
import com.enigma.TokoSayaApi.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.PRODUCT_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> productResponseList = productService.getAllProducts();

        CommonResponse<List<ProductResponse>> commonResponse = CommonResponse.<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All products retrieved successfully")
                .data(productResponseList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse productResponse = productService.getProductById(id);

        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product retrieved successfully")
                .data(productResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse productResponse = productService.addProduct(request);

        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .data(productResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse productResponse = productService.updateProduct(request);

        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product updated successfully")
                .data(productResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
