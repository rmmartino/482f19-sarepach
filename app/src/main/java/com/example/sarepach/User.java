/**
 * @author Patrick Sacchet
 * @version 1.0
*/

package com.example.sarepach;

import java.util.*;
import java.lang.*;

/**
 * This is a User class that represents an instance of a User
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
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
   * Constructor that takes in just the email and password because that's all that's required for
   * initial signup and sign in
   *
   * @param: email
   *            User's email address
   * @param: password
   *            User's password
   */
  User(String email) {
    this.Email = email;
    return;
  }

  /**
   * Updates the address of the user when they put that information in
   *
   * @param: streetname
   *              User's street name
   * @param: city
   *              User's city
   * @param: state
   *              User's state
   * @param: housenumber
   *              User's house number
   * @param: zipcode
   *              User's zipcode
  */
  public void UpdateAddress(String streetname, String city, String state, int housenumber, int zipcode) {
    this.StreetName = streetname;
    this.City = city;
    this.State = state;
    this.HouseNumber = housenumber;
    this.ZipCode = zipcode;
    return;
  }

  /**
   * Updates the credit card information of the user when they put that information in
   *
   * @param: creditcardnumber
   *                 User's credit card number
   * @param: expdate
   *                User's expiration date for their credit card
   * @param: csv
   *                User's CSV for their credit card
  */
  public void UpdateCreditCard( int creditcardnumber, int expdate, int csv) {
    this.CreditCardNumber = creditcardnumber;
    this.ExpDate = expdate;
    this.CSV = csv;
    return;
  }

  /**
   * Updates users information (first name, last name, phone number) when they put
   * that information in
   *
   * @param: firstname
   *              User's first name
   * @param: lastname
   *              User's last name
   * @param: phonenumber
   *              User's phone number
  */
  public void UpdateInfo(String firstname, String lastname, int phonenumber) {
    this.FirstName = firstname;
    this.LastName = lastname;
    this.PhoneNumber = phonenumber;
    return;
  }

}
