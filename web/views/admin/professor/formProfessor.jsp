<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Professor"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Professor</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .card {
            margin-top: 20px;
        }
        .alert {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />

        <div class="row justify-content-center mt-5">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <%
                            Professor professor = (Professor) request.getAttribute("professor");
                            String acao = (String) request.getAttribute("acao");

                            String titulo;
                            switch (acao) {
                                case "Incluir":
                                    titulo = "Incluir Professor";
                                    break;
                                case "Alterar":
                                    titulo = "Alterar Professor";
                                    break;
                                case "Excluir":
                                    titulo = "Excluir Professor";
                                    break;
                                default:
                                    titulo = "Gerenciar Professor";
                            }
                        %>
                        <h1 class="card-title mb-4"><%= titulo %></h1>

                        <%
                            String msgError = (String) request.getAttribute("msgError");
                            if (msgError != null && !msgError.isEmpty()) {
                        %>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError %>
                        </div>
                        <% } %>

                        <form action="/aplicacaoMVC/admin/ProfessorController" method="POST">
                            <input type="hidden" name="id" value="<%= professor.getId() %>">

                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome</label>
                                <input type="text" name="nome" value="<%= professor.getNome() %>" 
                                       class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">E-mail</label>
                                <input type="email" name="email" value="<%= professor.getEmail() %>" 
                                       class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="cpf" class="form-label">CPF</label>
                                <input type="text" name="cpf" value="<%= professor.getCpf() %>" 
                                       class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="senha" class="form-label">Senha</label>
                                <input type="password" name="senha" value="<%= professor.getSenha() %>" 
                                       class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="d-flex justify-content-between">
                                <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Listar" class="btn btn-secondary">Retornar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
