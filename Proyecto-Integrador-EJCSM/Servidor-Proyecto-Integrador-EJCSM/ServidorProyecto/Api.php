<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);
header("Content-Type: application/json");

require 'bbdd.php';
require 'jsonEsperado.php';

$arrMensaje = array();
$parameters = file_get_contents("php://input");

if (isset($parameters) && $parameters != "") {
    $mensajeRecibido = json_decode($parameters, true);

    if (JSONCorrecto($mensajeRecibido)) {
        $accion = $mensajeRecibido["accion"];

        switch ($accion) {
            case "leer":
                $query = "SELECT * FROM libro";
                $result = $conn->query($query);
                if ($result) {
                    $arrLibros = array();
                    while ($row = $result->fetch_assoc()) {
                        $arrLibros[] = $row;
                    }
                    $arrMensaje["estado"] = "ok";
                    $arrMensaje["libros"] = $arrLibros;
                } else {
                    $arrMensaje["estado"] = "error";
                    $arrMensaje["mensaje"] = "Error al leer libros";
                }
                break;

            case "insertar":
                $isbn = $mensajeRecibido["isbn"];
                $titulo = $mensajeRecibido["titulo"];
                $autor = $mensajeRecibido["autor"];
                $editorial = $mensajeRecibido["editorial"];
                $genero = $mensajeRecibido["genero"];
                $stmt = $conn->prepare("INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES (?, ?, ?, ?, ?)");
                $stmt->bind_param("issss", $isbn, $titulo, $autor, $editorial, $genero);
                
                if ($stmt->execute()) {
                    $arrMensaje["estado"] = "ok";
                    $arrMensaje["mensaje"] = "Libro insertado correctamente";
                } else {
                    $arrMensaje["estado"] = "error";
                    $arrMensaje["mensaje"] = "Error al insertar: " . $conn->error;
                }
                break;

            case "modificar":
                $isbn = $mensajeRecibido["isbn"];
                $titulo = $mensajeRecibido["titulo"];
                $autor = $mensajeRecibido["autor"];
                $editorial = $mensajeRecibido["editorial"];
                $genero = $mensajeRecibido["genero"];

                $stmt = $conn->prepare("UPDATE libro SET titulo=?, autor=?, editorial=?, genero=? WHERE isbn=?");
                $stmt->bind_param("ssssi", $titulo, $autor, $editorial, $genero, $isbn);
                
                if ($stmt->execute()) {
                    $arrMensaje["estado"] = "ok";
                    $arrMensaje["mensaje"] = "Libro modificado correctamente";
                } else {
                    $arrMensaje["estado"] = "error";
                    $arrMensaje["mensaje"] = "Error al modificar: " . $conn->error;
                }
                break;

            case "borrar":
                $isbn = $mensajeRecibido["isbn"];
                $stmt = $conn->prepare("DELETE FROM libro WHERE isbn=?");
                $stmt->bind_param("i", $isbn);
                
                if ($stmt->execute()) {
                    $arrMensaje["estado"] = "ok";
                    $arrMensaje["mensaje"] = "Libro eliminado correctamente";
                } else {
                    $arrMensaje["estado"] = "error";
                    $arrMensaje["mensaje"] = "Error al eliminar: " . $conn->error;
                }
                break;

            case "restablecer":
                if ($conn->query("DELETE FROM libro")) {
                    $arrMensaje["estado"] = "ok";
                    $arrMensaje["mensaje"] = "Biblioteca vaciada correctamente";
                } else {
                    $arrMensaje["estado"] = "error";
                    $arrMensaje["mensaje"] = "Error al restablecer: " . $conn->error;
                }
                break;

            default:
                $arrMensaje["estado"] = "error";
                $arrMensaje["mensaje"] = "ACCIÓN NO VÁLIDA";
        }
    } else {
        $arrMensaje["estado"] = "error";
        $arrMensaje["mensaje"] = "JSON INCORRECTO O CAMPOS INSUFICIENTES";
    }
} else {
    $arrMensaje["estado"] = "error";
    $arrMensaje["mensaje"] = "NO SE HA RECIBIDO JSON";
}

echo json_encode($arrMensaje, JSON_PRETTY_PRINT);
$conn->close();
?>