package org.zerobase.cms.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.zerobase.cms.order.domain.model.Product;
import org.zerobase.cms.order.domain.model.QProduct;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchByName(String name){

        String search = "%" + name + "%";

        QProduct product = QProduct.product;

        return queryFactory.selectFrom(product)
                .where(product.name.like(search))
                .fetch();

    }

}
