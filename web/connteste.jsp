<%-- 
    Document   : connteste
    Created on : 23 de nov. de 2023, 23:20:03
    Author     : telva
--%>

<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.ConnectionFactory"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Teste de Conexão</title>
    </head>
    <body>
        <%
            Connection conn = new ConnectionFactory().getConnection();
            String mensagem = "";
            if(conn == null) {
                mensagem = "NÃO FUNCIONOU";
            } else {
                mensagem = "FUNCIONOU";
            }
        %>
        
        <p><%=mensagem%></p>
    </body>
</html>
