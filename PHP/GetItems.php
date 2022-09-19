<?php
require_once("Config.php");

$conn = new PDO(DBCONNSTRING, DBUSER, DBPASS);

$res = $conn->prepare("SELECT name, price FROM sales_record_items WHERE record_id = ? ORDER BY name");
$res->execute(array($_REQUEST["record_id"]));

$entries = array();

while ($row = $res->fetch()) {
	array_push($entries, array("name"=>$row["name"], "price"=>$row["price"]));
}

echo json_encode($entries);