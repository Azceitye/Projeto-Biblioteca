<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.bean.Estante"%>
<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.EstanteDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Estante</title>
    </head>
    <body>
        <c:set var="ID" value="${param.id}"/>
        <c:set var="estante" value="${dao.buscar(ID)}"/>
        <form method="post" action="../EstanteLogic">
            <input type="hidden" value="update" name="action">
            <input type="hidden" value="${estante.ID}" name="IDEstante">
            <!-- Conjunto -->
            <div>
                <label for="conjuntoEstante">Conjunto</label>
                <input type="number" value="${estante.conjuntoID}" name="conjuntoEstante">
            </div>
            
            <!-- Local -->
            <div>
                <label for="localEstante">Local Info</label>
                <input type="text" value="${estante.local}" name="localEstante">
            </div>
            
            <div>
                <button type="submit">Registrar</button>
            </div>
        </form>
    </body>
</html>
