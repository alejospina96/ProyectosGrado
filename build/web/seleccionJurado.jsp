<%-- 
    Document   : seleccionJurado
    Created on : 05-mar-2017, 18:22:57
    Author     : daniel
--%>

<%@page import="controllers.Utilidades"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seleccion jurado</title>
    </head>
    <body>
        <jsp:include page="navbarMiembroComite.jsp"/>
        <form class="form-horizontal form-center" action="ListaTrabajosJurado" method="POST">
            <fieldset>
                <legend>Buscar trabajos por jurado</legend>
                <div class="form-group row">
                    <label class="col-sm-5 control-label" for="selJurado">Jurado:&nbsp;</label>
                    <div class="col-sm-4 input-div">
                        <select id="selJurado" name="selJurado" class="form-control" width="100%" required="required">
                            <option value="">Seleccione una opcion...</option>
                            <%
                                ResultSet listEstados = (ResultSet) request.getAttribute("listJurados");
                                if (listEstados != null) {
                                    while (listEstados.next()) {
                            %>
                            <option value="<%out.print(listEstados.getInt(1));%>"><%out.println(Utilidades.toFirstUppercase(listEstados.getString(2))+" "+Utilidades.toFirstUppercase(listEstados.getString(4)));%></option>
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
