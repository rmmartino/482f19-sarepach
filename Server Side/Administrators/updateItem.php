<html>
 <head>
  <title>Update Items</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Update Item PHP </h1>

<!-- switching to PHP code -->
<?php

  /* Getting the id the user entered */
  session_start();
  $id = $_GET['id'];
  session_start();
  $_SESSION['id'] = $id;

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

// View the row in the item table with the specific id
$sqlSelect = "SELECT * FROM item WHERE id = $id";
$result = $conn->query($sqlSelect);

// Check to see if there is an item in the database table with the specific id
if($result->num_rows > 0) {
     while($row = $result->fetch_assoc()) {
        echo "ID: " . $row["id"] . "<br>". "Starting Price: " . $row["startingPrice"] . "<br>". "Increment:  " . $row["increment"] . "<br>". "Description: " . $row["description"] . "<br>". "Category: " . $row["category"] . "<br>". "Name: " . $row["name"]. "<br>";
    } ?>
    <body>

        <!-- Check to see what field the admin wants to update -->
        <h2> Which field do you want to update?</h2>

        <p>Select a minimum of 1 choice</p>

        <!-- Provide a list of fields for the admin to update so that they can select which ones they want to update -->
        <form action="" method="post">
        <input type="checkbox" name="startingPrice" value="startingPrice" id="startingPrice"/>
        <label for="startingPrice">Starting Price</label><br>

        <input type="checkbox" name="increment" value="increment" id="increment"/>
        <label for="increment">Increment</label><br>

        <input type="checkbox" name="description" value="description" id="description"/>
        <label for="description">Description</label><br>

        <input type="checkbox" name="category" value="category" id="category"/>
        <label for="category">Category</label><br>

        <input type="checkbox" name="name" value="name" id="name"/>
        <label for="name">Name</label><br>

        <input type="submit" name="submitOptions" value="Continue" />
        </form>
    </body>
<?php
} else {
    echo "0 results";
}

$conn->close();

// Retrieve the values that the user inputted for each field
$startingPrice = $_POST['startingPrice'];
$increment = $_POST['increment'];
$description = $_POST['description'];
$category = $_POST['category'];
$name = $_POST['name'];
?>

<form action="updateItem2.php" method="get" enctype="multipart/form-data">

<?php
} else {
    echo "0 results";
}

$conn->close();

// Retrieve the values that the user inputted for each field
$startingPrice = $_POST['startingPrice'];
$increment = $_POST['increment'];
$description = $_POST['description'];
$category = $_POST['category'];
$name = $_POST['name'];
?>

<form action="updateItem2.php" method="get" enctype="multipart/form-data">

<?php
// Check to see if the admin wants to update the starting price of the item
if(isset($startingPrice)){ ?>
   <!-- Ask the admin for the new starting price of the item -->
   Enter new starting price of item: <br> <input type="float" name="newStartingPrice"><br>
<?php }

// Check to see if the admin wants to update the increment of the item
if(isset($increment)){ ?>
    <!-- Ask the admin for the new increment of the item -->
    <br>Enter new increment of item: <br> <input type="number" name="newIncrement"><br>
<?php }

// Check to see if the admin wants to update the description of the item
if(isset($description)){ ?>
    <!-- Ask the admin for the new description of the item -->
    <br>Enter new description of item: <br> <input type="text" name="newDescription"><br>
<?php }

// Check to see if the admin wants to update the category of the item
if(isset($category)){ ?>
    <!-- Ask the admin for the new categroy of the item -->
    <br>Enter new category of item: <br> <input type="text" name="newCategory"><br>
<?php }

// Check to see if the admin wants to update the name of the item
if(isset($name)){ ?>
    <!-- Ask the admin for the new name of the item -->
    <br>Enter new name of item: <br> <input type="text" name="newName"><br>
<?php }

// Check to see if the admin inputted text into at least one of these fields
if(isset($startingPrice) || isset($increment) || isset($description) || isset($category) || isset($name)){?>
  <input type="submit" name="submitInput" value="Continue" />

</form>
  <?php }
?>
</html>
