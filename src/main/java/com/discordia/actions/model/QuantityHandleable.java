package com.discordia.actions.model;

public interface QuantityHandleable {
  Long getQuantity();

  void setQuantity(Long quantity);

  default QuantityHandleable decrementQuantity() {
    final Long currentQuantity = getQuantity();
    if (currentQuantity > 0)
    {
      setQuantity(currentQuantity - 1);
      return this;
    }

    return null;
  }
}
