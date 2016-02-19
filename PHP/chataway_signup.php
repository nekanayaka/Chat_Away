<?php


$mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");


if(isset($_POST["username"])&&isset($_POST["password"])&&isset($_POST["email"])&&isset($_POST["accountLevel"])) {

    $username = $mysqli->real_escape_string($_POST["username"]);
    $password = $mysqli->real_escape_string($_POST["password"]);
    $email = $mysqli->real_escape_string($_POST["email"]);
    $accountLevel = $mysqli->real_escape_string($_POST["accountLevel"]);
    $longitude = $mysqli->real_escape_string($_POST["longitude"]);
    $latitude = $mysqli->real_escape_string($_POST["latitude"]);

    $emailPattern = "/^[a-zA-Z0-9_\-.!$&]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9]{2,}$/";

    $validEmail = preg_match($emailPattern, $email);

    $today = date("Y-m-d");
    $sql_query = "SELECT * FROM user WHERE email = '$email' OR username = '$username'";
    $result = $mysqli->query($sql_query);

    if ($result->num_rows==0 && $validEmail) {
        $sql_query = "INSERT INTO `user`(`username`, `email`, `password`, `accountLevel`, `banStatus`, `lastLogin`,`requestingChat`,`longitude`, `latitude`) VALUES ('$username', '$email', '$password', '$accountLevel', 0, '$today',0, '$longitude', '$latitude')";
        $mysqli->query($sql_query);
        echo "Success";
    } else {
        echo "fail";
    }

}
$mysqli->close();
?>