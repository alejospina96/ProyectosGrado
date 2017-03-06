<%-- 
    Document   : navbarMiembroComite
    Created on : 04-mar-2017, 1:15:32
    Author     : daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="includes.jsp"/>

    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">   
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="index.jsp">Inicio</a></li>
                        <li><a href="PrepararAgregarEstudiante">Registrar un estudiante</a></li>
                        <li><a href="PrepararRegistrarJurado">Registrar un jurado</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Propuestas<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="ListaPropuestas">Ver propuestas registradas</a></li>
                                <li><a href="ListaPropuestasVencer">Ver propuestas por vencer</a></li>
                                <li><a href="SeleccionEstadoPropuesta">Buscar propuestas por estado</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Trabajos de grado<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="ListaTrabajosVigentes">Ver trabajos vigentes</a></li>
                                <li><a href="ListaTrabajosVencer">Ver trabajos por vencer</a></li>
                                <li><a href="SeleccionJurado">Buscar propuestas por jurado</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </body>
</html>
