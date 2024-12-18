<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Administrador"%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Administrador</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        Administrador administrador = (Administrador) request.getAttribute("administrador");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Administrador</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Administrador</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Administrador</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError%>
                    </div>
                    <% }%>

                    <form action="/aplicacaoMVC/admin/AdministradorController" method="POST">
                        <input type="hidden" name="id" value="<%=administrador.getId()%>" class="form-control">

                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" name="nome" value="<%=administrador.getNome()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> required>
                        </div>

                        <div class="mb-3">
                            <label for="cpf" class="form-label">CPF</label>
                            <input type="text" name="cpf" value="<%=administrador.getCpf()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> required>
                        </div>

                        <div class="mb-3">
                            <label for="senha" class="form-label">Senha</label>
                            <input type="password" name="senha" value="<%=administrador.getSenha()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> required>
                        </div>

                        <div class="mb-3">
                            <label for="endereco" class="form-label">Endereço</label>
                            <input type="text" name="endereco" value="<%=administrador.getEndereco()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> >
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="<%=acao%>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/AdministradorController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

</html>