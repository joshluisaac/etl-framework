package com.joshluisaac.example.patterns.withIOC;

/**
 * 1. The User constructor decides which data access layer to use based on the
 * arguments passed to it at runtime.
 * <p>
 * 2. The decision of which data access implementation to use is controlled by
 * the User constructor.
 * <p>
 * 3. This is a bad design because the User class now has more than one reason
 * to change.
 * <p>
 * 4. Every class should have just one reason/requirement to change (SRP). This
 * brings us to IoC
 * <p>
 * 4. Instead pass the data access type through the constructor by way of an
 * interface and let the implementation be specified at runtime.
 * <p>
 * 5. The User class is longer controlling where it's data access layer is
 * coming from.
 * <p>
 * 6. This way the control has been inverted from the User class and moved over
 * to the client class, which is the app class.
 * <p>
 * 7. The User class has no idea where it's data access is coming from.
 * <p>
 * 8. It only has the notion that it needs a data access type for it's Add()
 * operation but where it comes from is entirely irrelevant.
 * <p>
 * 9. It is the job of the client class to specify which data access it needs to
 * use at runtime.
 * <p>
 * 10. We have also narrowed the focus of the User class to what exactly it
 * needs to do, like validate user and add user to db
 * 
 */

public class User {

  // region of DIs
  private IDataAccess dal;

  public User(IDataAccess dal) {
    this.dal = dal;
  }

  private boolean isValid() {
    return true;
  }

  public void Add() {
    if (isValid()) {
      dal.Add(this);
    }
  }
}
