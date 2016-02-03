<?php  
 require "init.php";  
 $username = $mysqli->real_escape_string($_POST["username"]);
 $password =  $mysqli->real_escape_string($_POST["password"]);
 $sql_query = "select username from users where username = '$username' and password = '$password'";  
 $result = $mysqli->query($sql_query);
 $row = $result->fetch_assoc($result);
 $name = $row["username"];
 
 if($name)
 {
	echo $name;
 }

$mysqli->close(); 
 ?> 