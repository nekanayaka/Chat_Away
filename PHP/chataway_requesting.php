<?php

  

$mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");

if(isset($_POST["userID"])){
 
	$userID = $mysqli->real_escape_string($_POST["userID"]);
	


    $query = "UPDATE user SET requestingChat = 0 WHERE userID = '$userID'";

    $result = $mysqli->query($query);

	$mysqli->close();
	
?>