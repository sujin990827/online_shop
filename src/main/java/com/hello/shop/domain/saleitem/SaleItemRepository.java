package com.hello.shop.domain.saleitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
    List<SaleItem> findSaleItemsBySellerId(int sellerId);
    List<SaleItem> findAll();
}
