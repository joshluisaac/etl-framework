package com.joshluisaac.example.patterns.withIOC;

public class App {
  public static void main(String[] args) {
    User user = new User(new OracleDataAccessLayer());
    user.Add();
  }
}
