package com.joshluisaac.priorityqueue;

public class Employee implements Comparable<Employee> {
  
  private Integer serialNo;
  private long id;
  private static long empCounter;
  
  public Employee(int serialNo){
    this.id = empCounter++;
    this.serialNo = serialNo;
  }
  
  public Integer getSerialNo(){return serialNo;}
  public long getId(){return id;}
  
  public String toString(){
    return "Employee(id:" +id+ ", serialNo: " +serialNo+ ")" ;
  }

  @Override
  public int compareTo(Employee o) {
    System.out.println("Comparing.." + this.serialNo + "|" +  o.getSerialNo());
    return this.serialNo.compareTo(o.getSerialNo());
  }

}
