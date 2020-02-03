<html>

 <head>
  <title>Log In PHP</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Log In PHP </h1>

<body>
<!-- switching to PHP code -->
<?php

  /* Getting the information user searched for */
  $username = $_GET['username'];
  $password = $_GET['password'];

$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Retrieve the rows in the database table with the specific admin username trying to be logged into
$sql = "SELECT * FROM admins WHERE username LIKE '$username'";

$result = mysqli_query($conn, $sql);

// Check that there is at least one row in the database table with the admin username
if (mysqli_num_rows($result)>0)
{
        $retrievedEncryptedPassword = (mysqli_fetch_assoc($result))['password'];
        // Check that the admin's password matches the row in the database table with the username being logged into
        $check = password_verify( $password, $retrievedEncryptedPassword );
        if ($check)
{       echo "<strong> WELCOME </strong> <br/>";
        echo ' <a href="viewUsers.php">View Users</a> <br/>';
        echo ' <a href="viewItem.php">View Items</a> <br/>';
        echo ' <a href="addItem.html">Add Item</a> <br/>';
        echo ' <a href="changePassword.html">Change Password</a> <br/>';
        echo ' <a href="addAdmins.html">Add Admins</a> <br/>';
        echo ' <a href="updateItem.html">Update Item</a> <br/>';
        echo ' <a href="removeItem.html">Remove Item</a> <br/>';
        echo ' <a href="seeWinners.php">See Winners</a> <br/>';
}

        else
        echo "Sorry, not a valid username and password.";
}
else
{
        echo "Sorry, not a valid username and password.";
}

?>

<?php

mysql_close( $link );

?>

</html>
