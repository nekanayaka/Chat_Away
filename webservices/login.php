<?php  
 require "init.php";  
 $username = $_POST["username"];  
 $password =  $_POST["password"];  
 $sql_query = "select username from users where username = '$username' and password = '$password'";  
 $result = mysqli_query($con,$sql_query);  
 if(mysqli_num_rows($result) > 0)  
 {  
	$row = mysqli_fetch_assoc($result);  
	$name =	$row["username"];  
	echo "Login Success..Welcome ".$name;  
 }  
 else  
 {   
	echo "Wrong Username or Password!";  
 }  
 ?> 