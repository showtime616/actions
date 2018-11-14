package com.discordia.actions.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discordia.actions.converter.ProductDataConverter;
import com.discordia.actions.data.ProductData;
import com.discordia.actions.repository.ProductRepository;
import com.discordia.actions.model.Product;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductDataConverter productDataConverter;

  @Transactional
  public Optional<Product> takeGift(final Long actionId, final Long productId) {
    return Optional.ofNullable(actionId).flatMap(a ->
            Optional.ofNullable(productId)
                    .flatMap(p ->  productRepository.findByIdAndActions_Id(p, a))
                    .map(Product::decrementQuantity)
                    .map(product -> (Product) product));
  }

  public Set<ProductData> getProducts(final Long actionId, final String searchText) {
    if (actionId == null) {
      return Collections.emptySet();
    }

    final List<Product> products = StringUtils.isNotBlank(searchText) ?
            productRepository.findByActions_IdAndNameContainingIgnoreCaseAndQuantityGreaterThan(actionId, searchText.trim(), 0L) :
            productRepository.findByActions_IdAndQuantityGreaterThan(actionId, 0L);

    return products.stream().map(productDataConverter::convert)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ProductData::getName))));
  }
}
