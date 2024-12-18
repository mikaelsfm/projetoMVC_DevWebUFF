<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Disciplina"%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Comentário</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        Disciplina disciplina = (Disciplina) request.getAttribute("disciplina");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Disciplina</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Disciplina</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Disciplina</h1>");
                                break;
                        }

                    %>

                    <form action="/aplicacaoMVC/admin/DisciplinaController" method="POST">
                        <input type="hidden" name="id" value="<%=disciplina.getId()%>" class="form-control">

                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" name="nome" value="<%=disciplina.getNome()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> required>
                        </div>

                        <div class="mb-3">
                            <label for="requisito" class="form-label">Requisito</label>
                            <input type="text" name="requisito" value="<%=disciplina.getRequisito()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> >
                        </div>

                        <div class="mb-3">
                            <label for="ementa" class="form-label">Ementa</label>
                            <input type="text" name="ementa" value="<%=disciplina.getEmenta()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> >
                        </div>

                        <div class="mb-3">
                            <label for="carga_horaria" class="form-label">Carga Horária</label>
                            <input type="text" name="carga_horaria" value="<%=disciplina.getCargaHoraria()%>" class="form-control" <%= acao.equals("Excluir") ? "readonly" : ""%> required>
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="<%=acao%>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

</html>