package com.discordia.actions.data;

import java.util.Set;

public class ActionData
{
  private Long id;
  private String name;
  private Long quantity = 0L;
  private Set<ProductData> products;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getQuantity()
  {
    return quantity;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public Set<ProductData> getProducts()
  {
    return products;
  }

  public void setProducts(Set<ProductData> products)
  {
    this.products = products;
  }
}
