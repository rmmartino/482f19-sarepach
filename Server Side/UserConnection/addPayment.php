<?php

/* Getting the information user searched for */
  $email = $_GET['email'];
  $creditCard = $_GET['creditCard'];
  $expirationDate = $_GET['expirationDate'];
  $csv = $_GET['csv'];
  $name = $_GET['name'];
  //encrypt card number
  $options = [ ‘cost’ => 12 ];
  $creditCardEncrypt = password_hash($creditCard, PASSWORD_BCRYPT, $options );

/* ACCESS THE DATABASE */
$servername = "cs-database.cs.loyola.edu";
$dbusername = "rmmartino";
$dbpassword = "1738874";
$dbname = "sarepach";

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Add credit card information to the user that is bidding
$sql = "UPDATE user SET creditCard = '$creditCardEncrypt', nameOnCard = '$name', expirationDate = '$expirationDate', csv = '$csv' where email like '$email';";

// Check to see if the credit card information was added
if ($conn->query($sql) == TRUE) {
    echo "Successful"
} else { 
    echo "Error with Information";
}

mysqli_close( $conn );

?>
