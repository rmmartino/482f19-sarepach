<?php

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

$email = $_GET['email'];
// Retrieving the password the user wants to change their old password to
$oldPassword = $_GET['oldPassword'];
$newPassword = $_GET['newPassword'];

$options = [ ‘cost’ => 12 ];

// Encrypt the password
$encpass = password_hash($newPassword, PASSWORD_BCRYPT, $options );

// View the user with the specified email
$sql = "SELECT * FROM user WHERE email = '$email';";

$result = mysqli_query($conn, $sql);

// Check to see if there is a user with the email
if(mysqli_num_rows($result)  > 0 ) {
        $retrievedEncryptedPassword = (mysqli_fetch_assoc($result))['password'];

        //check if password entered by user matches encrypted password in database
        $check = password_verify( $oldPassword, $retrievedEncryptedPassword );
        if($check) {
                // Change the password of the specified user
                $sqlUpdate = "UPDATE user SET password = '$encpass' where email like '$email'";
                if ($conn->query($sqlUpdate) == TRUE) {
                        echo "Successful";
                } else {
                       echo "Error: " . $sqlUpdate . "<br>" . $conn->error;
                }
        }
        else {
                echo "Passwords don't match";
        }
} else {
    echo "Failure";
}

mysqli_close( $conn );
?>                           
