<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.BibliotecariaDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bibliotecarias</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Nivel</th>
                <th>Instituiçao</th>
                <th>Responsavel</th>
                <th>Conjunto</th>
            </tr>
            <c:forEach var="biblio" items="${dao.list('')}">
                <tr>
                    <td>${biblio.ID}</td>
                    <td>${biblio.nome}</td>
                    <td>${biblio.nivel}</td>
                    <td>${biblio.instituicao}</td>
                    <td>
                        <c:if test="${biblio.responsavelID != null}">
                            <c:set var="resp" value="${dao.buscar(biblio.responsavelID)}" />
                            ${resp.nome}
                        </c:if>
                    </td>
                    <td>${biblio.conjuntoID}</td>
                    <td><a href="alterar-bibliotecaria.jsp?id=${biblio.ID}">Alterar</a></td>
                    <td><a href="#">Remover</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
