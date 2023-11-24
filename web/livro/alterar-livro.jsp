<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.bean.Livro"%>
<%@page import="dao.LivroDao"%>
<%@page import="model.ConnectionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Livro</title>
    </head>
    <body>
        <%
            LivroDao dao = new LivroDao( new ConnectionFactory().getConnection() );
            Long ID = Long.parseLong( request.getParameter("id"));
            Livro livro = dao.buscar(ID);
            
            String date = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(livro.getDatapublic().toString()));
        %>
        <form method="post" action="../LivroLogic">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="livroID" value="<%=ID%>">
            <!-- Titulo -->
            <div>
                <label for="livroTitulo">Titulo</label><br>
                <input type="text" name="livroTitulo" value="<%=livro.getTitulo()%>" required>
            </div>
            <!-- Subtitulo -->
            <div>
                <label for="livroSubtitulo">Subtitulo</label><br>
                <input type="text" name="livroSubtitulo" value="<%=livro.getSubtitulo()%>">
            </div>
            <!-- Edição -->
            <div>
                <label for="livroEdicao">Edição</label><br>
                <input type="text" name="livroEdicao" value="<%=livro.getEdicao()%>">
            </div>
            <!-- Autor -->
            <div>
                <label for="livroAutor">Autor</label><br>
                <input type="text" name="livroAutor" value="<%=livro.getAutor()%>" required>
            </div>
            <!-- Editora -->
            <div>
                <label for="livroEditora">Editora</label><br>
                <input type="text" name="livroEditora" value="<%=livro.getEditora()%>" required>
            </div>
            <!-- Data de Publicação -->
            <div>
                <label for="livroDatapublic">Data de Publicação</label><br>
                <input type="text" name="livroDatapublic" placeholder="dd/mm/YYYY" value="<%=date%>" required>
            </div>
            
            <div>
                <button type="submit">Salvar</button>
            </div>
        </form>
    </body>
</html>
