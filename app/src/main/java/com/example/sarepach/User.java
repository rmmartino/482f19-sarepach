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
  protected int HouseNumber;

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
   * @param: email - User's email address
   * @param: passworfd - User's password
   * @return: None
  */
  User(String email, String password ) {
    this.Email = email;
    this.Password = password;
    return;
  }

  /**
   * When user gives address information update it accordingly
   * @param: streetname - User's street name
   * @param: city - User's city
   * @param: state - User's state
   * @param: housenumber - User's house number
   * @param: zipcode - User's zipcode
   * @return: None
  */
  public void UpdateAddress(String streetname, String city, String state, int housenumber, int zipcode) {
    this.StreetName = streetname;
    this.City = city;
    this.State = state;
    this.HouseNumber = housenumber;
    this.ZipCode = zipcode;
    return;
  }


  
}
