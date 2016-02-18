<?php
    /**
     * Created by PhpStorm.
     * User: RYAN
     * Date: 2/16/2016
     * Time: 7:13 PM
     */
    $mysqli = new mysqli("nekanaya.hccis.info","nekanaya_CAadmin","opensesame","nekanaya_chat_away");
session_start();
if(isset($_POST["user"])&&isset($_POST["pass"])){

    $query = "SELECT * FROM user WHERE accountLevel = 'admin'";

    $result = $mysqli->query($query);


    while($row = $result->fetch_assoc()){
        echo $row["username"];

        $userName = $row["username"];
        $password = $row["password"];

        if($userName == $_POST["user"] && $password == sha1($_POST["pass"])){
            $_SESSION["login"] = true;
            header("Location: chataway_admin_userreport.php");
        }
        else{
            echo "login Failed";
        }

    }
}
else{
    echo "enter info";
}

?>
<form method="post" action="chatAway_admin_login.php">
    <input type="text" name="user"/>
    <input type="password" name="pass"/>
    <input type="submit" id="submit" value="submit"/>

</form>
