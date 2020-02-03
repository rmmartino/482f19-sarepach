<?php

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

/* Getting user's email and password from login */
$email = $_GET['usernameText'];
$password = $_GET['passwordText'];
$options = [ ‘cost’ => 12 ];

// Encrypt the password
$encpass = password_hash($password, PASSWORD_BCRYPT, $options );

// View the user with the specified email
$sql = "SELECT * FROM user WHERE email = '$email';";

$result = mysqli_query($conn, $sql);

// Check to see that there is at least one user with the email in the table
if(mysqli_num_rows($result)  > 0 ) {
        $retrievedEncryptedPassword = (mysqli_fetch_assoc($result))['password'];

        // Check if password entered by user matches encrypted password in database
        $check = password_verify( $password, $retrievedEncryptedPassword );

        // This means that the password matches the password in the row with the email 
        // so the email and password combination are correct
        if($check) {
          echo "Success";
        }
        // Email and password combination are incorrect
        else {
          echo "Failure";
        }
// There is no user with the specified email
} else {
    echo "Failure";
}

$conn->close();

?>
