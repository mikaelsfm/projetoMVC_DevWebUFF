<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Administrador"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrador</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .card { margin-top: 20px; }
        .alert { margin-top: 10px; }
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
                            Administrador administrador = (Administrador) request.getAttribute("administrador");
                            String acao = (String) request.getAttribute("acao");
                            String titulo;
                            switch (acao) {
                                case "Incluir":
                                    titulo = "Incluir Administrador";
                                    break;
                                case "Alterar":
                                    titulo = "Alterar Administrador";
                                    break;
                                case "Excluir":
                                    titulo = "Excluir Administrador";
                                    break;
                                default:
                                    titulo = "Gerenciar Administrador";
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
                        <form action="/aplicacaoMVC/admin/AdministradorController" method="POST">
                            <input type="hidden" name="id" value="<%= administrador.getId() %>">
                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome</label>
                                <input type="text" name="nome" value="<%= administrador.getNome() %>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>
                            <div class="mb-3">
                                <label for="cpf" class="form-label">CPF</label>
                                <input type="text" name="cpf" value="<%= administrador.getCpf() %>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>
                            <div class="mb-3">
                                <label for="senha" class="form-label">Senha</label>
                                <input type="password" name="senha" value="<%= administrador.getSenha() %>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>
                            <div class="mb-3">
                                <label for="endereco" class="form-label">Endereço</label>
                                <input type="text" name="endereco" value="<%= administrador.getEndereco() %>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>
                            <div class="mb-3">
                                <label for="aprovado" class="form-label">Aprovado</label>
                                <select name="aprovado" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                    <option value="S" <%= "S".equals(administrador.getAprovado()) ? "selected" : "" %>>Sim</option>
                                    <option value="N" <%= "N".equals(administrador.getAprovado()) ? "selected" : "" %>>Não</option>
                                </select>
                            </div>
                            <div class="d-flex justify-content-between">
                                <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                                <a href="/aplicacaoMVC/admin/AdministradorController?acao=Listar" class="btn btn-secondary">Retornar</a>
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
