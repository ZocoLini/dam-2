<?php

class Router {
    private $routes = [];

    public function add($method, $path, $callback) {
        $path = trim($path, '/'); // Eliminar barras extra
        $this->routes[strtoupper($method)][$path] = $callback;
    }

    public function dispatch() {
        $method = $_SERVER['REQUEST_METHOD'];
        $uri = trim(parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH), '/');
        
        foreach ($this->routes[$method] ?? [] as $route => $callback) {
            $pattern = preg_replace('/\{([^\/]+)}/', '([^/]+)', $route);
            if (preg_match("#^telefonos/telefonos.php/$pattern$#", $uri, $matches)) {
                array_shift($matches); // Eliminar la URL completa del array de coincidencias
                return call_user_func_array($callback, $matches);
            }
        }

        // Si no se encuentra la ruta
        header('Content-Type: application/json');
        http_response_code(404);
        echo json_encode(["error" => "Ruta no encontrada", "a" => "b"]);
    }
}