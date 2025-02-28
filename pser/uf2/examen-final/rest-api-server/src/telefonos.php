<?php
// require 'conexionBD.php';

require 'Database.php';
require 'Router.php';

// Instancia del enrutador
$router = new Router();

// Definir rutas
// GETTERS de las tablas 
$router->add('GET', "operadores", function () {
    $where = "";
    
    response_query("select codOperador, nombre from operadores where 1" . $where);
});

$router->add('GET', "telefonos", function () {
    $where = "";
    
    if (isset($_GET['titular']))
    {
        $where = $where . " and titular = '" . $_GET['titular'] . "'";
    }

    if (isset($_GET['codOperador']))
    {
        $where = $where . " and codOperador = " . $_GET['codOperador'];
    }
    
    response_query("select telefono, codOperador, titular from telefonos where 1" . $where);
});

$router->add("GET", "traspasos", function () {
    $where = "";
    
    if (isset($_GET['telefono']))
    {
        $where = $where . " and telefono = " . $_GET['telefono'];
    }
    
    response_query("select id, telefono, viejaOperadora, nuevaOperadora, motivo from traspasos where 1" . $where);
});

// POSTS n las tablas
$router->add("POST", "telefonos", function () {
    if (!isset($_POST['telefono']) || !isset($_POST['codOperador']) || !isset($_POST['titular']) )
    {
        http_response_code(400);
        return;
    }

    $db = Database::getInstance();
    $con = $db->getConnection();
    $sql = 'insert into telefonos (telefono, codOperador, titular) values (?, ?, ?)';
    $stmt = $con->prepare($sql);
    $stmt->bindValue(1, $_POST['telefono']);
    $stmt->bindValue(2, $_POST['codOperador']);
    $stmt->bindValue(3, $_POST['titular']);
    try {
        http_response_code($stmt->execute() ? 200 : 400);
    } catch (PDOException $e)
    {
        http_response_code(400);
    }
});

$router->add("PUT", "traspasos", function () {
    parse_str(file_get_contents('php://input',true), $datos);
    
    if (!isset($datos['telefono']) || !isset($datos['nuevaOperadora']) || !isset($datos['motivo']) )
    {
        echo "here";
        http_response_code(400);
        return;
    }

    $db = Database::getInstance();
    $con = $db->getConnection();
    
    $sql = "select t.codOperador from telefonos t where t.telefono = " . $datos['telefono'];
    $cursor = $con->query($sql);
    $res = $cursor->fetch();
    $viejaOperadora = $res['codOperador'];
    
    $sql = "update telefonos t set t.codOperador = ? where t.telefono = ?";
    $stmt = $con->prepare($sql);
    $stmt->bindValue(1, $datos['nuevaOperadora']);
    $stmt->bindValue(2, $datos['telefono']);
    
    try {
        if (!exec($stmt->execute()))
        {
            http_response_code(400);
        }
    } catch (PDOException $e)
    {
        http_response_code(400);
    }
    
    $sql = 'insert into traspasos (telefono, viejaOperadora, nuevaOperadora, motivo) VALUES (?, ?, ?, ?)';
    $stmt = $con->prepare($sql);
    $stmt->bindValue(1, $datos['telefono']);
    $stmt->bindValue(2, $viejaOperadora);
    $stmt->bindValue(3, $datos['nuevaOperadora']);
    $stmt->bindValue(4,$datos['motivo']);
    
    try {
        http_response_code($stmt->execute() ? 200 : 400);
    } catch (PDOException $e)
    {
        http_response_code(400);
    }
});

$router->dispatch();

function response_json($data)
{
    http_response_code(200);
    header('Content-Type: application/json');
    echo json_encode($data, JSON_UNESCAPED_UNICODE);
}

function response_query($sql)
{
    $db = Database::getInstance();
    $con = $db->getConnection();
    $cursor = $con->query($sql);
    response_json($cursor->fetchAll(PDO::FETCH_OBJ));
}