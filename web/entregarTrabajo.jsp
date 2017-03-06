<%-- 
    Document   : entregarTrabajo
    Created on : 05-mar-2017, 13:10:17
    Author     : daniel
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entregar trabajo</title>
    </head>
    <body>
        <jsp:include page="navbarEstudiante.jsp"/>
        <form id="formEntregarTrabajo" class="form-horizontal form-center" action="EntregarTrabajoGrado" method="POST">
            <h1 align="center">Entregar trabajo de grado</h1>
            <br/>
            <fieldset>
                <legend>Datos de tu propuesta</legend>
                <!--Input tematica-->
                <div class="form-group row">
                    <label for="txtTrabajo" class="col-sm-3 control-label">Trabajo de grado:&nbsp;</label>
                    <div class="col-sm-9 input-div" >
                        <input class="form-control" type="text" name="txtTrabajo" id="txtTrabajo" placeholder="Ingresa el codigo de trabajo de grado que llego a tu email" required="required"/>
                    </div>
                </div>
                <!--Load archivo-->
                <div class="form-group row">
                    <label class="control-label col-sm-3">Sube tu archivo:&nbsp;</label>
                    <div class="col-sm-5 input-div" >
                        <input id="filePropuesta" name="fileTrabajo" type="file" class="filestyle" data-buttonText="Examinar" onchange="manejarArchivo(this.files)" required="required"/>
                    </div>
                </div>
                <!-- Drop Zone -->
                <div class="form-group row">
                    <h4>... O arrastra y suelta tu archivo debajo</h4>
                    <div class="upload-drop-zone" id="drop-zone">
                        Just drag and drop files here
                    </div>
                </div>
                <div class="progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                        <span class="sr-only">60% Complete</span>
                    </div>
                </div>
                <!--JSP para el error-->
                <%
                    Integer agrego = (Integer) request.getAttribute("agrego");
                    if (agrego != null) {
                        if (agrego.intValue() == 1) {
                %>
                <div class="alert alert-success center-block fade in alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                    Trabajo entregado
                </div>
                <%
                } else {
                %>
                <div class="alert alert-danger center-block fade in alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                    Error en la entrega del trabajo
                </div>
                <%
                    }
                } else {
                %>
                <div >
                    <br/>
                </div>
                <%
                    }
                %>
                <!--Boton submit-->
                <div class="form-group row">
                    <input type="submit" class="btn btn-default btn-block" id="btnEnviar" value="Enviar"/>
                </div>
            </fieldset>
        </form>
    </body>
</html>
