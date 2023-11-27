<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<jsp:useBean id="connect" class="model.ConnectionFactory" scope="application"/>

<jsp:useBean id="daoCliente" class="dao.ClienteDao">
    <jsp:setProperty name="daoCliente" property="conn" value="${connect.connection}" />
</jsp:useBean>
<jsp:useBean id="daoBiblio" class="dao.BibliotecariaDao">
    <jsp:setProperty name="daoBiblio" property="conn" value="${connect.connection}" />
</jsp:useBean>
<jsp:useBean id="daoExemplar" class="dao.ExemplarDao">
    <jsp:setProperty name="daoExemplar" property="conn" value="${connect.connection}" />
</jsp:useBean>
<jsp:useBean id="daoEmprestimo" class="dao.EmprestimoDao">
    <jsp:setProperty name="daoEmprestimo" property="conn" value="${connect.connection}" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Emprestimo</title>
        <script>
            document.querySelector('form').addEventListener('submit', function(event) {
                event.preventDefault(); 

                var checkboxValues = Array.from(document.querySelectorAll('input[name="exemplarEmprestimo"]:checked')).map(function(checkbox) {
                    return checkbox.value;
                });

                console.log(checkboxValues);
            });

        </script>
    </head>
    <body>
        <c:set var="empID" value="${param.emp}" />
        
        <c:set var="exemplares" value="${daoExemplar.list('disponivel')}"/>
        <c:set var="exemplares" value="${daoExemplar.list('disponivel')}"/>
        <c:set var="emprestimo" value="${daoEmprestimo.buscar(empID)}" />
        <c:set var="cliente" value="${daoCliente.buscar(emprestimo.clienteID)}" />
        <c:set var="biblio" value="${daoBiblio.buscar(emprestimo.biblioID)}" />
        
        <form method="post" action="../EmprestimoLogic">
            <input type="hidden" value="update" name="action">
            <input type="hidden" value="${empID}" name="empID">
            <input type="hidden" value="<fmt:formatDate value='${emprestimo.datareg}' pattern='dd/MM/yyyy' />" name="datareg">
            <!-- Cliente -->
            <div>
                <label for="clienteEmprestimo">Cliente</label>
                <select name="clienteEmprestimo" >
                    <option value="${emprestimo.clienteID}">${cliente.nome}</option>
                </select>
            </div>
            <!-- Bibliotecaria -->
            <div>
                <label for="bibliotecariaEmprestimo">Bibliotecaria</label>
                <select name="bibliotecariaEmprestimo">
                    <option value="${biblio.ID}">${biblio.nome}</option>
                </select>
            </div>
                <!-- Date de Devolução -->
                <div>
                    <label for="datedevolucEmprestimo">Data de Devolução</label>
                    <input type="text" name="datedevolucEmprestimo" value="<fmt:formatDate value='${emprestimo.datadevolucao}' pattern='dd/MM/yyyy' />" placeholder="dd/MM/yyyy" required>
                </div>
                <!-- Valor da Multa Diária -->
                <div>
                    <label for="valmultaEmprestimo">Valor de Multa Diária</label>
                    <input type="text" name="valmultaEmprestimo" value="${emprestimo.valmultadiaria}" placeholder="000.00" required>
                </div>
                <!-- Status -->
                <div>
                <label for="statusEmprestimo">Status</label>
                    <select name="statusEmprestimo">
                        <c:choose>
                            <c:when test="${emprestimo.status == 'ativo'}">
                                <option value="ativo">Ativo</option>
                                <option value="concluido">Concluido</option>
                            </c:when>
                            <c:when test="${emprestimo.status != 'ativo'}">
                                <option value="concluido">Concluido</option>
                                <option value="ativo">Ativo</option>
                            </c:when>
                        </c:choose>
                    </select>
                </div>
                <div>
                    <input type="text" value="<fmt:formatDate value='${emprestimo.datareg}' pattern='dd/MM/yyyy' />" disabled>
                </div>
                <!-- Exemplares -->
                <div>
                    <table>
                        <tr>
                            <th>ISBN</th>
                            <th>Livro</th>
                            <th>Estante</th>
                            <th>Status</th>                
                        </tr>
                        <c:forEach var="empExemplar" items="${emprestimo.listItensISBN}">
                            <c:set var="exemplar" value="${daoExemplar.buscar(empExemplar)}" />
                            <tr>
                                <td>${exemplar.ISBN}</td>
                                <td>${exemplar.livroID}</td>
                                <td>${exemplar.estanteID}</td>
                                <td>${exemplar.status}</td>
                                <td>
                                    <input type="checkbox" name="exemplarEmprestimo" value="${exemplar.ISBN}" checked disabled>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="exemplar" items="${exemplares}">
                                <tr>
                                    <td>${exemplar.ISBN}</td>
                                    <td>${exemplar.livroID}</td>
                                    <td>${exemplar.estanteID}</td>
                                    <td>${exemplar.status}</td>
                                    <td>
                                        <input type="checkbox" name="exemplarEmprestimo" value="${exemplar.ISBN}">
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div>
                        <button type="submit">Registrar</button>
                    </div>
                </form>
    </body>
</html>
