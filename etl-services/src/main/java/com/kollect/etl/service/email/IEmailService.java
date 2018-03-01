package com.kollect.etl.service.email;

/**
 * Interface that defines common email operations.
 *
 * @author Josh Nwankwo
 */

public interface IEmailService {

  public boolean sendEmail(String user, String password, String host, String subject, String content, String userAuth, int port, String[] recipient);
  public final static String EMAIL_FAILURE_FLAG = "EMAIL_SUCCESS";
  public final static String EMAIL_SUCCESS_FLAG = "EMAIL_FAILURE";

}
