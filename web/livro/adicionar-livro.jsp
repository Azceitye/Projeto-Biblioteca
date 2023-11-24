<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Livro</title>
    </head>
    <body>
        <form method="post" action="../LivroLogic">
            <input type="hidden" value="create" name="action"/>
            <!-- Titulo -->
            <div>
                <label for="livroTitulo">Titulo</label><br>
                <input type="text" name="livroTitulo" required>
            </div>
            <!-- Subtitulo -->
            <div>
                <label for="livroSubtitulo">Subtitulo</label><br>
                <input type="text" name="livroSubtitulo">
            </div>
            <!-- Edição -->
            <div>
                <label for="livroEdicao">Edição</label><br>
                <input type="text" name="livroEdicao">
            </div>
            <!-- Autor -->
            <div>
                <label for="livroAutor">Autor</label><br>
                <input type="text" name="livroAutor" required>
            </div>
            <!-- Editora -->
            <div>
                <label for="livroEditora">Editora</label><br>
                <input type="text" name="livroEditora" required>
            </div>
            <!-- Data de Publicação -->
            <div>
                <label for="livroDatapublic">Data de Publicação</label><br>
                <input type="text" name="livroDatapublic" placeholder="dd/mm/YYYY" required>
            </div>
            
            <button type="submit">REGISTRAR</button>
        </form>
    </body>
</html>
