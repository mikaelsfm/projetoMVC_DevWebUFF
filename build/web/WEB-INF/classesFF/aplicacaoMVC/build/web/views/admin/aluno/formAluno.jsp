<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Aluno"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comentário</title>
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
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <%
                            Aluno aluno = (Aluno) request.getAttribute("aluno");
                            String acao = (String) request.getAttribute("acao");

                            String titulo;
                            switch (acao) {
                                case "Incluir":
                                    titulo = "Incluir Aluno";
                                    break;
                                case "Alterar":
                                    titulo = "Alterar Aluno";
                                    break;
                                case "Excluir":
                                    titulo = "Excluir Aluno";
                                    break;
                                default:
                                    titulo = "Aluno";
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

                        <form action="/aplicacaoMVC/admin/AlunoController" method="POST">
                            <input type="hidden" name="id" value="<%= aluno.getId() %>">

                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome</label>
                                <input type="text" name="nome" value="<%= aluno.getNome() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">E-mail</label>
                                <input type="email" name="email" value="<%= aluno.getEmail() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="celular" class="form-label">Celular</label>
                                <input type="text" name="celular" value="<%= aluno.getCelular() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="cpf" class="form-label">CPF</label>
                                <input type="text" name="cpf" value="<%= aluno.getCpf() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="senha" class="form-label">Senha</label>
                                <input type="password" name="senha" value="<%= aluno.getSenha() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>

                            <div class="mb-3">
                                <label for="endereco" class="form-label">Endereço</label>
                                <input type="text" name="endereco" value="<%= aluno.getEndereco() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>

                            <div class="mb-3">
                                <label for="cidade" class="form-label">Cidade</label>
                                <input type="text" name="cidade" value="<%= aluno.getCidade() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>

                            <div class="mb-3">
                                <label for="bairro" class="form-label">Bairro</label>
                                <input type="text" name="bairro" value="<%= aluno.getBairro() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>

                            <div class="mb-3">
                                <label for="cep" class="form-label">CEP</label>
                                <input type="text" name="cep" value="<%= aluno.getCep() %>" class="form-control" 
                                       <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>

                            <div class="d-flex justify-content-between">
                                <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                                <a href="/aplicacaoMVC/admin/AlunoController?acao=Listar" class="btn btn-secondary">Retornar</a>
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
