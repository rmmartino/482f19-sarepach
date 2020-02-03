<html>
 <head>
  <title>Add Items</title>
 </head>

<h1 style = "background-color: #68B7D5;"> Add Item PHP</h1>

<body>
<!-- switching to PHP code -->
<?php

/* Getting the information user searched for */
  $startPrice = $_POST['startingPrice'];
  $inc = $_POST['increment'];
  $desc= $_POST['description'];
  $cat = $_POST['category'];
  $name = $_POST['name'];
  $imageFile =$_FILES['myFile']['name'];

//Check Input passed correctly
  echo("Stating price: $startPrice <br>");
  echo ("Increment: $inc <br>");
  echo ("Description: $desc <br>");
  echo ("Category: $cat <br>");
  echo ("Name: $name <br>");
  echo ("Image File: $imageFile <br>");

//move image to the directory
$location = ($_FILES['myFile']['tmp_name']);
echo ("location is: $location");
$newpath= "uploads/";
echo exec("cp $location $newpath/$imageFile");
chmod("$newpath/$imageFile", 0775);

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Store info and image name in database table
$sql = "INSERT INTO item (startingPrice, increment, description, category, name, image) VALUES ('$startPrice', '$inc', '$desc', '$cat', '$name', '$imageFile')";

//Check that new item was inserted into the table
if ($conn->query($sql) === TRUE) {
    echo "<br> <strong> New item added successfully! </strong>";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close( $conn );

?>

</html>
