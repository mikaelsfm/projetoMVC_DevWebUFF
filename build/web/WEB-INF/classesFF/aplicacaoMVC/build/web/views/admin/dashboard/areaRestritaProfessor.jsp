<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Professor" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="#">
    <title>Área Restrita Professor</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="mt-4">
            <h1 class="text-primary">Área Restrita - Professor</h1>
            <%
                 Professor professorLogado = (Professor) session.getAttribute("professor");
                 out.println("<h3 class='text-success'>Usuário logado com sucesso (Professor)</h3>");
                 out.println("<h2>Nome: " + professorLogado.getNome() + "</h2>");

            %>
        </div>
    </div>
    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>

</html>
