<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Professor"%>
<%@page import="entidade.Disciplina"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Turma</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        Turma turma = (Turma) request.getAttribute("turma");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Turma</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Turma</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Turma</h1>");
                                break;
                        }
                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError %>
                        </div>
                    <% } %>

                    <%
                        ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");
                        ArrayList<Disciplina> listaDisciplinas = (ArrayList<Disciplina>) request.getAttribute("listaDisciplinas");
                    %>

                    <form action="/aplicacaoMVC/admin/TurmaController" method="POST">
                        <input type="hidden" name="id" value="<%= turma.getId() %>">
                        <div class="mb-3">
                            <label for="professorId" class="form-label">Professor</label>
                            <select name="professorId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                <option value="">Selecione o Professor</option>
                                <%
                                    if (listaProfessores != null) {
                                        for (Professor p : listaProfessores) {
                                            String selected = (turma.getProfessorId() == p.getId()) ? "selected" : "";
                                %>
                                            <option value="<%= p.getId() %>" <%= selected %>>
                                                <%= p.getNome() %>
                                            </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">Disciplina</label>
                            <select name="disciplinaId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                <option value="">Selecione a Disciplina</option>
                                <%
                                    if (listaDisciplinas != null) {
                                        for (Disciplina d : listaDisciplinas) {
                                            String selected = (turma.getDisciplinaId() == d.getId()) ? "selected" : "";
                                %>
                                            <option value="<%= d.getId() %>" <%= selected %>>
                                                <%= d.getNome() %>
                                            </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="alunoId" class="form-label">ID do Aluno</label>
                            <input type="text" name="alunoId" class="form-control" value="<%= turma.getAlunoId() %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>
                        <div class="mb-3">
                            <label for="codigoTurma" class="form-label">Código da Turma</label>
                            <input type="text" name="codigoTurma" class="form-control" value="<%= turma.getCodigoTurma() %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>
                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" step="0.01" name="nota" class="form-control" value="<%= turma.getNota() %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>
                        <div>
                            <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>