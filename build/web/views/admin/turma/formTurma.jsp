<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Professor"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Aluno"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Turma</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .card { margin-top: 20px; }
        .alert { margin-top: 10px; }
    </style>
</head>
<body>
<div class="container">
    <jsp:include page="../../comum/menu.jsp" />
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <%
                        Turma turma = (Turma) request.getAttribute("turma");
                        String acao = (String) request.getAttribute("acao");
                        String titulo;
                        switch (acao) {
                            case "Incluir": titulo = "Incluir Turma"; break;
                            case "Alterar": titulo = "Alterar Turma"; break;
                            case "Excluir": titulo = "Excluir Turma"; break;
                            default: titulo = "Gerenciar Turma";
                        }
                        String msgError = (String) request.getAttribute("msgError");
                    %>
                    <h1 class="card-title mb-4"><%= titulo %></h1>
                    <% if (msgError != null && !msgError.isEmpty()) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= msgError %>
                        </div>
                    <% } %>
                    <%
                        ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");
                        ArrayList<Disciplina> listaDisciplinas = (ArrayList<Disciplina>) request.getAttribute("listaDisciplinas");
                        ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                    %>
                    <form action="/aplicacaoMVC/admin/TurmaController" method="POST">
                        <input type="hidden" name="id" value="<%= turma.getId() %>">
                        <div class="mb-3">
                            <label for="professorId" class="form-label">Professor</label>
                            <select name="professorId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                <option value="">Selecione o Professor</option>
                                <% if (listaProfessores != null) {
                                    for (Professor p : listaProfessores) {
                                        String selected = (turma.getProfessorId() == p.getId()) ? "selected" : ""; %>
                                <option value="<%= p.getId() %>" <%= selected %>><%= p.getNome() %></option>
                                <%   }
                                   } %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">Disciplina</label>
                            <select name="disciplinaId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                <option value="">Selecione a Disciplina</option>
                                <% if (listaDisciplinas != null) {
                                    for (Disciplina d : listaDisciplinas) {
                                        String selected = (turma.getDisciplinaId() == d.getId()) ? "selected" : ""; %>
                                <option value="<%= d.getId() %>" <%= selected %>><%= d.getNome() %></option>
                                <%   }
                                   } %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="alunoId" class="form-label">Aluno</label>
                            <select name="alunoId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %>>
                                <option value="">Selecione um Aluno</option>
                                <% 
                                if (listaAlunos != null) {
                                    for (Aluno aluno : listaAlunos) {
                                        String selected = (turma.getAlunoId() == aluno.getId()) ? "selected" : ""; 
                                %>
                                <option value="<%= aluno.getId() %>" <%= selected %>><%= aluno.getNome() %></option>
                                <% 
                                    }
                                } 
                                %>
                            </select>
                            <% if (acao.equals("Excluir")) { %>
                                <input type="hidden" name="alunoId" value="<%= turma.getAlunoId() %>">
                            <% } %>
                        </div>
                        <div class="mb-3">
                            <label for="codigoTurma" class="form-label">Código da Turma</label>
                            <input type="text" name="codigoTurma" class="form-control" value="<%= turma.getCodigoTurma() %>" <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>
                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" step="0.01" name="nota" class="form-control" value="<%= turma.getNota() %>" <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>
                        <div class="d-flex justify-content-between">
                            <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar" class="btn btn-secondary">Retornar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
