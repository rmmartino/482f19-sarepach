/**
 * @author Patrick Sacchet
 * @version 1.0
*/

package com.example.sarepach;

import java.util.*;

/**
 * This is a UserSession class that will represent one instance of a user session, made up of
 * the current user and a connection to the studentvhost2 server
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class UserSession {

  /**
   * Each UserSession instance will have a User object that represents the user currently using the app
  */
  protected User CurrentUser;

  /**
   * Each UserSession will also have a live ServerConnection object to send requests to
  */
  protected ServerConnection CurrentConnection;
  
  /**
   * This is a constructor for an instance of UserSession
   *
   * @param current_user
   *            the current user this class represents
   * @param current_connection
   *            the current connection to the studentvhost2 server
   */
  UserSession(User current_user, ServerConnection current_connection) {
    this.CurrentUser = current_user;
    this.CurrentConnection = current_connection;
  }

}
