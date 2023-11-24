<%@page import="model.bean.Livro"%>
<%@page import="model.ConnectionFactory"%>
<%@page import="dao.LivroDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Exemplar</title>
    </head>
    <body>
        <% 
            LivroDao dao = new LivroDao(new ConnectionFactory().getConnection());
            long livroID = Long.parseLong(request.getParameter("livro"));
            Livro livro = dao.buscar(livroID);
            
            String livroNome = livro.getTitulo() + (livro.getSubtitulo() != null ? " : " + livro.getSubtitulo() : "");
            livroNome += " por " + livro.getAutor();
            
        %>
        <form method="post" action="../ExemplarLogic">
            <input type="hidden" name="action" value="create"/>
            <!-- Livro(Titulo, Subtitulo, Autor) -->
            <div>
                <input type="hidden" value="<%=livroID%>" name="livroIDExemplar">
                <label for="livroNome">Titulo do Livro</label>
                <input type="text" value="<%=livroNome%>" name="livroNome" disabled>
            </div>
            <!-- ISBN -->
            <div>
                <label for="isbnExemplar">ISBN</label>
                <input type="text" name="isbnExemplar" required>
            </div>
            <!-- Estante -->
            <div>
                <label for="estanteExemplar">Estante</label>
                <select name="estanteExemplar">
                    <option value="---">---</option>
                </select>
            </div>
            <!-- Status -->
            <div>
                <label for="statusExemplar">Status</label>
                <select name="statusExemplar">
                    <option>Emprestado</option>
                    <option>Disponivel</option>
                    <option>Indisponivel</option>
                </select>
            </div>
            <div>
                <button type="submit">Registrar</button>
            </div>
        </form>
    </body>
</html>
