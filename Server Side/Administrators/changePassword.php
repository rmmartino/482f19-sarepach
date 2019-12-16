<html>
 <head>
  <title>Change Password PHP</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Change Password PHP</h1>

<body>

<!-- switching to PHP code -->
<?php

/* Getting the information user searched for */
  $username = $_GET['username'];
  $oldpassword = $_GET['oldPassword'];
  $newInput = $_GET['newInputPassword'];

  $options = [ ‘cost’ => 12 ];
  // Encrypt the new password of the admin
  $newpassword = password_hash($newInput, PASSWORD_BCRYPT, $options );

  //Check Input passed correctly
  echo nl2br("Username Entered : $username\n");
  echo nl2br("Password Old Entered: $oldpassword \n");
  echo nl2br("New password: $newInput \n");

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

// Retrieve the columns with a specific admin username
$sql1 = "SELECT * from admins WHERE username LIKE '$username'";

// Change the password of the admin in the database table
$sql2 = "UPDATE admins set password='$newpassword' WHERE username LIKE '$username'";

$result = mysqli_query($conn, $sql1);

// Check if the result of the select statement includes at least 1 row
if (mysqli_num_rows($result) > 0) {
        echo nl2br("username in database \n");
        $retrievedEncryptedPassword = (mysqli_fetch_assoc($result))['password'];

        // Check that the admin's old password matches the password in the database table
        $check = password_verify( $oldpassword, $retrievedEncryptedPassword );
        if ($check)
        {
            // Run the update statement
            mysqli_query($conn, $sql2);
            echo nl2br("<strong> PASSWORD UPDATED </strong> \n");
        }
        else
          echo nl2br("<strong> Failed: Username and password don't match </strong> \n");
        }
else
{
        echo nl2br("<strong> username and password not valid</strong> \n");
}

mysql_close( $link );

?>

</html>
