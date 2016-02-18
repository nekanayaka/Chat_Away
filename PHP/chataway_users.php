<?php

    $distance = 0.0005;

$mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");

if(isset($_POST["latitude"])&&isset($_POST["longitude"])&&isset($_POST["userID"])){

    $lat = $mysqli->real_escape_string($_POST["latitude"]);
    $long = $mysqli->real_escape_string($_POST["longitude"]);
	$userID = $mysqli->real_escape_string($_POST["userID"]);
	
	$maxLat = $lat+$distance;
	$minLat = $lat-$distance;
	
	$maxLong = $long+$distance;
	$minLong = $long-$distance;

    $query = "SELECT * FROM user WHERE latitude<= '$maxLat' AND latitude>='$minLat' AND longitude<='$maxLong' AND longitude>='$minLong' AND requestingChat='1' AND banStatus='0' AND userID != '$userID'";

	
	
    $result = $mysqli->query($query);

    $user = array(array());
    $counter = 0;
    while($row = $result->fetch_assoc()){

        $user[$counter]['userID'] = $row["userID"];
        $user[$counter]['username'] = $row["username"];
        //$user[$counter]['password'] = $row["password"];
        $user[$counter]['email'] = $row["email"];
        $user[$counter]['banStatus'] = $row["banStatus"];
        $user[$counter]['accountLevel'] = $row["accountLevel"];
        $user[$counter]['latitude'] = $row["latitude"];
        $user[$counter]['longitude'] = $row["longitude"];
        $counter++;
    }

    $finalUser = $user[rand(0, count($user)-1)];

    if(isset($finalUser['userID'])){

		$finalUserID = $finalUser['userID'];
        echo json_encode($finalUser);
        $query = "UPDATE user SET requestingChat = 0 WHERE userID = '$finalUserID'";

        $result = $mysqli->query($query);

        $query = "UPDATE user SET requestingChat = 0, longitude= $long, latitude= $lat WHERE userID = '$userID'";

        $result = $mysqli->query($query);

        

    } else {
        
		
        $query = "UPDATE user SET requestingChat = '1' WHERE userID = '$userID'";

        $result = $mysqli->query($query);

        echo "not found";
    }
} else {
    echo "Not Set";
}

$mysqli->close();
?>