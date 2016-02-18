<?php  
	
	 
	 $mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");
 
	 if(isset($_POST["username"]) && isset($_POST["password"])){
		 
		 $username = $mysqli->real_escape_string($_POST["username"]);
		 $password =  $mysqli->real_escape_string($_POST["password"]);
		 $query = "SELECT * FROM user WHERE username='".$username."' AND password=sha1('".$password."')";
		 
		 $result = $mysqli->query($query);

		 $user = array();

		 while($row = $result->fetch_assoc()){

			 $user['userID'] = $row["userID"];
			 $user['username'] = $row["username"];
			 $user['password'] = $row["password"];
			 $user['email'] = $row["email"];
			 $user['banStatus'] = $row["banStatus"];
			 $user['accountLevel'] = $row["accountLevel"];
			 $user['longitude'] = $row["longitude"];
			 $user['latitude'] = $row["latitude"];
		 }

		 if(isset($user['userID'])){

			 $userID = $user['userID'];
			 echo json_encode($user);
			 $today = date("Y-m-d");
			 
			 $query = "UPDATE user SET lastLogin = '$today' WHERE userID = '$userID'";

			 $result = $mysqli->query($query);

		 } else {
			echo "not found";
		 }
	 } else {
		 echo "Not Set";
	 }
	 
	 
	
		$mysqli->close(); 
 ?> 