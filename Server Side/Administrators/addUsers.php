<html>
 <head>
  <title>Sign Up User PHP</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Add Users PHP</h1>

<body>
<!-- switching to PHP code -->
<?php

/* Getting the information user searched for */
  $email = $_GET['email'];
  $password = $_GET['password'];

  $options = [ ‘cost’ => 12 ];
  // Encrypt the password
  $encpass = password_hash($password, PASSWORD_BCRYPT, $options );



  //Check Input passed correctly
  echo nl2br("Email Entered : $email\n");
  echo nl2br("Password Entered: $password\n");

/* ACCESS THE DATABASE */
$servername = "cs-database.cs.loyola.edu";
$dbusername = "cnmeier";
$dbpassword = "1732821";
$dbname = "sarepach";

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Insert new user into the database table
$sql = "INSERT INTO user(email, password)
VALUES ('$email', '$encpass')";

// Check that the user was inserted into the table
if ($conn->query($sql) === TRUE) {
    echo "<strong> New record created successfully! </strong>";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysql_close( $link );

?>

</html>
