<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Disciplina"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Disciplina</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    <style>
        .card { margin-top: 20px; }
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
                            Disciplina disciplina = (Disciplina) request.getAttribute("disciplina");
                            String acao = (String) request.getAttribute("acao");
                            String titulo;
                            switch (acao) {
                                case "Incluir":
                                    titulo = "Incluir Disciplina";
                                    break;
                                case "Alterar":
                                    titulo = "Alterar Disciplina";
                                    break;
                                case "Excluir":
                                    titulo = "Excluir Disciplina";
                                    break;
                                default:
                                    titulo = "Gerenciar Disciplina";
                            }
                        %>
                        <h1 class="card-title mb-4"><%= titulo %></h1>
                        <form action="/aplicacaoMVC/admin/DisciplinaController" method="POST">
                            <input type="hidden" name="id" value="<%= disciplina.getId() %>">
                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome</label>
                                <input type="text" name="nome" value="<%= disciplina.getNome() %>" 
                                    class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>
                            <div class="mb-3">
                                <label for="requisito" class="form-label">Requisito</label>
                                <input type="text" name="requisito" value="<%= disciplina.getRequisito() %>" 
                                    class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>
                            <div class="mb-3">
                                <label for="ementa" class="form-label">Ementa</label>
                                <input type="text" name="ementa" value="<%= disciplina.getEmenta() %>" 
                                    class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %>>
                            </div>
                            <div class="mb-3">
                                <label for="carga_horaria" class="form-label">Carga Horária</label>
                                <input type="text" name="carga_horaria" value="<%= disciplina.getCargaHoraria() %>" 
                                    class="form-control" <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                            </div>
                            <div class="d-flex justify-content-between">
                                <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar" class="btn btn-secondary">Retornar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
