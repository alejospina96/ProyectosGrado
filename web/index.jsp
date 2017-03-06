<%-- 
    Document   : index
    Created on : 28-feb-2017, 7:56:26
    Author     : daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Propuestas de grado</title>
        <jsp:include page="includes.jsp"/>
        <link rel=stylesheet href="css/main_modernizr.css">
        <!--[if lte IE 8]>
        <link rel=stylesheet href="css/ie.css">
        <![endif]-->
        <script src="js/vendor/modernizr.js"></script>

        <script src="js/vendor/respond.min.js"></script>

    </head>
    <body>        
        <div class="level level-hero hero-home has-hint">
            <div class="container full-height">
                <div class=v-align-parent>
                    <div class=v-center>
                        <div class="row">
                            <div class="col-xs-10 col-sm-6">
                                <h1 class="mb-10 heading">Propuestas y <span>Proyectos de grado.</span></h1>
                                <br/>
                                <div class="subheading mb-20">Soy un... </div>
                            </div>
                        </div>
                        <div>
                            <a class="btn btn-prepend" href="estudiante.jsp">
                                <i class="glyphicon glyphicon-user"></i>&nbsp;&nbsp;&nbsp;Estudiante
                            </a>
                            <a class="btn btn-prepend" href="miembroComite.jsp">
                                <i class="glyphicon glyphicon-user"></i>&nbsp;&nbsp;&nbsp;Miembro del comite
                            </a>    
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
