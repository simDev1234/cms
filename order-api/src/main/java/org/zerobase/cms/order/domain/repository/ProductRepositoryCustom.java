package org.zerobase.cms.order.domain.repository;

import org.zerobase.cms.order.domain.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> searchByName(String name);

}
