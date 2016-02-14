<?php  
	
	 
	 $mysqli = new mysqli("mysql12.000webhost.com","a5530916_chat","bluecadet3","a5530916_chat");
 
	$distance = 0.0005;
	 
 
if(isset($_POST["latitude"])&&isset($_POST["longitude"])&&isset($_POST["userID"])){

    $lat = $mysqli->real_escape_string($_POST["latitude"]);
    $long = $mysqli->real_escape_string($_POST["longitude"]);
    $userID = $mysqli->real_escape_string($_POST["userID"]);

    $maxLat = $lat+$distance;
    $minLat = $lat-$distance;

    $maxLong = $long+$distance;
    $minLong = $long-$distance;

    $query = "SELECT * FROM groupchat WHERE latitude<= '$maxLat' AND latitude>='$minLat' AND longitude<='$maxLong' AND longitude>='$minLong' AND userID != '$userID'";



    $result = $mysqli->query($query);

    $group = array(array());
    $counter = 0;
    while($row = $result->fetch_assoc()){

        $groupID = $row["groupID"];

        $query = "SELECT count(*) FROM groupchatrecipients WHERE groupID = $groupID";

		$countResult = $mysqli->query($query);
        $countRow = $countResult->fetch_row();
        $num = $countRow[0];

		if($num<10){
            $group[$counter]['groupID'] = $row["groupID"];
            $group[$counter]['userID'] = $row["userID"];
            $counter++;
        } else {
			$query = "DELETE FROM groupchat WHERE groupID = $groupID";
			
			$mysqli->query($query);
			
			$query = "DELETE FROM groupchatrecipients WHERE groupID = $groupID";
			
			$mysqli->query($query);
		}

    }

    $finalGroup = $group[rand(0, count($group)-1)];



    if(isset($finalGroup['groupID'])){

        $finalGroupID = $finalGroup['groupID'];
        $query = "SELECT userID FROM groupchatrecipients WHERE groupID = $finalGroupID";
        $usernames = array();

        $result = $mysqli->query($query);
        $count = 0;
        while($row = $result->fetch_assoc()){

            $recipientsID = $row["userID"];
            $query = "SELECT username FROM user WHERE userID = $recipientsID";
       
            $usernameResult = $mysqli->query($query);

            if($usernameResult){
                $usernameRow = $usernameResult->fetch_row();
                $usernames[$count] = $usernameRow[0];
                $count++;
            }



        }

        echo json_encode($usernames);
        $query = "INSERT INTO `groupchatrecipients`(`groupID`, `userID`) VALUES ($groupID, $userID)";

        $mysqli->query($query);


    } else {


        $query = "INSERT INTO `groupchat`(`groupID`, `userID`, `latitude`, `longitude`) VALUES (0,$userID, $lat, $long)";


        $mysqli->query($query);

        $query = "SELECT groupID FROM groupchat WHERE userID = $userID";

        $result = $mysqli->query($query);

        $row = $result->fetch_row();

        $groupID = $row[0];

        $query = "INSERT INTO `groupchatrecipients`(`groupID`, `userID`) VALUES ($groupID, $userID)";


        $mysqli->query($query);

        echo "not found";
    }
} else {
    echo "Not Set";
}

$mysqli->close();
 ?> 