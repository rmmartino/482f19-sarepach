<?php

    /* ACCESS THE DATABASE */
    $servername = "cs-database.cs.loyola.edu";
    $dbusername = "rmmartino";
    $dbpassword = "1738874";
    $dbname = "sarepach";

    // Create connection
    $conn = mysqli_connect($servername, $dbusername, $dbpassword, $dbname);

    // Check connection
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }
    //$email = $_GET['email'];

    // View what items are available
    $sql = "select * from item;";

    $result = mysqli_query($conn, $sql);

     if (mysqli_num_rows($result) > 0)
     {
         while($row = $result->fetch_assoc())
         {
            $id = $row['id'];

            // Retrieve the highest bidder for each item
            $topBid = $conn->query("select * from bidsOn where id = '$id' and amount = (select max(amount) from bidsOn where id = $id group by id);");
            $topBid = $topBid->fetch_assoc();

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
