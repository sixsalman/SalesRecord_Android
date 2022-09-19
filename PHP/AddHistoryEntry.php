<?php
require_once("Config.php");

$conn = new PDO(DBCONNSTRING, DBUSER, DBPASS);

$statement = $conn->prepare("INSERT INTO sales_record_history_entries (`id`, `date_time`, `total`) VALUES (NULL, ?, ?)");
$statement->execute(array($_REQUEST["date_time"], $_REQUEST["total"]));

echo $conn->lastInsertId();
