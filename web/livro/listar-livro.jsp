<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>


<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="dao" class="dao.LivroDao">
    <jsp:setProperty name="dao" property="conn" value="${connect.connection}" />
</jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Livros</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Titulo</th>
                <th>Subtitulo</th>
                <th>Edição</th>
                <th>Autor</th>
                <th>Editora</th>
                <th>Data de Publicação</th>
                <th></th>
                <th></th>
                <th colspan="2">Exemplares</th>
            </tr>
            <c:forEach var="livro" items="${dao.lista}">
                <tr>
                    <td>${livro.ID}</td>
                    <td>${livro.titulo}</td>
                    <td>${livro.subtitulo}</td>
                    <td>${livro.edicao}</td>
                    <td>${livro.autor}</td>
                    <td>${livro.editora}</td>
                    <td><fmt:formatDate value="${livro.datapublic}" pattern="dd/MM/yyyy" /></td>
                    <td><a href="alterar-livro.jsp?id=${livro.ID}">EDITAR</a></td>
                    <td><a href="#">EXCLUIR</a></td>
                    <td><a href="../exemplar/listar-exemplar.jsp?livro=${livro.ID}">Exemplares</a></td>
                    <td><a href="../exemplar/adicionar-exemplar.jsp?livro=${livro.ID}">+</a></td>
                </tr>        
            </c:forEach>
        </table>
    </body>
</html>
