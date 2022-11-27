package org.zerobase.cms.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.model.ProductItem;
import org.zerobase.cms.order.domain.product.AddProductItemForm;
import org.zerobase.cms.order.domain.product.UpdateProductForm;
import org.zerobase.cms.order.domain.product.UpdateProductItemForm;
import org.zerobase.cms.order.domain.repository.ProductItemRepository;
import org.zerobase.cms.order.domain.repository.ProductRepository;
import org.zerobase.cms.order.exception.CustomException;
import org.zerobase.cms.order.exception.ErrorCode;

import static org.zerobase.cms.order.exception.ErrorCode.NOT_FOUND_PRODUCT;
import static org.zerobase.cms.order.exception.ErrorCode.SAME_ITEM_NAME;

@Service
@RequiredArgsConstructor
public class ProductItemService {

    private final ProductItemRepository productItemRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form) {

        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        if (product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))){
            throw new CustomException(SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId, form);
        product.getProductItems().add(productItem);
        return product;

    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form) {

        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));

        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());
        return productItem;

    }

    @Transactional
    public void deleteProductItem(Long sellerId, Long productId, Long id) {

        ProductItem productItem = productItemRepository.findById(id)
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));

        productItemRepository.delete(productItem);

    }

}
