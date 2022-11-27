package org.zerobase.cms.order.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.product.AddProductForm;
import org.zerobase.cms.order.domain.product.AddProductItemForm;
import org.zerobase.cms.order.domain.product.UpdateProductForm;
import org.zerobase.cms.order.domain.product.UpdateProductItemForm;
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

        AddProductForm form = makeAddProductForm("나이키 에어포스", "신발", 3);

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

    @Test
    void updateProduct(){

        // given
        Long sellerId = 1L;

        AddProductForm addProductForm = makeAddProductForm("나이키 에어포스", "신발", 3);
        Product p = productService.addProduct(sellerId, addProductForm);
        p.setId(1L);

        for (int i = 0; i < 3; i++) {
            p.getProductItems().get(i).setId((long) i);
        }

        UpdateProductForm form = makeUpdateProductForm(1L, "나이키 에어포스2", "신발1", 3);

        // when
        p = productService.updateProduct(sellerId, form);

        // then
        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSellerId(), 1L);
        Assertions.assertEquals(result.getName(), "나이키 에어포스2");
        Assertions.assertEquals(result.getDescription(), "신발1");
        Assertions.assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스20");

    }

    private static AddProductForm makeAddProductForm(String name, String description, int itemCount) {
        List<AddProductItemForm> addProductItemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            addProductItemForms.add(makeAddProductItemForm(null, name + i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .addProductItemForms(addProductItemForms)
                .build();
    }

    private static AddProductItemForm makeAddProductItemForm(Long productId, String name){
        return AddProductItemForm.builder()
                .productId(productId)
                .name(name)
                .price(10000)
                .count(1)
                .build();
    }

    private static UpdateProductForm makeUpdateProductForm(Long productId, String name, String description, int itemCount) {
        List<UpdateProductItemForm> updateProductItemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            updateProductItemForms.add(makeUpdateProductItemForm((long) i, name + i));
        }
        return UpdateProductForm.builder()
                .productId(productId)
                .name(name)
                .description(description)
                .items(updateProductItemForms)
                .build();
    }

    private static UpdateProductItemForm makeUpdateProductItemForm(Long id, String name){
        return UpdateProductItemForm.builder()
                .id(id)
                .name(name)
                .price(10000)
                .count(1)
                .build();
    }
}