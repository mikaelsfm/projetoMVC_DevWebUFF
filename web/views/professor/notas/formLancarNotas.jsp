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
        <title>Lançar Notas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            
            <div class="mt-5">
                <h1>Lançar Notas</h1>

                <%
                    String msgError = (String) request.getAttribute("msgError");
                    if (msgError != null && !msgError.isEmpty()) {
                %>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError %>
                    </div>
                <%
                    }
                    ArrayList<Turma> turmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                %>

                <form action="/aplicacaoMVC/professor/LancarNotasController" method="GET">
                    <input type="hidden" name="acao" value="SelecionarTurma">
                    <div class="mb-3">
                        <label for="turmaId" class="form-label">Selecione a Turma</label>
                        <select id="turmaId" name="turmaId" class="form-select" onchange="this.form.submit()" required>
                            <option value="">Selecione uma Turma</option>
                            <%
                                if (turmas != null) {
                                    for (Turma turma : turmas) {
                                        String selected = (request.getAttribute("turmaSelecionada") != null &&
                                                           ((int) request.getAttribute("turmaSelecionada")) == turma.getId())
                                                          ? "selected" : "";
                            %>
                                        <option value="<%= turma.getId() %>" <%= selected %>>
                                            <%= turma.getCodigoTurma() %> - <%= turma.getDisciplinaNome() %>
                                        </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </form>

                <form action="/aplicacaoMVC/professor/LancarNotasController" method="POST">
                    <input type="hidden" name="turmaId" value="<%= request.getAttribute("turmaSelecionada") %>">

                    <div class="mb-3">
                        <label for="alunoId" class="form-label">Selecione o Aluno</label>
                        <select name="alunoId" class="form-select" required>
                            <option value="">Selecione um Aluno</option>
                            <%
                                ArrayList<Aluno> alunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                                if (alunos != null && !alunos.isEmpty()) {
                                    for (Aluno aluno : alunos) {
                            %>
                                        <option value="<%= aluno.getId() %>">
                                            <%= aluno.getNome() %>
                                        </option>
                            <%
                                    }
                                } else {
                            %>
                                <option value="">Nenhum aluno cadastrado nesta turma</option>
                            <%
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="nota" class="form-label">Nota</label>
                        <input type="number" step="0.1" name="nota" class="form-control" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Lançar Nota</button>
                </form>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
