<html>
 <head>
  <title>Update Items</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Update Item PHP</h1>

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

  // Retrieve values that the user wants to update item to
  $newStartingPrice = $_GET['newStartingPrice'];
  $newIncrement = $_GET['newIncrement'];
  $newDescription = $_GET['newDescription'];
  $newCategory = $_GET['newCategory'];
  $newName = $_GET['newName'];


  // Retrieve id variable from user input on previous page
  session_start();
  $id = $_SESSION['id'];

  // Check to see if the admin wants to change the starting price of the item
  if($newStartingPrice != ""){
    // Update the starting price of the item with the specific id
    $sqlUpdateStartingPrice = "UPDATE item SET startingPrice = $newStartingPrice WHERE id = $id";
    // Check that the item was updated successfully
    if ($conn->query($sqlUpdateStartingPrice) == TRUE) {
      echo "<strong> Starting price of item was updated successfully! </strong>" . "<br>";
    } else {
      echo "Error: " . $sqlUpdateStartingPrice . "<br>" . $conn->error;
    }
  }

  else {
    echo "Starting price of item was not updated" . "<br>";
  }

  // Check to see if the admin wants to change the increment of the item
  if($newIncrement != ""){
    // Update the increment of the item with the specific id
    $sqlUpdateIncrement = "UPDATE item SET increment = $newIncrement WHERE id = $id";
    // Check that the item was updated successfully
    if ($conn->query($sqlUpdateIncrement) == TRUE) {
      echo "<strong> Increment of item was updated successfully! </strong>" . "<br>";
    } else {
      echo "Error: " . $sqlUpdateIncrement . "<br>" . $conn->error;
    }
  }

  else {
    echo "Increment of item was not updated" . "<br>";
  }

  // Check to see if the admin wants to change the description of the item
  if($newDescription != "") {
    // Update the description of the item with the specific id
    $sqlUpdateDescription = "UPDATE item SET description = '$newDescription' WHERE id = $id";
    // Check that the item was updated successfully
    if ($conn->query($sqlUpdateDescription) == TRUE) {
      echo "<strong> Description of item was updated successfully! </strong>". "<br>";
    } else {
      echo "Error: " . $sqlUpdateDescription . "<br>" . $conn->error;
    }
  }

  else {
    echo "Description of item was not updated" . "<br>";
  }

  // Check to see if the admin wants to change the category of the item
  if($newCategory != "") {
    // Update the category of the item with the specific id
    $sqlUpdateCategory = "UPDATE item SET category = '$newCategory' WHERE id = $id";
    // Check that the item was updated successfully
    if ($conn->query($sqlUpdateCategory) == TRUE) {
      echo "<strong> Category of item was updated successfully! </strong>". "<br>";
    } else {
      echo "Error: " . $sqlUpdateCategory . "<br>" . $conn->error;
    }
  }

  else {
    echo "Category of item was not updated" . "<br>";
  }

  // Check to see if the admin wants to change the name of the item
  if($newName != "") {
    // Update the name of the item with the specific id
    $sqlUpdateName = "UPDATE item SET name = '$newName' WHERE id = $id";
    // Check that the item was updated successfully
    if ($conn->query($sqlUpdateName) == TRUE) {
      echo "<strong> Name of item was updated successfully! </strong>" . "<br>";
    } else {
      echo "Error: " . $sqlUpdateName . "<br>" . $conn->error;
    }
  }

  else {
    echo "Name of item was not updated" . "<br>";
  }

  mysql_close( $conn );
?>

</html>
