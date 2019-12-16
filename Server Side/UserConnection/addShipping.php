<?php

/* Getting the information user searched for */
  $email = $_GET['email'];
  $house = $_GET['houseInput'];
  $street = $_GET['streetInput'];
  $city = $_GET['cityInput'];
  $state = $_GET['stateInput'];
  $zipcode = $_GET['zipcodeInput'];
  $name = $_GET['nameShippingInput'];

  //encode card number
  #$encpass = password_hash($password, PASSWORD_BCRYPT, $options );


  //Validate card number!!


/* ACCESS THE DATABASE */
$servername = "cs-database.cs.loyola.edu";
$dbusername = "sflahert";
$dbpassword = "1732754";
$dbname = "sarepach";

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Add shippping information (their address) to the user bidding
$sql = "UPDATE user SET houseNumber = '$house', street = '$street', town = '$city', state = '$state', zipcode = '$zipcode', nameShipping = '$name'  where email like '%$email%';";

// Check to see if the shipping address was added
if ($conn->query($sql) == TRUE) {
//    echo "<strong> Shipping info added successfully! </strong>";
        echo "Shipping Added Successfully";
} 
else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close( $conn );

?>
