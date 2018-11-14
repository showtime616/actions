package com.discordia.actions.converter;

import org.springframework.stereotype.Component;

import com.discordia.actions.data.ProductData;
import com.discordia.actions.model.Product;

@Component
public class ProductDataConverter {
  public ProductData convert(final Product product) {
    final ProductData productData = new ProductData();
    productData.setId(product.getId());
    productData.setName(product.getName());
    productData.setQuantity(product.getQuantity());

    return productData;
  }
}
