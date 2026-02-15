<?php

function JSONCorrecto($mensajeRecibido) {
    
    if (!isset($mensajeRecibido["accion"])) {
        return false;
    }

    $accion = $mensajeRecibido["accion"];

    switch ($accion) {
        
        case "insertar":
        case "modificar":
            return 
                isset($mensajeRecibido["isbn"]) && 
                isset($mensajeRecibido["titulo"]) &&
                isset($mensajeRecibido["autor"]) &&
                isset($mensajeRecibido["editorial"]) &&
                isset($mensajeRecibido["genero"]);

        case "borrar":
            return isset($mensajeRecibido["isbn"]);

        case "leer":
        case "restablecer":
            return true;

        default:
            return false;
    }
}

?>