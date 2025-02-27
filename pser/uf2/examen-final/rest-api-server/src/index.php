<?php
require 'conexionBD.php';

$verbo = $_SERVER['REQUEST_METHOD'];
$uri = $_SERVER['PATH_INFO'] ?? 'test';

$pathInfo = isset($_SERVER['PATH_INFO']) ? trim($_SERVER['PATH_INFO'], '/') : '';
$rutas = $pathInfo == '' ? [] : explode('/', $pathInfo);
function devolverClientes($con) {
    $sql = 'SELECT * FROM clientes';
    $cursor = $con->query($sql);
    $clientes = $cursor->fetchAll(PDO::FETCH_OBJ);
    return $clientes;
}

function devolverProvincias($con) {
    $sql = 'SELECT * FROM provincias';
    $cursor = $con->query($sql);
    $provincias = $cursor->fetchAll(PDO::FETCH_OBJ);
    return $provincias;
}

function insertarCliente($con,$nombre,$apellidos,$codProvincia,$vip) {
    //$sql = 'SELECT * FROM clientes c INNER JOIN PROVINCIAS p ON c.codProvincia = p.codProvincia WHERE p.nomProvincia = ?';
    $sql = 'INSERT INTO clientes (nombre,apellidos,codProvincia,vip) VALUES (?,?,?,?)';
    $stmt = $con->prepare($sql);
    $stmt->bindValue(1,$nombre);
    $stmt->bindValue(2,$apellidos);
    $stmt->bindValue(3,$codProvincia);
    $stmt->bindValue(4,$vip);
    $stmt->execute();
}

switch (count($rutas)) {
    case 0:
        echo json_encode(["error" => "No hay datos."]);
        http_response_code(400);
        break;
    case 1:
        if ($rutas[0] == 'clientes' && $verbo == 'GET') {
            $clientes = devolverClientes($con); // FIXED FUNCTION CALL
            http_response_code(200);
            header('Content-Type: application/json');
            echo json_encode($clientes,JSON_UNESCAPED_UNICODE);
        } elseif
                ($rutas[0] == 'provincias' && $verbo == 'GET') {
                $provincias = devolverProvincias($con); // FIXED FUNCTION CALL
                http_response_code(200);
                header('Content-Type: application/json');
                echo json_encode($provincias, JSON_UNESCAPED_UNICODE);
        } elseif
            ($rutas[0] == 'insertarCliente' && $verbo == 'POST') {

            $nombre = $_POST["nombre"];
            $apellidos = $_POST["apellidos"];
            $codProvincia = $_POST["codProvincia"];
            $vip = $_POST["vip"];

            insertarCliente($con,$nombre,$apellidos,$codProvincia,$vip);
            http_response_code(200);
            header('Content-Type: application/json');
            echo json_encode(["success" => "Cliente registrado"]);
        }
        break;
    default:
        echo json_encode(["error" => "Ruta no válida."]);
        http_response_code(404);
        break;
}
?>