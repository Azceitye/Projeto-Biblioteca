<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Cliente</title>
    </head>
    <body>
        <form method="post" action="../ClienteLogic">
            <input type="hidden" value="create" name="action">
            <!-- Nome -->
            <div>
                <label for="clienteNome">Nome</label><br>
                <input type="text" name="clienteNome" required>
            </div>
            <!-- Data de Nascimento -->
            <div>
                <label for="clienteDatenasc">Data de Nascimento</label><br>
                <input type="text" name="clienteDatenasc">
            </div>
            <button type="submit">REGISTRAR</button>
        </form>
    </body>
</html>
