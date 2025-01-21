<%@ page import="entidade.Aluno" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Alunos</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-actions a {
            margin-right: 5px;
        }
        h1, h2 {
            color: #333;
        }
        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
    </div>

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h1 class="mb-0">Área Restrita</h1>
                <p class="text-muted">Gerenciamento de Alunos</p>
            </div>
            <a href="/aplicacaoMVC/admin/AlunoController?acao=Incluir" class="btn btn-success">
                Incluir Aluno
            </a>
        </div>

        <div class="table-responsive">
            <table class="table table-hover table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th scope="col"># ID</th>
                        <th scope="col">Nome</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Celular</th>
                        <th scope="col" class="text-center">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                        if (listaAlunos != null && !listaAlunos.isEmpty()) {
                            for (Aluno aluno : listaAlunos) {
                    %>
                    <tr>
                        <th scope="row"><%= aluno.getId() %></th>
                        <td><%= aluno.getNome() %></td>
                        <td><%= aluno.getEmail() %></td>
                        <td><%= aluno.getCelular() %></td>
                        <td class="text-center table-actions">
                            <a href="/aplicacaoMVC/admin/AlunoController?acao=Alterar&id=<%= aluno.getId() %>" class="btn btn-warning btn-sm" title="Alterar">
                                Alterar
                            </a>
                            <a href="/aplicacaoMVC/admin/AlunoController?acao=Excluir&id=<%= aluno.getId() %>" class="btn btn-danger btn-sm" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este aluno?');">
                                Excluir
                            </a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Nenhum aluno encontrado.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
