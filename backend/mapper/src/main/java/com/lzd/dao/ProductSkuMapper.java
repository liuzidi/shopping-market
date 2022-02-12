package com.lzd.dao;

import com.lzd.entity.ProductSku;
import com.lzd.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {
    public List<ProductSku> selectLowerestPriceByProductId(String productId);
}