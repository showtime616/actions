package com.discordia.actions;

import com.discordia.actions.converter.ActionDataConverter;
import com.discordia.actions.converter.ProductDataConverter;
import com.discordia.actions.exception.GiftException;
import com.discordia.actions.model.Action;
import com.discordia.actions.model.Product;
import com.discordia.actions.repository.ActionRepository;
import com.discordia.actions.repository.ProductRepository;
import com.discordia.actions.service.ActionService;
import com.discordia.actions.service.GiftService;
import com.discordia.actions.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GiftServiceTests {

  @TestConfiguration
  static class ActionServiceTestContextConfiguration {
    @Bean
    public GiftService giftService() {
      return new GiftService();
    }

    @Bean
    public ActionService actionService() {
      return new ActionService();
    }

    @Bean
    public ProductService productService() {
      return new ProductService();
    }
  }

  @Autowired
  private GiftService giftService;

  @Autowired
  private ActionService actionService;

  @Autowired
  private ProductService productService;

  @MockBean
  private ActionRepository actionRepository;

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private ActionDataConverter actionDataConverter;

  @MockBean
  private ProductDataConverter productDataConverter;

  private Product product;

  private Action action;

  @Before
  public void setUp() {
    product = new Product("Pen", 1L);
    product.setId(1L);

    action = new Action("testAction", Collections.singleton(product), 2L);
    action.setId(2L);

    product.setActions(Collections.singleton(action));
  }

  @Test(expected = GiftException.class)
  public void whenProductQuantityIsZero_thenGiftExceptionShouldBeThrown() throws GiftException {
    product.setQuantity(0L);

    giftService.takeGift(action.getId(), product.getId());
  }

  @Test(expected = GiftException.class)
  public void whenActionQuantityIsZero_thenGiftExceptionShouldBeThrown() throws GiftException {
    action.setQuantity(0L);

    giftService.takeGift(action.getId(), product.getId());
  }

  @Test
  public void whenAvailableProductIsTaken_thenProductNameShouldBeReturned() throws GiftException {
    when(actionRepository.findById(action.getId())).thenReturn(Optional.of(action));
    when(productRepository.findByIdAndActions_Id(product.getId(), action.getId())).thenReturn(Optional.of(product));

    final String productName = giftService.takeGift(action.getId(), product.getId());

    assertEquals(product.getName(), productName);
  }

  @Test
  public void whenAvailableProductIsTaken_thenActionAndProductQuantitiesShouldBeDecremented() throws GiftException {
    final long productQuantity = product.getQuantity();
    final long actionQuantity = action.getQuantity();

    product.setActions(Collections.singleton(action));

    when(actionRepository.findById(action.getId())).thenReturn(Optional.of(action));
    when(productRepository.findByIdAndActions_Id(product.getId(), action.getId())).thenReturn(Optional.of(product));

    giftService.takeGift(action.getId(), product.getId());

    assertEquals(productQuantity, product.getQuantity() + 1);
    assertEquals(actionQuantity, action.getQuantity() + 1);
  }

}
