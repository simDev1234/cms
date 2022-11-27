package org.zerobase.cms.order.domain.model;

import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.zerobase.cms.order.domain.product.AddProductForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AuditOverride(forClass = BaseEntity.class)
@Audited  // Entity가 변할 때마다, 변화된 내용을 저장
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductItem> productItems = new ArrayList();


    public static Product of(Long sellerId, AddProductForm form) {
        return Product.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .description(form.getDescription())
                .productItems(form.getAddProductItemForms().stream()
                        .map(p -> ProductItem.of(sellerId, p)).collect(Collectors.toList())
                ).build();
    }
}
