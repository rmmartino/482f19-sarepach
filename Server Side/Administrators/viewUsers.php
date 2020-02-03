<html>

<header>
<title>Sarepach</title>
</header>

<h1 style = "background-color: #68B7D5;"> View Users </h1>

<body>

<?php

$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// View the users in the database table 
$sql = "SELECT * FROM user";
$result = mysqli_query($conn, $sql);

    // Create the table with the results
    echo "<table border='1'>";

    echo "  <thead>
    <tr>
      <th >Email</th>
      <th >First Name</th>
      <th >Last Name</th>
      <th >Credit Card Number</th>
      <th >Password</th>
      <th >Zip Code</th>
      <th >PhoneNumber</th> 
      <th >CSV</th>
      <th >Expiration Date</th>
      <th >House Number</th>
      <th >Street</th>
      <th >Town</th>
      <th >State</th>
   </tr>
  </thead>";

// Check to see if there is at least one user in the table
if (mysqli_num_rows($result) > 0)
{

  while($row = mysqli_fetch_assoc($result))

  {
    echo '</td><td>' .$row['email'];
    echo '</td><td>' .$row['firstName'];
    echo '</td><td> ' .$row['lastName'];
    echo ' </td><td>'.$row['creditCard'];
    echo ' </td><td>'.$row['password'];
    echo ' </td><td>'.$row['zipcode'];
    echo ' </td><td>'.$row['phoneNumber'];
    echo ' </td><td>'.$row['csv'];
    echo ' </td><td>'.$row['expirationDate'];
    echo ' </td><td>'.$row['houseNumber'];
    echo ' </td><td>'.$row['street'];
    echo ' </td><td>'.$row['town'];
    echo ' </td><td>'.$row['state'];
  }
  echo "</table>";

}
else
{
 echo "No Users Right Now";
}

?>

</body>
</html>
