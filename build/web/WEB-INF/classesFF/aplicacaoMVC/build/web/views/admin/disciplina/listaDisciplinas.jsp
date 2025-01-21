<%@page import="entidade.Disciplina"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Disciplinas</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="mt-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1>Área Restrita</h1>
                    <h2>Lista de Disciplinas</h2>
                </div>
                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Incluir" class="btn btn-success">
                    Incluir Disciplina
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Requisito</th>
                            <th scope="col">Ementa</th>
                            <th scope="col">Carga Horária</th>
                            <th scope="col">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Disciplina> listaDisciplinas = (ArrayList<Disciplina>) request.getAttribute("listaDisciplinas");
                            if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
                                for (Disciplina disciplina : listaDisciplinas) {
                        %>
                        <tr>
                            <th scope="row"><%= disciplina.getId() %></th>
                            <td><%= disciplina.getNome() %></td>
                            <td><%= disciplina.getRequisito() %></td>
                            <td><%= disciplina.getEmenta() %></td>
                            <td><%= disciplina.getCargaHoraria() %></td>
                            <td>
                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Alterar&id=<%= disciplina.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Excluir&id=<%= disciplina.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="6" class="text-center">Nenhuma disciplina encontrada.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
