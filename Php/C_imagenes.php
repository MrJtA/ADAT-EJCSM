<?php

$personajes = array(
    "ironman"  => "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Iron_Man_Repulsors_%2814041559344%29.jpg/245px-Iron_Man_Repulsors_%2814041559344%29.jpg",
    "hulk" => "https://m.media-amazon.com/images/I/71XboxdV4RL._AC_SL1500_.jpg",
    "capitan" => "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Captain_America_cosplay_o.jpg/220px-Captain_America_cosplay_o.jpg"    
)

?>


<!DOCTYPE html>
<html lang="es">
<head>
    <title>Apartado C - Imagenes</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="estilos/misestilos.css">
</head>
<body>

<h1>Apartado C - Imagenes</h1>
<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">

    Personaje: 
    <select name="personajeId">

<?php
 
    echo "<option value=''>  </option>";

    foreach($personajes as $key => $valor){

        echo "<option value='$key'";

        if(isset($_POST["personajeId"]) && 
            $key == $_POST["personajeId"] ){

            echo "selected";

        }
        
        echo "> $key </option>";

    }

?>


    </select>


    <br>
<br/>
<input type="submit">
</form>



<?php
/*
    echo "<br>";
    echo "<pre>";
    var_dump($_POST);
    echo "</pre>";
    echo "<br>";*/

    if(  isset( $_POST["personajeId"])){

        $key = $_POST["personajeId"];

        $url = $personajes[$key];

        echo "<img src='$url' alt='foto de $key'";

    }   
    






?>

</body>
</html>