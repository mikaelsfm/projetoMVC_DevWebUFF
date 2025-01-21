<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Form Inscrições</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />

            <div class="mt-5">
                <h1>Turmas Disponíveis</h1>
                <%
                    String msgError = (String) request.getAttribute("msgError");
                    if (msgError != null && !msgError.isEmpty()) {
                %>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError %>
                    </div>
                <%
                    }

                    ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                    if (listaTurmas == null) {
                        listaTurmas = new ArrayList<>();
                    }
                %>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>ID Turma</th>
                                <th>Código</th>
                                <th>Professor(a)</th>
                                <th>Disciplina</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (!listaTurmas.isEmpty()) {
                                    for (Turma t : listaTurmas) {
                            %>
                                <tr>
                                    <td><%= t.getId() %></td>
                                    <td><%= t.getCodigoTurma() %></td>
                                    <td><%= t.getProfessorNome() %></td>
                                    <td><%= t.getDisciplinaNome() %></td>
                                    <td>
                                        <form action="/aplicacaoMVC/aluno/InscricoesController" method="POST">
                                            <input type="hidden" name="id" value="<%= t.getId() %>">
                                            <input type="hidden" name="btEnviar" value="Incluir">
                                            <button type="submit" class="btn btn-primary btn-sm">Inscrever-se</button>
                                        </form>
                                    </td>
                                </tr>
                            <%
                                    }
                                } else {
                            %>
                                <tr>
                                    <td colspan="5" class="text-center">Nenhuma turma disponível para inscrição.</td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
