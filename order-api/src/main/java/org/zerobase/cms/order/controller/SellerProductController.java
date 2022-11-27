package org.zerobase.cms.order.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerobase.cms.order.domain.product.*;
import org.zerobase.cms.order.service.ProductItemService;
import org.zerobase.cms.order.service.ProductService;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;

    private final ProductItemService productItemService;

    private final JwtAuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody AddProductForm form) {

        return ResponseEntity.ok(ProductDto.from(productService.addProduct(provider.getUserVo(token).getId(), form)));

    }

    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                     @RequestBody AddProductItemForm form) {

        return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(provider.getUserVo(token).getId(), form)));

    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                    @RequestBody UpdateProductForm form) {

        return ResponseEntity.ok(ProductDto.from(productService.updateProduct(provider.getUserVo(token).getId(), form)));

    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                            @RequestBody UpdateProductItemForm form) {
        return ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(provider.getUserVo(token).getId(), form)));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                    @PathVariable Long productId) {

        productService.deleteProduct(provider.getUserVo(token).getId(), productId);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<ProductItemDto> deleteProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                            @PathVariable Long productId, @RequestParam Long id) {

        productItemService.deleteProductItem(provider.getUserVo(token).getId(), productId, id);

        return ResponseEntity.ok().build();
    }

}
