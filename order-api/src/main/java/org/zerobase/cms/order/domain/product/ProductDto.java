package org.zerobase.cms.order.domain.product;

import lombok.*;
import org.zerobase.cms.order.domain.model.Product;

import java.util.List;
import java.util.stream.Collectors;
import org.zerobase.cms.order.domain.model.ProductItem;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private List<ProductItemDto> items;

    public static ProductDto from(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .items(product.getProductItems().stream()
                        .map(ProductItemDto::from).collect(Collectors.toList()))
                .build();
    }

    public static ProductDto withoutItemsFrom(Product product) {
        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .build();
    }

}
