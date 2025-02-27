<?php
$servidorBD = 'localhost';
$usuarioBD = 'root';
$passwordBD = '';
$nombreBD = 'clientes';
$dsn = "mysql:host=$servidorBD;dbname=$nombreBD;charset=utf8mb4";
try {
    $con = new PDO($dsn, $usuarioBD, $passwordBD);
    // unset($passwordBD); // modo paranoico
} catch (PDOException $ex) {
    exit('No se ha podido conectar con la BD:<br>' . $ex->getMessage());
}
?>