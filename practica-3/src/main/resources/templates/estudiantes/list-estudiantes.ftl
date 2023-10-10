<!DOCTYPE HTML>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

    <title>Estudiantes</title>
</head>

<body>

<div class="container">

    <h3>Estudiantes</h3>
    <hr>

    <!-- Add button -->
    <a href="/estudiantes/add"
       class="btn btn-primary btn-sm mb-3">
        Nuevo Estudiante
    </a>


    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th>Matricula</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Telefono</th>
            <th>Accion</th>
        </tr>
        </thead>

        <tbody>
        <#list estudiantes as tempEstudiante>
            <tr>

                <td>${tempEstudiante.getMatricula()}</td>
                <td>${tempEstudiante.getNombre()}</td>
                <td>${tempEstudiante.getApellido()}</td>
                <td>${tempEstudiante.getTelefono()}</td>

                <td>
                    <!--  Update button/link -->
                    <a href="/estudiantes/add?id=${tempEstudiante.getMatricula()}"
                       class="btn btn-info btn-sm">
                        Editar
                    </a>
                    <!-- Delete button/link -->
                    <a href="/estudiantes/delete?id=${tempEstudiante.getMatricula()}"
                       class="btn btn-danger btn-sm"
                       onClick="if (!(confirm('Esta seguro que desea borrar este estudiante?'))) return false">
                        Borrar
                    </a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <#if msg??>
        <h2>${msg}</h2>
    </#if>
</div>
</body>
</html>