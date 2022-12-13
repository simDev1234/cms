package org.zerobase.cms.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.model.ProductItem;
import org.zerobase.cms.order.domain.product.AddProductForm;
import org.zerobase.cms.order.domain.product.UpdateProductForm;
import org.zerobase.cms.order.domain.product.UpdateProductItemForm;
import org.zerobase.cms.order.domain.repository.ProductRepository;
import org.zerobase.cms.order.exception.CustomException;
import org.zerobase.cms.order.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form) {
        return productRepository.save(Product.of(sellerId, form));
    }

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form) {

        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for (UpdateProductItemForm itemForm : form.getItems()) {

            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getId()))
                    .findFirst().orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));

            item.setName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setCount(itemForm.getCount());

        }

        return product;

    }

    @Transactional
    public void deleteProduct(Long sellerId, Long productId){

        Product p = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

        productRepository.delete(p);

    }



}
