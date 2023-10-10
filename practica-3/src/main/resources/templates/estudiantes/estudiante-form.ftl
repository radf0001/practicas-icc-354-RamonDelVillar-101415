<!DOCTYPE HTML>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

    <title>Estudiante</title>
</head>

<body>
<div class="container">
    <h3>Estudiante</h3>
    <hr>

    <p class="h4 mb-4">Estudiante</p>

    <#if datos??>
        <form action="/estudiantes/add?id=${datos.getMatricula()}" method="POST">

           <input disabled name="matricula" type="number" class="form-control mb-4 col-4" placeholder="Matricula" value="${datos.getMatricula()}" required>

            <input name="nombre" type="text" class="form-control mb-4 col-4" placeholder="Nombre" value="${datos.getNombre()}" required>

            <input name="apellido" type="text" class="form-control mb-4 col-4" placeholder="Apellido" value="${datos.getApellido()}" required>

            <input name="telefono" type="text" class="form-control mb-4 col-4" placeholder="Telefono" value="${datos.getTelefono()}" required>

            <button type="submit" class="btn btn-info col-2">Guardar</button>
        </form>
    <#else>
        <form action="/estudiantes/add" method="POST">

            <input name="matricula" type="number" class="form-control mb-4 col-4" placeholder="Matricula" required>

            <input name="nombre" type="text" class="form-control mb-4 col-4" placeholder="Nombre" required>

            <input name="apellido" type="text" class="form-control mb-4 col-4" placeholder="Apellido" required>

            <input name="telefono" type="text" class="form-control mb-4 col-4" placeholder="Telefono" required>

            <button type="submit" class="btn btn-info col-2">Guardar</button>
        </form>
    </#if>
    <hr>
    <a href="/estudiantes/list">Volver a lista</a>

    <#if msg??>
        <h2>${msg}</h2>
    </#if>

</div>
</body>
</html>