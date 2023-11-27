<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>
<jsp:useBean id="daoCliente" class="dao.ClienteDao">
    <jsp:setProperty name="daoCliente" property="conn" value="${connect.connection}" />
</jsp:useBean>
<jsp:useBean id="daoEmprestimo" class="dao.EmprestimoDao">
    <jsp:setProperty name="daoEmprestimo" property="conn" value="${connect.connection}" />
</jsp:useBean>
<jsp:useBean id="daoBiblio" class="dao.BibliotecariaDao">
    <jsp:setProperty name="daoBiblio" property="conn" value="${connect.connection}" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Emprestimos</title>
        <style>
            td p {
                margin-bottom: 5px; 
                border-bottom: 1px solid #ccc; 
                padding-bottom: 5px; 
            }
            
            td p:last-child {
                border-bottom: none; 
            }
        </style>
    </head>
    <body>
        <c:set var="cliente" value="${daoCliente.buscar(param.id)}" />
        <c:choose>
            <c:when test="${cliente != null}">
                
                <table border="1">
                    <tr>
                        <th>Cliente :</th>
                        <th colspan="5">${cliente.nome}</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Bibliotecaria</th>
                        <th>Valor da Multa Diária</th>
                        <th>Data de Devolução</th>
                        <th>Status</th>
                        <th>Data de Registro</th>
                        <th>ISBN</th>
                    </tr>
                    <tr>
                        <c:forEach var="emp" items="${daoEmprestimo.list(cliente.ID)}">
                            <c:set var="biblio" value="${daoBiblio.buscar(emp.biblioID).getNome()}"/>
                            <c:set var="exemplares" value="${emp.listItensISBN}" />
                            <td>${emp.ID}</td>
                            <td>${biblio}</td>
                            <td><fmt:formatDate value='${emp.datadevolucao}' pattern='dd/MM/yyyy' /></td>
                            <td><fmt:formatDate value='${emp.datareg}' pattern='dd/MM/yyyy' /></td>
                            <td>${emp.valmultadiaria}</td>
                            <td>${emp.status}</td>
                            <td>
                            <c:forEach var="exemplar" items="${exemplares}">
                                <p>${exemplar}</p>
                            </c:forEach>
                            </td>
                            <td>
                            <c:forEach var="exemplar" items="${exemplares}">
                            <td>
                                <p>
                                    <a href="alterar-emprestimo.jsp?isbn=${exemplar}&emp=${emp.ID}">Alterar</a>
                                </p>
                            <td>
                                <p>
                                    <form method="post" action="../EmprestimoLogic">
                                        <input type="hidden" value="delete-row" name="action">
                                        <input type="hidden" value="${exemplar}" name="isbn">
                                        <input type="hidden" value="${emp.ID}" name="empID">
                                        <button type="submit">-</button>
                                    </form>
                                </p>
                            </td>
                            </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </table>
            </c:when>
            <c:when test="${cliente == null}">
                <c:redirect url="cliente/adicionar-cliente.jsp"/>
            </c:when>
        </c:choose>
    </body>
</html>
