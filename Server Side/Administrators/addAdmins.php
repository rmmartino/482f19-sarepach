<html>
 <head>
  <title>Sign Up PHP</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Add Admins PHP</h1>

<body>
<!-- switching to PHP code -->
<?php

/* Getting the information user searched for */
  $username = $_GET['username'];
  $password = $_GET['password'];
  $email = $_GET['email'];

  $options = [ ‘cost’ => 12 ];
  // Encrypt the old password
  $encpass = password_hash($password, PASSWORD_BCRYPT, $options );

//Check Input passed correctly
  echo nl2br("Username Entered : $username\n");
  echo nl2br("Password Entered: $password\n");

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

// Insert admin into table
$sql = "INSERT INTO admins (username, password,email)
VALUES ('$username', '$encpass', '$email')";

// Check sql statement to make sure new admin was inserted into table
if ($conn->query($sql) === TRUE) {
    echo "<strong> New record created successfully! </strong>";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysql_close( $link );

?>

</html>
