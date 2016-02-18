<?php
/**
 * Created by PhpStorm.
 * User: mhutchinson110082
 * Date: 2/17/2016
 * Time: 4:33 PM
 */
session_start();
if(!$_SESSION["login"]){
    header("Location: chataway_admin_login.php");
}
?>