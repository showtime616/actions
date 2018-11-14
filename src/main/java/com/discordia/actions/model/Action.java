package com.discordia.actions.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Action implements QuantityHandleable {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "action_product", joinColumns = @JoinColumn(name = "action_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  private Set<Product> products = new HashSet<>();

  @Column(nullable = false)
  private Long quantity;

  public Action() {
  }

  public Action(final String name, final Set<Product> products, final Long quantity) {
    this.name = name;
    this.products = products;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  @Override
  public Long getQuantity() {
    return quantity;
  }

  @Override
  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

}
