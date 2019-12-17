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
$name = $_GET['name'];
$amount = $_GET['amount'];

$getId = "SELECT * FROM item WHERE name = '$name';";
$id = (($conn->query($getId))->fetch_assoc())['id'];

// Add who is bidding on what item
$sql = "INSERT INTO bidsOn(email, id, amount, datetime) VALUES ('$email', '$id', '$amount', now());";

// Check to see if the information was added
if ($conn->query($sql) === TRUE) {
    echo "Success";
    $updateItemSql = "UPDATE item SET currentPrice = '$amount' where id = '$id';";
    $conn->query($updateItemSql);
} else { 
    echo "Failure";
}

mysqli_close( $conn );

?>
