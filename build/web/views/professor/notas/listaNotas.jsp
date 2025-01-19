<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Aluno"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Listagem de Alunos</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />

            <div class="mt-5">
                <h1>Listagem de Alunos</h1>

                <%
                    ArrayList<Turma> turmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                    if (turmas == null || turmas.isEmpty()) {
                %>
                    <div class="alert alert-info">
                        Nenhuma turma com alunos cadastrados foi encontrada.
                    </div>
                <%
                    } else {
                %>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID Turma</th>
                                    <th>Código</th>
                                    <th>Disciplina</th>
                                    <th>Alunos</th>
                                    <th>Nota</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="turma" items="${listaTurmas}">
                                    <tr>
                                        <td>${turma.id}</td>
                                        <td>${turma.codigoTurma}</td>
                                        <td>${turma.disciplinaNome}</td>
                                        <td>
                                            <c:forEach var="aluno" items="${turma.alunosNomes}">
                                                <span>${aluno}</span><br />
                                            </c:forEach>
                                        </td>
                                        <td>${turma.nota}</td>
                                    </tr>
                                </c:forEach>
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
