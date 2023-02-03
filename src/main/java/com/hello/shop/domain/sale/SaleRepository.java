package com.hello.shop.domain.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findAll();
    List<Sale> findSalesById(int id);
    Sale findBySellerId(int id);
}
