package org.zerobase.cms.order.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.product.AddProductForm;
import org.zerobase.cms.order.domain.product.AddProductItemForm;
import org.zerobase.cms.order.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addProduct() {

        // given
        Long sellerId = 1L;

        AddProductForm form = makeProductForm("나이키 에어포스", "신발", 3);

        // when
        Product p = productService.addProduct(sellerId, form);

        // then
        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSellerId(), 1L);
        Assertions.assertEquals(result.getName(), "나이키 에어포스");
        Assertions.assertEquals(result.getDescription(), "신발");
        Assertions.assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스0");
        Assertions.assertEquals(result.getProductItems().get(0).getPrice(), 10000);

    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount) {
        List<AddProductItemForm> addProductItemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            addProductItemForms.add(makeProductItemForm(null, name + i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .addProductItemForms(addProductItemForms)
                .build();
    }

    private static AddProductItemForm makeProductItemForm(Long productId, String name){
        return AddProductItemForm.builder()
                .productId(productId)
                .name(name)
                .price(10000)
                .count(1)
                .build();
    }
}