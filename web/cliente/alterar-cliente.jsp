<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.ClienteDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Cliente</title>
    </head>
    <body>
        <c:set var="id" value="${param.id}" />
        <c:set var="cliente" value="${dao.buscar(id)}"/>
        <form method="post" action="../ClienteLogic">
            <input type="hidden" value="update" name="action">
            <input type="hidden" value="${id}" name="clienteID">
            <!-- Nome -->
            <div>
                <label for="clienteNome">Nome</label><br>
                <input type="text" value="${cliente.nome}" name="clienteNome" required>
            </div>
            <!-- Data de Nascimento -->
            <div>
                <label for="clienteDatenasc">Data de Nascimento</label><br>
                <input type="text" value="<fmt:formatDate value='${cliente.datanasc}' pattern='dd/MM/yyyy' />" name="clienteDatenasc">
            </div>
            <button type="submit">Salvar</button>
        </form>
    </body>
</html>
