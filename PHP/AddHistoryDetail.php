<?php
require_once("Config.php");

$conn = new PDO(DBCONNSTRING, DBUSER, DBPASS);

$statement = $conn->prepare("INSERT INTO sales_record_history_details (`id`, `record_id`, `name`, `price`, `quantity`, `entry_id`) VALUES (NULL, ?, ?, ?, ?, ?)");
$statement->execute(array($_REQUEST["record_id"], $_REQUEST["name"], $_REQUEST["price"], $_REQUEST["quantity"], $_REQUEST["entry_id"]));
