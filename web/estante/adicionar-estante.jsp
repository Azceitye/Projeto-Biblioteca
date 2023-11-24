<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="../EstanteLogic">
            <input type="hidden" value="create" name="action">
            <!-- Conjunto -->
            <div>
                <label for="conjuntoEstante">Conjunto</label>
                <input type="number" name="conjuntoEstante">
            </div>
            
            <!-- Local -->
            <div>
                <label for="localEstante">Local Info</label>
                <input type="text" name="localEstante">
            </div>
            
            <div>
                <button type="submit">Registrar</button>
            </div>
        </form>
    </body>
</html>
