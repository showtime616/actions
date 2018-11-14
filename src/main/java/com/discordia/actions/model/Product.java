package com.discordia.actions.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Product implements QuantityHandleable {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long quantity;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "action_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "action_id"))
  private Set<Action> actions;

  public Product() {
  }

  public Product(final String name, final Long quantity) {
    this.name = name;
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

  @Override
  public Long getQuantity() {
    return quantity;
  }

  @Override
  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Set<Action> getActions() {
    return actions;
  }

  public void setActions(Set<Action> actions) {
    this.actions = actions;
  }
}
