package com.joshluisaac.example.patterns.withoutIOC;

public class ClientAppWithoutIOC {
  public static void main(String[] args) {
    UserWithOutIOC user = new UserWithOutIOC(3);
    user.Add();
  }
}
