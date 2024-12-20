<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Turma</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
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
                        <%= msgError%>
                    </div>
                    <% }%>

                    <form action="/aplicacaoMVC/admin/TurmaController" method="POST">
                        <input type="hidden" name="id" value="<%=turma.getId()%>" class="form-control">
                        <div class="mb-3">
                            <label for="professorId" class="form-label">ID do Professor</label>
                            <input type="number" name="professorId" <%= acao.equals("Excluir") ? "Readonly" : ""%> value="<%=turma.getProfessorId()%>" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">ID da Disciplina</label>
                            <input type="number" name="disciplinaId" <%= acao.equals("Excluir") ? "Readonly" : ""%> value="<%=turma.getDisciplinaId()%>" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="alunoId" class="form-label">ID do Aluno</label>
                            <input type="number" name="alunoId" <%= acao.equals("Excluir") ? "Readonly" : ""%> value="<%=turma.getAlunoId()%>" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="codigoTurma" class="form-label">Código da Turma</label>
                            <input type="text" name="codigoTurma" <%= acao.equals("Excluir") ? "Readonly" : ""%> value="<%=turma.getCodigoTurma()%>" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" step="0.01" name="nota" <%= acao.equals("Excluir") ? "Readonly" : ""%> value="<%=turma.getNota()%>" class="form-control">
                        </div>
                        <div>
                            <input type="submit" name="btEnviar" value="<%=acao%>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

</html>