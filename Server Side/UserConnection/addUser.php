<?php

/* Getting the information user searched for */
  $email = $_GET['email'];
  $password = $_GET['password'];
  $first = $_GET['firstName'];
  $last = $_GET['lastName'];

  $options = [ ‘cost’ => 12 ];

  // Encrypt the password
  $encpass = password_hash($password, PASSWORD_BCRYPT, $options );

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Insert the user into the table and include the encrypted password for security purposes
$sql = "INSERT INTO user(email, password, firstName, lastName)
VALUES ('$email', '$encpass', '$first', '$last');";

// Check to see if the user was added correctly
if ($conn->query($sql) === TRUE) {
  echo ("Success and firstname is : $first");

} else {
    echo "Error: " . $sql . "<br>" . $conn->error . "firstname is : $first";
}

mysqli_close( $conn );

?>
