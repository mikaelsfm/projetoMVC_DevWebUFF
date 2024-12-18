<%@page import="entidade.Disciplina"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de Disciplinas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lista de Disciplinas</h2>

                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
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

                                for (Disciplina disciplina : listaDisciplinas) {
                                    out.println("<tr>");
                                    out.println("<th>" + disciplina.getId() + "</th>");
                                    out.println("<td>" + disciplina.getNome() + "</td>");
                                    out.println("<td>" + disciplina.getRequisito() + "</td>");
                                    out.println("<td>" + disciplina.getEmenta() + "</td>");
                                    out.println("<td>" + disciplina.getCargaHoraria() + "</td>");
                                    %>
                            <td>
                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Alterar&id=<%=disciplina.getId()%>" class="btn btn-warning">Alterar</a>
                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Excluir&id=<%=disciplina.getId()%>" class="btn btn-danger">Excluir</a>
                            </td>
                            <%  
                                    out.println("</tr>");
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