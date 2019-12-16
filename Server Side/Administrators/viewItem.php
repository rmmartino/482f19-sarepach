<html>

<header>
<title>Sarepach</title>
</header>

<h1 style = "background-color: #68B7D5;"> View Items </h1>

<body>
<?php
/* ACCESS THE DATABASE */
$servername = "cs-database.cs.loyola.edu";
$dbusername = "sflahert";
$dbpassword = "1732754";
$dbname = 'sarepach';

$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// View all of the items in the database table 
$sql = "SELECT * FROM item";
$result = mysqli_query($conn, $sql);

    echo "<table border='1'>";

    echo "  <thead>
    <tr>
      <th >ID</th>
      <th >Starting Price</th>
      <th >Increment</th>
      <th >Description</th>
      <th >Category</th>
      <th >Name</th>
      <th >Image</th>
    </tr>
  </thead>";

// Check to see if there is at least one item in the table
if (mysqli_num_rows($result) > 0)
{

  while($row = mysqli_fetch_assoc($result))

  {
    echo ' <tr> ' ;

    echo '</td><td>' .$row['id'];
    echo '</td><td>' .$row['startingPrice'];
    echo '</td><td> ' .$row['increment'];
    echo ' </td><td>'.$row['description'];
    echo ' </td><td>'.$row['category'];
    echo ' </td><td>'.$row['name'];
    echo ' </td><td>'.$row['image'];
    echo ' </td>';

    echo ' </tr> ';
  }
  echo "</table>";

}
else
{
   echo "Error";
}

?>

</body>
</html>
