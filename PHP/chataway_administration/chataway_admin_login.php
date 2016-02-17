<?php
    /**
     * Created by PhpStorm.
     * User: RYAN
     * Date: 2/16/2016
     * Time: 7:13 PM
     */
    $mysqli = new mysqli("localhost","nekanaya_CAadmin","opensesame","nekanaya_chat_away");

?>
<form method="post" action="chatAway_admin_login.php">
    <input type="text" id="user"/>
    <input type="password" id="pass"/>

</form>
