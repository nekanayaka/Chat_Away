<?php
/**
 * Created by PhpStorm.
 * User: mhutchinson110082
 * Date: 2/18/2016
 * Time: 2:58 PM
 */
require("chataway_admin_auth.php");
$mysqli = new mysqli("nekanaya.hccis.info","nekanaya_CAadmin","opensesame","nekanaya_chat_away");
$year = date("Y");
$month = date("m") -1;
$day = date("d");
$query="SELECT * FROM user WHERE lastLogin <= '$year-$month-$day'";
$result = $mysqli->query($query);
echo "<h1>User Exception Report</h1>";
echo "<table><th>User Name</th><th>Email</th><th>Last Login</th>";
while($row = $result->fetch_assoc()){
    echo "<tr>";
    $userName = $row["username"];
    $email = $row["email"];
    $lastLogin = $row["lastLogin"];
    echo"<td>$userName</td><td>$email</td><td>$lastLogin</td></tr>";
}
?>


