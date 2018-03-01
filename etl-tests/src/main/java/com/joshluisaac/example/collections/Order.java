package com.joshluisaac.example.collections;

public class Order {
  
  int orderId;
  String description;
  
  public Order (int orderId, String description) {
  
      this.orderId = orderId;
      this.description = description;
  }
  
  public Order (){}
  
  
  public String getDescription(){
    return this.description;
  }
  
  public int getOrderId() {
    return this.orderId;

  }
  
  public static void main(String[] args) {
  Order order = new Order(1, "Fipper");
  System.out.println(order.getDescription());
  System.out.println(order.getOrderId());
  }

}
