<%@page import="java.util.List"%>
<%@page import="dao.EstanteDao"%>
<%@page import="model.ConnectionFactory"%>
<%@page import="model.bean.Estante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Estantes</title>
    </head>
    <body>
        <%
            List<Estante> estantes = new EstanteDao(new ConnectionFactory().getConnection()).list();
            if(!estantes.isEmpty()) {
        %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Conjunto</th>
                <th>Local</th>
            </tr>
            <tr>
                <% for(Estante estante : estantes) { %>
                    <td><%=estante.getID()%></td>
                    <td><%=estante.getConjuntoID()%></td>
                    <td><%=estante.getLocal()%></td>               
                    <td><a href="alterar-estante.jsp?id=<%=estante.getID()%>">Alterar</a></td>
                    <td><a href="#">Remover</a></td>
                <% } %>
            </tr>
        </table>
        <% } else {%>
        <div>
            <p>Sem estantes registradas : Registre por <a href="adicionar-estante.jsp">aqui</a>.</p>
        </div>
        <% } %>
    </body>
</html>
