<%-- 
    Document   : listaTrabajosGrado
    Created on : 05-mar-2017, 17:29:11
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
        <title>Trabajos de grado</title>
    </head>
    <body>
        <jsp:include page="navbarMiembroComite.jsp"/>  
        <h1 class="table-title"><%out.print(request.getAttribute("titulo"));%></h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="thead-inverse">
                    <tr>
                        <th valign="middle" class="center">Id Trabajo Grado</th>
                        <th valign="middle" class="center">Tem&aacute;tica</th>
                        <th valign="middle" class="center">Modalidad</th> 
                        <th valign="middle" class="center">Estado trabajo</th>  
                        <th valign="middle" class="center">Fecha de defensa</th>
                        <th valign="middle" class="center">Plazo de entrega</th>   
                    </tr>
                </thead>
                <tbody>
                    <%
                        ResultSet propuestas = (ResultSet) request.getAttribute("listTrabajosVigentes");
                        if (propuestas != null) {
                            while (propuestas.next()) {
                    %>
                    <tr>
                        <th scope="row" class="center">
                            <%out.print(propuestas.getInt(1));%>
                        </th>
                        <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(2)));%>
                        </td>
                        <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(3)));%>
                        </td>
                         <td align="center">
                            <%out.print(Utilidades.toFirstUppercase(propuestas.getString(4)));%>
                        </td>
                        <td align="center">
                            <%
                                Date fecha = propuestas.getDate(5);
                                if (fecha == null) {
                                    out.print("-");
                                } else {
                                    out.print(fecha);
                                }
                            %>
                        </td>
                        <td align="center">
                            <%out.print(propuestas.getDate(6));%>
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
