<?php

    $distance = 0.0005;

$mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");

if(isset($_POST["latitude"])&&isset($_POST["longitude"])&&isset($_POST["userId"])){

    $lat = $mysqli->real_escape_string($_POST["latitude"]);
    $long = $mysqli->real_escape_string($_POST["longitude"]);

    $query = "SELECT * FROM user WHERE lat<='".$lat-$distance."' AND lat>='".$lat+$distance."'' AND long<='".$long-$distance."' AND long>='".$long+$distance."' AND requestingChat='1' AND banStatus='0'";

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

    $finalUser = $users[rand(0, count($user)-1)];

    if(isset($finalUser['userID'])){

        echo json_encode($finalUser);
        $query = "UPDATE users SET requestingChat = 0 WHERE userId = ".$finalUser['userID'];

        $result = $mysqli->query($query);

        echo $result;

        $query = "UPDATE users SET requestingChat = 0 WHERE userId = ".$_POST['userID'];

        $result = $mysqli->query($query);

        echo $result;

    } else {
        echo "not found";
        $query = "UPDATE users SET requestingChat = 1 WHERE userId = ".$_POST['userID'];

        $result = $mysqli->query($query);

        echo $result;
    }
} else {
    echo "Not Set";
}

$mysqli->close();
?>