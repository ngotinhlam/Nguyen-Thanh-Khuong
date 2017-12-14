<?php
    require './database.php';
    
    $db = new database();
    $sql = "SELECT * FROM banner";
    $db->query($sql);
    $data = [];
    while ($row = $db->fetch()) {
        $data[] = $row;
    }
    echo json_encode($data);
?>

