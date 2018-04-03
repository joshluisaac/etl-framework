package com.kollect.etl.entity;

public class TransferObject {
  
  private static final long serialVersionUID = 3013754764417463899L;
  public static final int SUCCESS = 0;
  public static final int ERROR = 1;
  public static final int EXCEPTION = 2;
  private final String message;
  private final int flag;
  private final Object object;

  public TransferObject() {
    this(null, SUCCESS, null);
  }

  /**
   * Flags for success for you!
   */
  public TransferObject(final Object holder) {
    this(holder, SUCCESS, null);
  }

  public TransferObject(final int flag, final String message) {
    this(null, flag, message);
  }

  public TransferObject(final Object holder, final int flag, final String message) {
    this.object = holder;
    this.flag = flag;
    this.message = message;
  }

  public Object getBeanHolder() {
    return object;
  }

  public String getMessage() {
    return message;
  }

  public int getFlag() {
    return flag;
  }

  public boolean isFailed() {
    return getFlag() != SUCCESS;
  }
  
  

}
