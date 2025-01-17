<%@page import="entidade.Professor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de Professores</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menuAdmin.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lista de Professores</h2>

                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Nome</th>
                                <th scope="col">E-mail</th>
                                <th scope="col">CPF</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");

                                for (Professor professor : listaProfessores) {
                                    out.println("<tr>");
                                    out.println("<th>" + professor.getId() + "</th>");
                                    out.println("<td>" + professor.getNome() + "</td>");
                                    out.println("<td>" + professor.getEmail() + "</td>");
                                    out.println("<td>" + professor.getCpf() + "</td>");
                                    %>
                            <td>
                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Alterar&id=<%=professor.getId()%>" class="btn btn-warning">Alterar</a>
                                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Excluir&id=<%=professor.getId()%>" class="btn btn-danger">Excluir</a>
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
