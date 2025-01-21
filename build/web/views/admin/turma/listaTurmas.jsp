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
        <title>Lista de Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            
            <div class="mt-5">
                <h1>Lista de Turmas</h1>
                <a href="/aplicacaoMVC/admin/TurmaController?acao=Incluir" class="btn btn-primary mb-3">
                    Incluir Nova Turma
                </a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Código da Turma</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Disciplina</th>
                                <th scope="col">Nota</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                                
                                if (listaTurmas != null) {
                                    for (Turma turma : listaTurmas) {
                            %>
                            <tr>
                                <td><%= turma.getId() %></td>
                                <td><%= turma.getCodigoTurma() %></td>
                                <td><%= turma.getProfessorNome() %></td>
                                <td><%= turma.getDisciplinaNome() %></td>
                                <td><%= turma.getNota() %></td>
                                <td>
                                    <a href="/aplicacaoMVC/admin/TurmaController?acao=Alterar&id=<%= turma.getId() %>" 
                                       class="btn btn-sm btn-primary">
                                        Alterar
                                    </a>
                                    <a href="/aplicacaoMVC/admin/TurmaController?acao=Excluir&id=<%= turma.getId() %>" 
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Tem certeza que deseja excluir esta turma?');">
                                        Excluir
                                    </a>
                                </td>
                            </tr>
                            <%
                                    }
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
