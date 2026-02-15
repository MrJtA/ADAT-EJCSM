<?php
$servername = "localhost";
$user = "root";
$password = "root";
$dbname = "biblioteca";
$conn = new mysqli($servername, $user, $password, $dbname);
$conn->set_charset("utf8");
if ($conn->connect_error) {
    header('Content-Type: application/json');
    die(json_encode([
        "estado" => "error",
        "mensaje" => "Error de conexión: " . $conn->connect_error
    ]));
}
?>