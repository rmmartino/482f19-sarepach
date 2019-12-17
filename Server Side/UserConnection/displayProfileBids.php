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
    $email = $_GET['email'];

    // View what items the specific user bidded on
    $sql = "select * from bidsOn where email = '$email' and datetime in (select max(datetime) from bidsOn where email = '$email' group by email, id);";


    $result = mysqli_query($conn, $sql);

     // Check to see if the user bidded on at least one item
     if (mysqli_num_rows($result) > 0)
     {
         while($row = $result->fetch_assoc())
         {
            $id = $row['id'];

            $itemInfo = $conn->query("select * from item where id = '$id';");
            $itemInfo = $itemInfo->fetch_assoc();

            // Retrieve the highest bidder for each item
            $topBid = $conn->query("select * from bidsOn where id = '$id' and amount = (select max(amount) from bidsOn where id = $id group by id);");
            $topBid = $topBid->fetch_assoc();

            if ($topBid['email'] == $email){
                 $isTopBid = true;
            }
            else{
                 $isTopBid = false;
            }
                        echo $itemInfo['name'], ";";
            echo "http://sarepach.cs.loyola.edu/Administrators/uploads/", $itemInfo['image'], ";";
            echo $row['amount'], ";";
            echo var_export($isTopBid), "_";
         }
    }
    else{
        echo "no bids";
    }
  mysqli_close($conn);

?>

                    
                                 
