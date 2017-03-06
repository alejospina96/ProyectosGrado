<%-- 
    Document   : listaPropuestas
    Created on : 05-mar-2017, 14:35:47
    Author     : daniel
--%>

<%@page import="java.sql.Date"%>
<%@page import="controllers.Utilidades"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de propuestas</title>
    </head>
    <body>
        <jsp:include page="navbarMiembroComite.jsp"/>  
        <h1 class="table-title">Propuestas de grado registradas</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="thead-inverse">
                    <tr>
                        <th class="center">Id Trabajo Grado</th>
                        <th class="center">Fecha de propuesta</th>
                        <th class="center">Modalidad</th>
                        <th class="center">Tem&aacute;tica</th>
                        <th class="center">C&oacute;digo estudiante</th>  
                        <th class="center">Estado propuesta</th>  
                        <th class="center">Plazo entrega correcciones</th>    
                    </tr>
                </thead>
                <tbody>
                    <%
                        ResultSet propuestas = (ResultSet) request.getAttribute("listPropuestas");
                        if (propuestas != null) {
                            while (propuestas.next()) {
                    %>
                    <tr>
                        <td align="center">
                            <%out.print(propuestas.getInt(1));%>
                        </td>
                        <td align="center">
                            <%out.print(propuestas.getDate(2));%>
                        </td>
                        <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(3)));%>
                        </td>
                        <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(4)));%>
                        </td>
                        <td align="center">
                            <%out.print(propuestas.getLong(5));%>
                        </td>
                        <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(6)));%>
                        </td>
                        <td align="center">
                            <%
                                Date fecha = propuestas.getDate(7);
                                if (fecha == null) {
                                    out.print("-");
                                } else {
                                    out.print(fecha);
                                }
                            %>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="7">
                            No se encontraron registros
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
