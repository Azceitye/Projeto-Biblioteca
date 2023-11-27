<%@page import="java.util.ArrayList"%>
<%@page import="dao.EstanteDao"%>
<%@page import="java.util.List"%>
<%@page import="model.bean.Estante"%>
<%@page import="dao.LivroDao"%>
<%@page import="model.bean.Livro"%>
<%@page import="java.sql.Connection"%>
<%@page import="model.ConnectionFactory"%>
<%@page import="model.bean.Exemplar"%>
<%@page import="dao.ExemplarDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Exemplar</title>
    </head>
    <body>
        <% 
            String isbn = request.getParameter("exemplar");
            Connection conn = new ConnectionFactory().getConnection();
            Exemplar exemplar = new ExemplarDao(conn).buscar(isbn);       
            
            List<String> status = new ArrayList<>();
            status.add("Disponivel");
            status.add("Indisponivel");
            status.remove(exemplar.getStatus());
            
            
            List<Estante> estantes = new EstanteDao(conn).list();
            estantes.remove(exemplar.getEstanteID());
            if(!estantes.isEmpty()) {
        %>
            <form method="post" action="../ExemplarLogic">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" value="<%=exemplar.getLivroID()%>" name="livroIDExemplar">
                <!-- ISBN -->
                <div>
                    <input type="hidden" value="<%=exemplar.getISBN()%>" name="oldisbn">
                    <label for="isbnExemplar">ISBN</label>
                    <input type="text" value="<%=exemplar.getISBN()%>" name="isbnExemplar" required>
                </div>
                <!-- Estante -->
                <div>
                    <label for="estanteExemplar">Estante</label>
                    <select name="estanteExemplar">
                        <option value="<%=exemplar.getEstanteID()%>"><%=exemplar.getEstanteID()%></option>
                        <%for(Estante estante : estantes) { %>
                            <option value="<%=estante.getID()%>"><%=estante.getID()%></option>
                        <% } %>
                    </select>
                </div>
                <!-- Status -->
                <div>
                    <label for="statusExemplar">Status</label>
                    <select name="statusExemplar">
                        <option value="<%=exemplar.getStatus()%>"><%=exemplar.getStatus()%></option>
                        <% for(String stat : status) { %>
                            <option value="<%=stat%>"><%=stat%></option>
                        <% } %>
                    </select>
                </div>
                <div>
                    <button type="submit">Registrar</button>
                </div>
            </form>        
        <% } else { %>
            <div>
                <p>Antes, registre uma estante para colocar os Exemplares: <a href="../estante/adicionar-estante.jsp">AQUI</a></p>
            </div>
        <% } %>
    </body>
</html>
