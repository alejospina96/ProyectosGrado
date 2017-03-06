<%-- 
    Document   : registrarPropuesta
    Created on : 04-mar-2017, 22:57:13
    Author     : daniel
--%>

<%@page import="controllers.Utilidades"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nueva Popuesta</title>        
    </head>
    <body>
        <jsp:include page="navbarEstudiante.jsp"/>
        <script src="js/registrarPropuesta.js" type="text/javascript"></script>
        <form class="form-horizontal form-center" action="RegistrarPropuesta" method="POST">
            <h1 align="center">Registrar una nueva propuesta</h1>
            <br/>
            <fieldset>
                <legend>Datos de tu propuesta</legend>
                <!--Select modalidad-->
                <div class="form-group row">
                    <label class="col-sm-3 control-label" for="selModalidad">Modalidad:&nbsp;</label>
                    <div class="col-sm-4 input-div">
                        <select id="selModalidad" name="selModalidad" class="form-control" width="100%" required="required">
                            <option value="">Seleccione una opcion...</option>
                            <%
                                ResultSet listSelect = (ResultSet) request.getAttribute("listModalidades");
                                if (listSelect != null) {
                                    while (listSelect.next()) {
                            %>
                            <option value="<%out.print(listSelect.getInt(1));%>"><%out.println(Utilidades.toFirstUppercase(listSelect.getString(2)));%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <!--Input tematica-->
                <div class="form-group row">
                    <label for="txtTematica" class="col-sm-3 control-label">Tem&aacute;tica:&nbsp;</label>
                    <div class="col-sm-9 input-div" >
                        <input class="form-control" type="text" name="txtTematica" id="txtTematica" placeholder="De qué tratará tu trabajo en pocas palabras" required="required"/>
                    </div>
                </div>
                <!--Load archivo-->
                <div class="form-group row">
                    <label class="control-label col-sm-3">Sube tu archivo:&nbsp;</label>
                    <div class="col-sm-5 input-div" >
                        <input id="filePropuesta" name="filePropuesta" type="file" class="filestyle" data-buttonText="Examinar" onchange="manejarArchivo(this.files)" required="required"/>
                    </div>
                </div>
                <legend>Datos de los participantes</legend>
                <!--Botones añadir y quitar participante-->
                <div class="form-group row">
                    <input type="button" class="btn btn-primary" id="btnMasParticipantes" name="btnMasParticipantes" value="Agregar participante"/>
                    <input type="button" class="btn btn-primary" id="btnMenosParticipantes" name="btnMenosParticipantes" value="Quitar participante"/>
                </div>
                <div class="form-group row" id="codigo-1">
                    <label for="txtCodigo' + cantidad + '" class="col-sm-3 control-label">
                        Codigo 1:&nbsp;
                    </label>
                    <div class="col-sm-8 input-div" >
                        <input class="form-control" type="text" name="txtCodigo1" id="txtCodigo1" placeholder="Codigo estudiantil" required="required"/>    
                    </div>
                </div>
                <div class="form-group row" id="here">
                    <!--Aqui se agrega un nuevo campo para un estudiante-->
                </div>            <!--JSP para el error-->
                <%
                    Integer agrego = (Integer) request.getAttribute("agrego");
                    if (agrego != null) {
                        if (agrego.intValue() == 1) {
                %>
                            <div class="alert alert-success center-block fade in alert-dismissable">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                                Propuesta registrada correctamente
                            </div>
                            <%
                        } else {
                            %>
                                <div class="alert alert-danger center-block fade in alert-dismissable">
                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
                                    Error en el registro de la propuesta
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
                    <input type="submit" class="btn btn-default btn-block" id="btnAgregar" value="Agregar"/>
                </div>
            </fieldset>
        </form>
    </body>
</html>
