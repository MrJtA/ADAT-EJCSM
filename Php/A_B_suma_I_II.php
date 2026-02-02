<!DOCTYPE html>
<html lang="es">
<head>
    <title>Apartados A y B - Suma</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="estilos/misestilos.css">
</head>
<body>

<h1>Apartados A y B - Suma</h1>

<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">

    Numero 1: <input type="number" name="numero1" required> <br>
    Numero 2: <input type="number" name="numero2" required>

    <br>
<br/>
<input type="submit">
</form>



<?php

/*    echo "<br>";
    echo "<pre>";
    var_dump($_POST);
    echo "</pre>";
    echo "<br>";*/

    if(  isset( $_POST["numero1"]) &&
        isset( $_POST["numero2"]) &&
        is_numeric($_POST["numero1"]) && 
        is_numeric($_POST["numero2"])   ){
        $num1 = $_POST["numero1"];
        $num2 = $_POST["numero2"];

        $suma = $num1+$num2;
        
        echo "<br>El resultado de sumar $num1 y $num2 es $suma<br>";

    }








?>

</body>
</html>