<%@page import="model.ConnectionFactory"%>
<%@page import="dao.ExemplarDao"%>
<%@page import="java.util.List"%>
<%@page import="model.bean.Exemplar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.ExemplarDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Exemplares</title>
        <script>
            windows.location.href = 'lista-exemplar.jsp';
        </script>
    </head>
    <body>
        <c:set var="LivroID" value="${param.livro}" />
        <c:set var="exemplares" value="${not empty LivroID ? dao.getLista(LivroID) : null}" />
        
        <c:choose>
            <c:when test="${LivroID != null}">
                <c:choose>
                    <c:when test="${not empty exemplares}">
                        <table border="1">
                            <tr>
                                <th>ISBN</th>
                                <th>Livro</th>
                                <th>Estante</th>
                                <th>Status</th>                
                            </tr>
                        <c:forEach var="exemplar" items="${exemplares}">
                            <tr>
                                <td>${exemplar.ISBN}</td>
                                <td>${exemplar.livroID}</td>
                                <td>${exemplar.estanteID}</td>
                                <td>${exemplar.status}</td>
                                <td><a href="alterar-exemplar.jsp?exemplar=${exemplar.ISBN}">Alterar</a></td>
                                <td>
                                    <form method="post" action="../ExemplarLogic">
                                        <input type="hidden" value="delete" name="action">
                                        <input type="hidden" value="${exemplar.ISBN}" name="isbnExemplar">
                                        <input type="hidden" value="${exemplar.livroID}" name="livroIDExemplar">
                                        <button type="submit">-</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </table>
                        <div>
                            <a href="adicionar-exemplar.jsp?livro=${LivroID}">(+) Adicionar</a>
                        </div>
                    </c:when>
                    <c:when test="${empty exemplares}">
                        <div>
                            <p>Esse livro não possui exemplares</p>
                            <a href="../livro/listar-livro.jsp">Voltar</a>
                        </div>
                    </c:when>
                </c:choose>
            </c:when>
            
            <c:when test="${LivroID == null}">
                <div>
                    <p>Por favor insira um ID de livro real</p>
                    <a href="../livro/listar-livro.jsp">Voltar</a>
                </div>
            </c:when>
        </c:choose>
    </body>
</html>