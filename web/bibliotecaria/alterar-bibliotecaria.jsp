<%@page import="dao.BibliotecariaDao"%>
<%@page import="model.bean.Bibliotecaria"%>
<%@page import="java.util.Map"%>
<%@page import="dao.EstanteDao"%>
<%@page import="model.ConnectionFactory"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Bibliotecaria</title>
        <% 
            List<String> listaNivel = new ArrayList<>();
            listaNivel.add("junior");
            listaNivel.add("estagio");
            listaNivel.add("pleno");
            listaNivel.add("senior");
            
            Connection conn = new ConnectionFactory().getConnection();
            
            long ID = Long.parseLong(request.getParameter("id"));
            Bibliotecaria biblio = new BibliotecariaDao(conn).buscar(ID);
            List<Bibliotecaria> biblios = new BibliotecariaDao(conn).list("estagio");
            
            Map<Long, String> conjuntos = new EstanteDao(conn).listarConjunto();
            conjuntos.remove(biblio.getConjuntoID());
            listaNivel.remove(biblio.getNivel());
            String conjuntoCat = new EstanteDao(conn).buscarConjunto(biblio.getConjuntoID());
        %>
        <script>
            function estagioInput() {
                var opcao = document.getElementById("biblioNivel").value;
                var campo = document.getElementById("biblioResp");
                var btn = document.getElementById("btnCadastro");
                var avisovazio = document.getElementById("avisoVazio");
                
                
                if(opcao === "estagio") {
                    campo.disabled = false;
                    
                    if((<%=biblios.isEmpty()%>)) {
                        btn.disabled = true;
                        avisovazio.style.display = "block";
                    } else {
                        btn.disabled = false;
                        avisovazio.style.display = "none";
                    }
                } else {
                    campo.disabled = true;
                    btn.disabled = false;
                    avisovazio.style.display = "none";
                }
            }
        </script>
    </head>
    <body>
        <form method="post" action="../BibliotecariaLogic">
            <input type="hidden" value="update" name="action">
            <input type="hidden" value="<%=ID%>" name="biblioID">
            <!-- Nome -->
            <div>
                <label for="biblioNome">Nome</label>
                <input type="text" name="biblioNome" value="<%=biblio.getNome()%>" required>
            </div>
            <!-- Instituicao -->
            <div>
                <label for="biblioInstituicao">Instituição</label>
                <input type="text" name="biblioInstituicao" value="<%=biblio.getInstituicao()%>" required>
            </div>
            <!-- Conjunto -->
            <div>
                <label for="biblioConjunto">Conjunto</label>
                <select name="biblioConjunto" required>
                    <option value="<%=biblio.getConjuntoID()%>"><%=conjuntoCat%></option>
                    <% 
                        for(Map.Entry<Long, String> entry : conjuntos.entrySet()) {
                        Long id = entry.getKey();
                        String cat = entry.getValue();
                        String option = id + ( !cat.isBlank() && !cat.isEmpty() ? ": " + cat : "" );
                    %>
                        <option value="<%=id%>"><%=option%></option>
                    <% } %>
                </select>
            </div>
            <!-- Nivel -->
            <div>
                <label for="biblioNivel">Nível</label>
                <select id="biblioNivel" name="biblioNivel" onchange="estagioInput()">
                    <option value="<%=biblio.getNivel()%>"><%=biblio.getNivel()%></option>
                    <% for(String nivel : listaNivel) { %>
                        <option value="<%=nivel%>"><%=nivel%></option>
                    <% } %>
                </select>
            </div>
            <!-- Responsável -->
            <div>
                <label for="biblioResp">Responsável</label>
                <select id="biblioResp" name="biblioResp" disabled>
                    <% for(Bibliotecaria biblioItem : biblios) { %>
                        <option value="<%=biblioItem.getID()%>"><%=biblioItem.getNome()%></option>
                    <% } %>
                </select>
            </div>              
            <div>
                <button id="btnCadastro" type="submit">REGISTRAR</button>
            </div>
                <span id="avisoVazio" style="display: none;">
                    <p >* Para cadastro de bibliotecarias estagiarias, antes por favor registre alguém de nível júnior no minimo</p>
                </span>
        </form>
    </body>
</html>
