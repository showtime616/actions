package com.discordia.actions.controller;

import java.util.Optional;
import java.util.Set;

import com.discordia.actions.exception.GiftException;
import com.discordia.actions.service.GiftService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.discordia.actions.data.ActionData;
import com.discordia.actions.data.ProductData;
import com.discordia.actions.service.ActionService;
import com.discordia.actions.service.ProductService;

@Controller
@RequestMapping(value = "/action")
public class ActionController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ActionService actionService;

  @Autowired
  private ProductService productService;

  @Autowired
  private GiftService giftService;

  @GetMapping(value = "/{actionId}")
  public String getAction(@PathVariable(value = "actionId") final Long actionId,
          @RequestParam(value = "searchText", required = false) final String searchText,
          final Model model) {
    logger.debug("getAction actionId: {} searchText: {}", actionId, searchText);

    final Optional<ActionData> actionData = actionService.getAction(actionId);
    actionData.filter(action -> action.getQuantity() > 0).ifPresent(action -> {
      final Set<ProductData> productDataSet = productService.getProducts(actionId, searchText);
      action.setProducts(productDataSet);
    });

    model.addAttribute("actionData", actionData);

    return "action";
  }

  @PostMapping(value = "/{actionId}/takeGift/{productId}")
  public String takeGift(@PathVariable(value = "actionId") final Long actionId, @PathVariable(value = "productId") final Long productId,
          @RequestParam(value="searchText", required = false) final String searchText,
          final RedirectAttributes redirectAttributes) {
    logger.debug("takeGift actionId: {} productId: {} searchText: {}", actionId, productId, searchText);

    try {
      final String takenProductName = giftService.takeGift(actionId, productId);
      redirectAttributes.addFlashAttribute("message", "Поздравляем, Вы получили подарок \"" + takenProductName + "\"");
      redirectAttributes.addFlashAttribute("alert", "success");
    } catch (GiftException e) {
      redirectAttributes.addFlashAttribute("message", "К сожалению, выбранный Вами подарок недоступен");
      redirectAttributes.addFlashAttribute("alert", "danger");
    }

    if (StringUtils.isNotBlank(searchText)) {
      redirectAttributes.addAttribute("searchText", searchText);
    }

    return "redirect:/action/" + actionId;
  }
}

