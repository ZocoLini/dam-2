<?php
// require 'conexionBD.php';

require 'Database.php';
require 'Router.php';

// Instancia del enrutador
$router = new Router();

// Definir rutas
$router->add('GET', '/', function() {
    if (!isset($_GET['nombre'])) {
        http_response_code(400);
        header('Content-Type: application/json');
        echo json_encode(["error" => "Campo obligatorio faltante"]);
    } else {
        echo "Hola, " . $_GET['nombre'] . ".";
    }
});

$router->add("PUT", "usuarios/{id}", function ($id) {
    parse_str(file_get_contents('php://input',true), $datos);

    response_json($datos);
});

$router->add('GET', 'usuarios/{id}', function ($id)
{
    $db = Database::getInstance();
    $con = $db->getConnection();
    $sql = 'SELECT * FROM clientes where id = ?';
    $stmt = $con->prepare($sql);
    $stmt->bindValue(1, $id);
    $stmt->execute();
    http_response_code(200);
    header('Content-Type: application/json');
    echo json_encode($stmt->fetch(PDO::FETCH_OBJ), JSON_UNESCAPED_UNICODE);
});

$router->dispatch();

function response_json($data)
{
    http_response_code(200);
    header('Content-Type: application/json');
    echo json_encode($data, JSON_UNESCAPED_UNICODE);
}

/*
$db = Database::getInstance();
$con = $db->getConnection();

$verbo = $_SERVER['REQUEST_METHOD'];
$uri = $_SERVER['PATH_INFO'] ?? 'test';

$pathInfo = isset($_SERVER['PATH_INFO']) ? trim($_SERVER['PATH_INFO'], '/') : '';
$rutas = $pathInfo == '' ? [] : explode('/', $pathInfo);

// REGEX de ejemplo:
//$rutas = preg_split('/\//', $pathInfo, -1, PREG_SPLIT_NO_EMPTY);

$email = "usuario@ejemplo.com";

// Expresión regular con dos grupos: (usuario)@(dominio)
$pattern = "/^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/";

if (preg_match($pattern, $email, $matches)) {
    echo "Usuario: " . $matches[1] . PHP_EOL;  // usuario
    echo "Dominio: " . $matches[2] . PHP_EOL;  // ejemplo.com
} else {
    echo "Formato de email inválido.";
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
*/
