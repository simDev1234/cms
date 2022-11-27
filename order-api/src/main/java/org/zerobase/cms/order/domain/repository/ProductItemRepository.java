package org.zerobase.cms.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.cms.order.domain.model.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
