<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Relatorio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Relatório de Turmas</title>
        <link rel="stylesheet" href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h1>Relatório de Disciplinas, Turmas e Alunos</h1>
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Código da Turma</th>
                        <th>Nome da Disciplina</th>
                        <th>ID do Aluno</th>
                        <th>Nome do Aluno</th>
                        <th>Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Relatorio> relatorio = (ArrayList<Relatorio>) request.getAttribute("relatorio");
                        if (relatorio != null) {
                            for (Relatorio item : relatorio) {
                    %>
                    <tr>
                        <td><%= item.getCodigoTurma() %></td>
                        <td><%= item.getNomeDisciplina() %></td>
                        <td><%= item.getAlunoId() %></td>
                        <td><%= item.getAlunoNome() %></td>
                        <td><%= item.getNota() %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            <a href="/aplicacaoMVC/admin/home.jsp" class="btn btn-primary">Voltar</a>
        </div>
    </body>
</html>