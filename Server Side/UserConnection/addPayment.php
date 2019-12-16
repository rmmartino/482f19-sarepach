<?php

/* Getting the information user searched for */
  $email = $_GET['email'];
  $creditCard = $_GET['creditCard'];
  $expirationDate = $_GET['expirationDate'];
  $csv = $_GET['csv'];
  $name = $_GET['name'];
  //encode card number
  #$encpass = password_hash($password, PASSWORD_BCRYPT, $options );


  //Validate card number!!


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
$sql = "UPDATE user SET creditCard = '$creditCard', nameOnCard = '$name', expirationDate = '$expirationDate', csv = '$csv' where email like '$email';";

// Check to see if the credit card information was added
if ($conn->query($sql) == TRUE) {
//    echo "<strong> Credit card info added successfully! </strong>";
   // echo "Successfully"
} 
else {
    echo "Error with Information";
    //echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close( $conn );

?>
