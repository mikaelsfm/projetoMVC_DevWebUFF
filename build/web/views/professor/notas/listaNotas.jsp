<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Aluno"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listagem de Alunos</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        h1 {
            color: #007bff;
            margin-bottom: 20px;
        }
        .table-responsive {
            margin-top: 20px;
        }
        .alert {
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />

        <div class="mt-5">
            <h1 class="text-center">Listagem de Alunos</h1>

            <%
                ArrayList<Turma> turmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                if (turmas == null || turmas.isEmpty()) {
            %>
                <div class="alert alert-info text-center">
                    Nenhuma turma com alunos cadastrados foi encontrada.
                </div>
            <%
                } else {
            %>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">ID Turma</th>
                                <th scope="col">Código</th>
                                <th scope="col">Disciplina</th>
                                <th scope="col">Alunos</th>
                                <th scope="col">Nota</th>
                                <th scope="col">Vagas Disponíveis</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Turma turma : turmas) {
                            %>
                            <tr>
                                <td><%= turma.getId() %></td>
                                <td><%= turma.getCodigoTurma() %></td>
                                <td><%= turma.getDisciplinaNome() %></td>
                                <td>
                                    <% 
                                        if (turma.getAlunosNomes() != null && !turma.getAlunosNomes().isEmpty()) {
                                            for (String aluno : turma.getAlunosNomes()) {
                                    %>
                                    <span><%= aluno %></span><br />
                                    <%
                                            }
                                        } else {
                                    %>
                                    <span class="text-muted">Nenhum aluno cadastrado</span>
                                    <%
                                        }
                                    %>
                                </td>
                                <td><%= turma.getNota() %></td>
                                <td>
                                    <%
                                        int vagasDisponiveis = 2 - turma.getAlunosNomes().size();
                                        if (vagasDisponiveis > 0) {
                                    %>
                                    <span class="text-success"><%= vagasDisponiveis %> vagas</span>
                                    <%
                                        } else {
                                    %>
                                    <span class="text-danger">Turma cheia</span>
                                    <%
                                        }
                                    %>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            <%
                }
            %>
        </div>
    </div>

    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
