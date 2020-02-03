<html>
 <head>
  <title>Remove Items</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Remove Item PHP</h1>

<!-- switching to PHP code -->
<?php

  /* Getting the id the user entered */
  $id = $_GET['id'];

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Delete the item in the database table with the specific id
$sql = "DELETE FROM item WHERE id = $id";

// Check that the item was successfully deleted from the item table
if ($conn->query($sql) === TRUE) {
    echo "<strong> Item was deleted successfully! </strong>";

} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysql_close( $conn );

?>
                                                                                                      1,1           T
</html>
