<%@page import="entidade.Professor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Professores</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-actions a { margin-right: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="mt-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1>Área Restrita</h1>
                    <h2>Lista de Professores</h2>
                </div>
                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Incluir" class="btn btn-success">
                    Incluir Professor
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nome</th>
                            <th scope="col">E-mail</th>
                            <th scope="col">CPF</th>
                            <th scope="col">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");
                            if (listaProfessores != null && !listaProfessores.isEmpty()) {
                                for (Professor professor : listaProfessores) {
                        %>
                        <tr>
                            <th scope="row"><%= professor.getId() %></th>
                            <td><%= professor.getNome() %></td>
                            <td><%= professor.getEmail() %></td>
                            <td><%= professor.getCpf() %></td>
                            <td>
                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Alterar&id=<%= professor.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Excluir&id=<%= professor.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="5" class="text-center">Nenhum professor encontrado.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
