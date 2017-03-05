<%-- 
    Document   : agregarEstudiante
    Created on : 04-mar-2017, 6:11:10
    Author     : daniel
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="controllers.Servicios"%>
<%@page import="controllers.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Estudiante</title>
    </head>
    <body>
        <jsp:include page="navbarEstudiante.jsp"/>
        <form id="formAgregarEstudiante" class="form-horizontal form-center" action="AgregarEstudiante" method="POST"> 
            <h1 align="center">Agregar un nuevo estudiante</h1>
            <br/>
            <fieldset>
                <legend>Datos personales</legend>
                <!--Seleccion del programa academico-->
                <div class="form-group row">
                    <label class="col-sm-5 control-label" for="selPrograma">Programa acad&eacute;mico:&nbsp;</label>
                    <div class="col-sm-4 input-div">
                        <select id="selPrograma" name="selPrograma" class="form-control" width="100%" required="required">
                            <option value="">Seleccione una opcion...</option>
                            <%
                                ResultSet listProgramas = (ResultSet) request.getAttribute("listProgramas");
                                if (listProgramas != null) {
                                    while (listProgramas.next()) {
                            %>
                            <option value="<%out.print(listProgramas.getInt(1));%>"><%out.println(Utilidades.toFirstUppercase(listProgramas.getString(3)));%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <!--Input codigo-->
                <div class="form-group row">
                    <label for="txtCodigo" class="col-sm-5 control-label">Codigo:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name ="txtCodigo" id="txtCodigo" placeholder="Tu codigo de estudiante" required="required"/>
                    </div>
                </div>
                <!--Input identificacion-->
                <div class="form-group row">
                    <label for="txtIdentificacion" class="col-sm-5 control-label">Identificaci&oacute;n&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name="txtIdentificacion" id="txtIdentificacion" placeholder="Tu numero de identificaci&oacute;n" required="required"/>
                    </div>
                </div>
                <!--Input primer nombre-->
                <div class="form-group">
                    <label for="txtPNombre" class="col-sm-5 control-label">Primer Nombre:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name="txtPNombre" id="txtPNombre" placeholder="Tu primer nombre" required="required"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="txtSNombre" class="col-sm-5 control-label">Segundo Nombre:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name="txtSNombre" id="txtSNombre" placeholder="Tu segundo nombre... si tienes"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtPApellido" class="col-sm-5 control-label">Primer Apellido:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name="txtPApellido" id="txtPNombre" placeholder="Tu primer nombre" required="required"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="txtSApellido" class="col-sm-5 control-label">Segundo Apellido:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="text" name="txtSApellido" id="txtSNombre" placeholder="Tu segundo nombre... si tienes" required="required"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="txtEmail" class="col-sm-5 control-label">Email:&nbsp;</label>
                    <div class="col-sm-7 input-div" >
                        <input class="form-control" type="email" name="txtEmail" id="txtEmail" placeholder="Y ahora tu email" required="required"/>
                    </div>
                </div>
                <br/>  
                <%
                    Integer agrego = (Integer) request.getAttribute("agrego");
                    if (agrego != null) {
                        if (agrego.intValue() == 1) {
                %>
                <div class="alert alert-success center-block fade in alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                    Estudiante registrado correctamente
                </div>
                <%
                } else {
                %>
                <div class="alert alert-danger center-block fade in alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                    Error en el registro del estudiante
                </div>
                <%
                    }
                } else {
                %>
                <div>
                    <br/><br/>
                </div>
                <%
                    }
                %>
                <div class="form-group row">
                    <input type="submit" class="btn btn-default btn-block" id="btnAgregar" value="Agregar"/>
                </div>
            </fieldset>
        </form>

    </body>
</html>
