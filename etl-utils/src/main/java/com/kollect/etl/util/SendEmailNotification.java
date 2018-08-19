package com.kollect.etl.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

/**
 * A concrete class for sending emails
 * It firsts pings for connection to SMTP server before sending email notification
 *
 * @author Josh Uzo Nwankwo
 * @version 1.0
 */

public class SendEmailNotification {

  private static final Logger LOG = LoggerFactory.getLogger(SendEmailNotification.class);

  /** Checks if SMTP server is reachable or not reachable
   *
   * @param smtpServerIp the IP address of SMTP server
   * @return a boolean value, true or false.
   *        a true value means SMTP server is reachable, while false means the opposite
   */
  public boolean pingSMTPServer(String smtpServerIp){
    //ping logic goes in here...
    return false;
  }

  /** Sends email to SMTP server
   *
   * @param userName sender's username login credential
   * @param password sender's password
   * @throws ConnectException connectException
   * @return an int primitive. 1 means sent while -1 means not sent
   *
   */
  public int sendEmail(String userName, String password) throws ConnectException{

    // Step 1: test connection
    boolean smtpIsReachable  = pingSMTPServer("192.168.1.100");
    if (!smtpIsReachable) {
      // write error to event log
      LOG.error("Error! SMTP server is not reachable");
      //throw error and exit
      //throw new SmtpNotReachableException();
      throw new ConnectException();
    } else {

      // Step2: execute send email
      executeSendEmail();

      //Step 3: Write success message status to event log file
      LOG.info("Email sent!");
      return -1;

    }
  }



   int executeSendEmail(){
    return 1;
  }


}
