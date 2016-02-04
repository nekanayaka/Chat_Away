 <?php  
	
	 
	$mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");
	 
	
	if(isset($_POST["username"])&&isset($_POST["password"])&&isset($_POST["email"])&&isset($_POST["coordinates"])&&isset($_POST["accountLevel"])){
		
		$username = $mysqli->real_escape_string($_POST["username"]);
		$password = $mysqli->real_escape_string($_POST["password"]);
		$email = $mysqli->real_escape_string($_POST["email"]);
		$coordinates = $mysqli->real_escape_string($_POST["coordinates"]);
		$accountLevel = $mysqli->real_escape_string($_POST["userStatus"]);
		$sql_query = "INSERT INTO `user`(`username`, `email`, `password`, `coordinates`, `accountLevel`) VALUES ('$username', '$email', '$password', '$coordinates', '$accountLevel')";
		$mysqli->query($sql_query);
	 
	
	}
	 $mysqli->close();
 ?>  