<?php

// Create connection
$conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

/* Getting filter choice selected by the user */
$filterChoice = $_GET['filterChoice'];

// Run different SQL statements based on the filter chosen by the user on the app
if(strcmp($filterChoice, "Trending") == 0 ) {
  $sql = "SELECT * FROM item";
}
else if(strcmp($filterChoice, "Price: Low to High") == 0) {
  $sql = "SELECT * FROM item ORDER BY currentPrice";
}
else if(strcmp($filterChoice, "Price: High to Low") == 0) {
  $sql = "SELECT * FROM item ORDER BY currentPrice DESC";
}
else if(strcmp($filterChoice, "Home and Kitchen") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Home and Kitchen';";
}
else if(strcmp($filterChoice, "Clothing and Accessories") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Clothing and Accessories';";
}
else if(strcmp($filterChoice, "Food and Drink") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Food and Drink';";
}
else if(strcmp($filterChoice, "Technology") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Technology';";
}
else if(strcmp($filterChoice, "Travel and Vehicles") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Travel and Vehicles';";
}
else if(strcmp($filterChoice, "Sports and Entertainment") == 0) {
  $sql = "SELECT * FROM item WHERE category = 'Sports and Entertainment';";
}

$result = mysqli_query($conn, $sql);

// Checks if the SQL statements return at least one row
if (mysqli_num_rows($result) > 0)
{
  // Loop through each row of the database tables returned from the Select statements
  while($row = $result->fetch_assoc())
  {
        // Get the id of each item in the resulted database table
        $id = $row['id'];

        // Retrieve the highest bidder for each item
        $topBid = $conn->query("select * from bidsOn where id = '$id' and amount = (select max(amount) from bidsOn where id = $id group by id);");
        $topBid = $topBid->fetch_assoc();

        // Display each item
        echo $row['name'], ";";
        echo "http://sarepach.cs.loyola.edu/Administrators/uploads/", $row['image'], ";";

        if ($topBid['amount'] != NUll){
                echo $topBid['amount'] + $row['increment'], "_"; // min bid
        } else {
                echo $row['startingPrice'], "_";// + $row['increment'], "_"; // min bid
        }
   }
}
else{
       echo "no bids";
}
mysqli_close($conn);
?>
