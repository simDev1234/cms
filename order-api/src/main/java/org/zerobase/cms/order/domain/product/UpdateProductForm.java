package org.zerobase.cms.order.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateProductForm {

    private Long productId;
    private String name;
    private String description;
    private List<UpdateProductItemForm> items;

}
