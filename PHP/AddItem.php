<?php
require_once("Config.php");

$conn = new PDO(DBCONNSTRING, DBUSER, DBPASS);

$statement = $conn->prepare("INSERT INTO sales_record_items (`id`, `record_id`, `name`, `price`) VALUES (NULL, ?, ?, ?)");
$statement->execute(array($_REQUEST["record_id"], $_REQUEST["name"], $_REQUEST["price"]));
