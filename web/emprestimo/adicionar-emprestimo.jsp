<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Emprestimo</title>
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
        <% String[] stats = {"ativo", "concluido"}; %>
        <c:set var="exemplares" value="${daoExemplar.list('disponivel')}"/>
        <c:choose>
            <c:when test="${not empty exemplares}">
                <form method="post" action="../EmprestimoLogic">
                    <input type="hidden" value="create" name="action">
                    <!-- Cliente -->
                    <div>
                        <label for="clienteEmprestimo">Cliente</label>
                        <select name="clienteEmprestimo">
                            <c:forEach var="cliente" items="${daoCliente.list()}">
                                <option value="${cliente.ID}">${cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- Bibliotecaria -->
                    <div>
                        <label for="bibliotecariaEmprestimo">Bibliotecaria</label>
                        <select name="bibliotecariaEmprestimo">
                            <c:forEach var="biblio" items="${daoBiblio.list('')}">
                                <option value="${biblio.ID}">${biblio.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- Date de Devolução -->
                    <div>
                        <label for="datedevolucEmprestimo">Data de Devolução</label>
                        <input type="text" name="datedevolucEmprestimo" placeholder="dd/MM/yyyy" required>
                    </div>
                    <!-- Valor da Multa Diária -->
                    <div>
                        <label for="valmultaEmprestimo">Valor de Multa Diária</label>
                        <input type="text" name="valmultaEmprestimo" placeholder="000.00" required>
                    </div>
                    <!-- Status -->
                    <div>
                        <label for="statusEmprestimo">Status</label>
                        <select name="statusEmprestimo">
                            <% for(String status : stats) { %>
                                <option value="<%=status%>"><%=status%></option>
                            <% } %>
                        </select>
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
            </c:when>
            
            <c:when test="${empty exemplares}">
                <div>
                    <p>Primeiramente registre um Exemplar : <a href="../livro/listar-livro.jsp">aqui</a></p
                </div>
            </c:when>
        </c:choose>
        
    </body>
</html>