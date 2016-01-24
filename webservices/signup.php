 <?php  
 require "init.php";
 $username = $mysqli->real_escape_string($_POST["username"]);
 $password = $mysqli->real_escape_string($_POST["password"]);
 $email = $mysqli->real_escape_string($_POST["email"]);
 $sql_query = "INSERT INTO `users`(`username`, `email`, `password`) VALUES ('$username', '$email', '$password')";
 $mysqli->query($sql_query);
 ?>  