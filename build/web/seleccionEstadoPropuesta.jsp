<%-- 
    Document   : seleccionEstadoPropuesta
    Created on : 05-mar-2017, 16:27:45
    Author     : daniel
--%>

<%@page import="controllers.Utilidades"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar propuestas por estado</title>
    </head>
    <body>
        <jsp:include page="navbarMiembroComite.jsp"/>
        <form class="form-horizontal form-center" action="ListaPropuestasEstado" method="POST">
            <fieldset>
                <legend>Buscar propuesta por estado</legend>
                <div class="form-group row">
                    <label class="col-sm-5 control-label" for="selEstado">Estado de propuesta:&nbsp;</label>
                    <div class="col-sm-4 input-div">
                        <select id="selEstado" name="selEstado" class="form-control" width="100%" required="required">
                            <option value="">Seleccione una opcion...</option>
                            <%
                                ResultSet listEstados = (ResultSet) request.getAttribute("listEstados");
                                if (listEstados != null) {
                                    while (listEstados.next()) {
                            %>
                            <option value="<%out.print(listEstados.getInt(1));%>"><%out.println(Utilidades.toFirstUppercase(listEstados.getString(2)));%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <br/>
                </div>
                <div class="form-group row">
                    <input type="submit" class="btn btn-default center-block" id="btnBuscar" value="Buscar"/>
                </div>
            </fieldset>
        </form>
    </body>
</html>
