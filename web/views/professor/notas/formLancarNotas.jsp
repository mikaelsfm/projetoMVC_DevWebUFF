<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Aluno"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lançar Notas</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .card {
            margin-top: 20px;
        }
        .alert {
            margin-top: 10px;
        }
        h1 {
            margin-bottom: 20px;
            color: #007bff;
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
                        <h1 class="card-title">Lançar Notas</h1>
                        <% String msgError = (String) request.getAttribute("msgError");
                           if (msgError != null && !msgError.isEmpty()) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError %>
                        </div>
                        <% } %>
                                
                        <form action="/aplicacaoMVC/professor/LancarNotasController" method="GET" class="mb-4">
                        <input type="hidden" name="acao" value="SelecionarTurma">
                        <div class="mb-3">
                            <label for="turmaId" class="form-label">Selecione a Turma</label>
                            <select id="turmaId" name="turmaId" class="form-select" onchange="this.form.submit()" required>
                                <option value="">Selecione uma Turma</option>
                                <%
                                    Map<String, List<Turma>> turmasAgrupadas = (Map<String, List<Turma>>) request.getAttribute("turmasAgrupadas");
                                    if (turmasAgrupadas != null) {
                                        for (Map.Entry<String, List<Turma>> entry : turmasAgrupadas.entrySet()) {
                                            String descricaoTurma = entry.getKey();
                                            List<Turma> turmas = entry.getValue();
                                            int idRepresentativo = turmas.get(0).getId();
                                            String selected = (request.getAttribute("turmaSelecionada") != null &&
                                                               ((int) request.getAttribute("turmaSelecionada")) == idRepresentativo)
                                                              ? "selected" : "";
                                %>
                                <option value="<%= idRepresentativo %>" <%= selected %>>
                                    <%= descricaoTurma %>
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
                                    ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                                    if (listaAlunos != null && !listaAlunos.isEmpty()) {
                                        for (Aluno aluno : listaAlunos) {
                                %>
                                <option value="<%= aluno.getId() %>">
                                    <%= aluno.getNome() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" step="0.1" name="nota" class="form-control" required>
                        </div>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary">Lançar Nota</button>
                            <a href="/aplicacaoMVC/professor/LancarNotasController?acao=Listar" class="btn btn-secondary">Retornar</a>
                        </div>
                    </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>