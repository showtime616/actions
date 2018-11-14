package com.discordia.actions.service;

import com.discordia.actions.exception.GiftException;
import com.discordia.actions.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GiftService {
  @Autowired
  private ActionService actionService;

  @Autowired
  private ProductService productService;

  @Transactional(rollbackOn = GiftException.class)
  public String takeGift(final Long actionId, final Long productId) throws GiftException {
    return actionService.takeGift(actionId)
            .map(action -> productService.takeGift(action.getId(), productId))
            .flatMap(optionalProduct -> optionalProduct.map(Product::getName))
            .orElseThrow(GiftException::new);
  }
}
