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

// Getting information about bid from user
$email = $_GET['email'];
$id = $_GET['id'];
$amount = $_GET['amount'];

$intId = (int) $id;
// Add who is bidding on what item
$sql = "INSERT INTO bidsOn(email, id, amount, datetime) VALUES ('$email', '$intId', '$amount', now())";

// Check to see if the information was added
if ($conn->query($sql) === TRUE) {
    echo "Success";
    $updateItemSql = "UPDATE item SET currentPrice = $amount where id = $intId;";
    $conn->query($updateItemSql);
} else {
    echo "Failure";
}

mysqli_close( $conn );

?>
