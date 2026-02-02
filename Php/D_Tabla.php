<!DOCTYPE html>
<html lang="es">
<head>
    <title>Apartado D - Tabla I</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="estilos/misestilos.css">
</head>
<body>
    <h1>Apartado D - Tabla I</h1>
    <p>Escribe un número</p>
    <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
        <label>Numero 1:</label>
        <input type="number" name="numero1" required> <br>
        <br>
        <br>
        <input type="submit">
    </form>

<?php

/*    echo "<br>";
    echo "<pre>";
    var_dump($_POST);
    echo "</pre>";
    echo "<br>";*/

    if(  isset( $_POST["numero1"]) &&
        is_numeric($_POST["numero1"])){
        $max = $_POST["numero1"];
        
        $num_columns = 4;
        $num = 1;
        echo "<p>Pintamos tabla de 4 columnas hasta $max </p>";
        echo "<table>";
        /* Hacemos doble bucle. Uno para filas. 
            Otro para columnas dentro de una fila*/
            $contador = 1;
            for($i = 0; $i < $max; $i++ ){
                echo "<tr>";
                for($j = 0; $j < 4; $j++ ){
                    echo "<td>".$contador++."</td>";
                    if($contador>$max) break;
                }
                echo "</tr>";
                if($contador>$max) break;
            }


        echo "</table>";

    }

?>

</body>
</html>