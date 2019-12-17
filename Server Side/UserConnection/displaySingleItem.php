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

/* Getting id of item */
$name = $_GET['name'];

$sql = "SELECT * FROM item WHERE name = '$name';";
$result = mysqli_query($conn, $sql);

if(mysqli_num_rows($result)  > 0) {
  while($row = $result->fetch_assoc())
  {
    $id = $row['id'];
    $topBid = $conn->query("select * from bidsOn where id = '$id' and amount = (select max(amount) from bidsOn where id = $id group by id);");
    $topBid = $topBid->fetch_assoc();

    echo $row['name'], ";";
    echo "http://sarepach.cs.loyola.edu/Administrators/uploads/", $row['image'], ";";
    echo $row['description'], ";";



    if ($topBid['amount'] != NUll){
        echo $topBid['amount'], ";"; // current bid
        echo $topBid['amount'] + $row['increment'], "_"; // min bid
    } else {
        echo 0, ";"; //current bid
        echo $row['startingPrice'], "_"; // + $row['increment'], "_"; // min bid
    }
  }
} else {
  echo "Not a valid id";
}

mysqli_close($conn);

?>
