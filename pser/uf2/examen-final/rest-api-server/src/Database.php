<?php

class Database
{
    private static $instance = null;
    private $con;

    private $servidorBD = 'localhost';
    private $usuarioBD = 'root';
    private $passwordBD = '';
    private $nombreBD = 'clientes';

    private function __construct()
    {
        $dsn = "mysql:host={$this->servidorBD};dbname={$this->nombreBD};charset=utf8mb4";
        try {
            $this->con = new PDO($dsn, $this->usuarioBD, $this->passwordBD);
            $this->con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $ex) {
            throw new Exception('Error de conexión a la BD: ' . $ex->getMessage());
        }
    }

    public static function getInstance()
    {
        if (self::$instance === null) {
            self::$instance = new self();
        }
        return self::$instance;
    }

    public function getConnection(): PDO
    {
        return $this->con;
    }

    private function __clone() {}
    private function __wakeup() {}
}

