 <?php  
 require "init.php";
 $username = $_POST["username"];  
 $password = $_POST["password"];
 $email = $_POST["email"];
 $sql_query = "INSERT INTO `users`(`username`, `email`, `password`) VALUES ('$username', '$email', '$password')";
 $mysqli->query($sql_query);
 ?>  