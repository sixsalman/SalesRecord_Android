<?php
require_once("Config.php");

$conn = new PDO(DBCONNSTRING, DBUSER, DBPASS);

$res = $conn->prepare("SELECT sales_record_history_entries.id, date_time, total, name, price, quantity FROM sales_record_history_entries INNER JOIN sales_record_history_details ON sales_record_history_entries.id = sales_record_history_details.entry_id WHERE record_id = ? ORDER BY sales_record_history_entries.id DESC, name ASC");
$res->execute(array($_REQUEST["record_id"]));

$entries = array();
$entry = array();
$last_row = -1;

if ($row = $res->fetch()) {
    $curr_id = $row["id"];
    array_push($entry, array("name"=>$row["name"], "price"=>$row["price"], "quantity"=>$row["quantity"]));
    $last_row = $row;
}

while ($row = $res->fetch()) {
    if ($row["id"] != $curr_id) {
        array_push($entries, array("date_time"=>$last_row["date_time"], "total"=>$last_row["total"], "transaction_items"=>$entry));
        
        $curr_id = $row["id"];
        $entry = array();
    }
    
    array_push($entry, array("name"=>$row["name"], "price"=>$row["price"], "quantity"=>$row["quantity"]));
    
    $last_row = $row;
}

if ($last_row != -1) {
    array_push($entries, array("date_time"=>$last_row["date_time"], "total"=>$last_row["total"], "transaction_items"=>$entry));
}

echo json_encode($entries);
