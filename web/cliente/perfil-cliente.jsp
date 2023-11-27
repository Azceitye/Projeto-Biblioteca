<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.ClienteDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil</title>
    </head>
    <body>
        <div>
            <c:set var="id" value="${param.id}" />
            <c:set var="cliente" value="${dao.buscar(id)}"/>
            
            <div>
                Nome : ${cliente.nome}
            </div>
            <div>
                Data de Nascimento : <fmt:formatDate value="${cliente.datanasc}" pattern="dd/MM/yyyy" />
            </div>
            <div>
                <a href="cliente/alterar-cliente.jsp?id=${id}">Alterar</a>
            </div>
            <div>
                <a href="../emprestimo/listar-emprestimo.jsp?id=${id}">Emprestimos</a>
            </div>
        </div>
    </body>
</html>
