package com.joshluisaac.example.nested.classes;

public class MemberInnerClass {
  String vehicleManufacturer = "Volvo";

  private class Child {
    private int mileage = 160;
    private static final int mileageHex = 0x17fff;

    private int getMileage() {
      return mileage;
    }

    private void printVehicleManufacturer() {
      System.out.println(vehicleManufacturer);
    }
  }

  public static void main(String[] args) {

    /** Creates an instance of the outer class */
    MemberInnerClass in = new MemberInnerClass();

    /**
     * The inner class (Child) exists within the scope or boundaries of the outer
     * class, therefore to access non-static data members and methods of the inner
     * class has to be through instances of the inner class. To create an instance
     * of the inner class, you first have to create an instance of the outer class
     * and then create an instance of the inner class using this syntax
     * 
     */
    MemberInnerClass.Child child = in.new Child();

    /**
     * Accessing non-static members mileage which is a non-static data member of the
     * inner class (Child) can then be accessed like this
     */
    System.out.println(child.mileage);
    System.out.println(child.getMileage());
    child.printVehicleManufacturer();

    /**
     * static members of inner class should be accessed in a static way without
     * having to create an instance or object reference
     */
    System.out.println(Child.mileageHex);

  }

}

// It cannot access non-static (instance) data members and methods of the
// enclosing class
// It can access static data members and methods