package com.discordia.actions;

import com.discordia.actions.converter.ProductDataConverter;
import com.discordia.actions.data.ProductData;
import com.discordia.actions.model.Product;
import com.discordia.actions.repository.ProductRepository;
import com.discordia.actions.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

  @TestConfiguration
  static class ActionServiceTestContextConfiguration {
    @Bean
    public ProductService productService() {
      return new ProductService();
    }

    @Bean
    public ProductDataConverter productDataConverter() {
      return new ProductDataConverter();
    }
  }

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductDataConverter productDataConverter;

  @MockBean
  private ProductRepository productRepository;

  @Test
  public void whenGetProductsWithNullProductId_thenEmptySetShouldBeReturned() {
    assertEquals(Collections.emptySet(), productService.getProducts(null, ""));
  }

  @Test
  public void whenGetProductsWithBlankSearchText_thenAllProductsShouldBeReturned() {
    final Long actionId = 3L;

    final Long productId = 1L;
    final String productName = "product";
    final Long productQuantity = 10L;

    final Product product = new Product(productName, productQuantity);
    product.setId(productId);

    final List<Product> products = Collections.singletonList(product);

    when(productRepository.findByActions_IdAndQuantityGreaterThan(actionId, 0L)).thenReturn(products);

    final Set<ProductData> productDataSet = productService.getProducts(actionId, null);

    assertEquals(1L, productDataSet.size());

    final ProductData productData = productDataSet.iterator().next();

    assertEquals(productId, productData.getId());
    assertEquals(productName, productData.getName());
    assertEquals(productQuantity, productData.getQuantity());
  }

  @Test
  public void whenGetProductsWithNotBlankSearchText_thenProductsWithNameContainingSearchTextShouldBeReturned() {
    final Long actionId = 3L;

    final Long firstProductId = 1L;
    final String firstProductName = "firstProduct";
    final Long firstProductQuantity = 10L;

    final Product firstProduct = new Product(firstProductName, firstProductQuantity);
    firstProduct.setId(firstProductId);

    final Long secondProductId = 1L;
    final String secondProductName = "secondProduct";
    final Long secondProductQuantity = 10L;

    final Product secondProduct = new Product(secondProductName, secondProductQuantity);
    secondProduct.setId(secondProductId);

    final String searchText = "second";

    when(productRepository.findByActions_IdAndNameContainingIgnoreCaseAndQuantityGreaterThan(actionId, searchText, 0L))
            .thenReturn(Stream.of(firstProduct, secondProduct).filter(p -> p.getName().contains(searchText)).collect(Collectors.toList()));

    final Set<ProductData> productDataSet = productService.getProducts(actionId, searchText);

    assertEquals(1L, productDataSet.size());

    final ProductData productData = productDataSet.iterator().next();

    assertEquals(secondProductId, productData.getId());
    assertEquals(secondProductName, productData.getName());
    assertEquals(secondProductQuantity, productData.getQuantity());
  }
}
