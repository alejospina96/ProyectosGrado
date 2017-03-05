<%-- 
    Document   : navbarEstudiante
    Created on : 04-mar-2017, 0:17:37
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
        <header>
            <nav class="navbar navbar-default navbar-custom navbar-fixed-top">
                <div class="container-fluid">
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">                   
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="index.jsp">Inicio</a></li>
                            
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Propuestas<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="PrepararRegistrarPropuesta">Registrar una propuesta</a></li>
                                    <li><a href="#">Presentar correcci&oacute;n</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Trabajos de grado<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="PrepararRegistrarTrabajoGrado">Entregar trabajo</a></li>
                                    <li><a href="#">Solicitar prorroga</a></li>
                                    <li><a href="#">Presentar correcci&oacute;n</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </header>
    </body>
</html>
