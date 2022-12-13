package org.zerobase.cms.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.repository.ProductRepository;
import org.zerobase.cms.order.exception.CustomException;
import java.util.List;
import static org.zerobase.cms.order.exception.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;

    /**
     * 상품명을 통해 유사 상품 목록 조회
     */
    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }

    /**
     * 상품 ID를 통해 특정 상품 조회
     */
    public Product getByProductId(Long productId) {
        return productRepository.findWithProductItemsById(productId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
    }

    /**
     * 상품 ID들을 통해 상품 목록을 조회
     */
    public List<Product> getListByProductIds(List<Long> productIds){

        return productRepository.findAllById(productIds);

    }

}
