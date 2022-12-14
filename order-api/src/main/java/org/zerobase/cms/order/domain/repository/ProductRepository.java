package org.zerobase.cms.order.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.cms.order.domain.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

    @EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findWithProductItemsById(Long id);

}
