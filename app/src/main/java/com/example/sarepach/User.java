/**
 * @author Patrick Sacchet
 * @version 1.0
*/

package com.example.sarepach;

import java.util.*;

/**
 * Class will represent an instance of a User
*/

public class User {

  /**
   * Each user has a phone number
  */
  protected int PhoneNumber;

  /**
   * Each user has a first name
  */
  protected String FirstName;

  /**
   * Each user has a last name
  */
  protected String LastName;

  /**
   * Each user has a email address
  */
  protected String Email;

  /**
   * Each user has a password (will only be transfered if it is encrypted, otherwise not accessible)
  */
  private String Password;

  /**
   * Each user has a street name which they live on
  */
  protected String StreetName;

  /**
   * Each user has a house number
  */
  protected String HouseNumber;

  /**
   * Each user has a city they live in
  */
  protected String City;

  /**
   * Each user has a state they live in
  */
  protected String State;

  /**
   * Each user has a zipcode for their address
  */
  protected int ZipCode;

  /**
   * Each user has a credit card number
  */
  protected int CreditCardNumber;

  /**
   * Each user has a expiration date for their credit card
  */
  protected int ExpDate;

  /**
   * Each user has a CSV for their credit card
  */
  protected int CSV;

  /**
   * User constructor, we only require email and password for initial sign up and sign in so thats all we require for this constructor
  */
  User(String email, String password ) {
    this.Email = email;
    this.Password = password;
    return;
  }

  
}
