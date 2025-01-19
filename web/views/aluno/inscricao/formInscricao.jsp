<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Form Inscrições</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />

            <div class="row mt-5">
                <div class="col-sm-12">
                    <%
                        String acao = (String) request.getAttribute("acao");
                        String msgError = (String) request.getAttribute("msgError");

                        if (acao == null) {
                            acao = "";
                        }

                        if ("Incluir".equals(acao)) {
                            out.println("<h1>Inscrever-se em Turmas</h1>");
                        } else {
                            out.println("<h1>Turmas Disponíveis</h1>");
                        }

                        if (msgError != null && !msgError.isEmpty()) {
                    %>
                            <div class="alert alert-danger" role="alert">
                                <%= msgError %>
                            </div>
                    <%
                        }

                        ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                        if (listaTurmas == null) {
                            listaTurmas = new ArrayList<>();
                        }
                    %>

                    <div class="mb-3">
                        <p>Selecione uma turma e clique em “Inscrever”.</p>
                    </div>

                    <%
                        if (!listaTurmas.isEmpty()) {
                            for (Turma t : listaTurmas) {
                    %>
                                <div class="row mb-2 p-2 border align-items-center">
                                    <div class="col-sm-2">
                                        <strong>ID Turma:</strong> <%= t.getId() %>
                                    </div>
                                    <div class="col-sm-2">
                                        <strong>Código:</strong> <%= t.getCodigoTurma() %>
                                    </div>
                                    <div class="col-sm-2">
                                        <strong>ProfID:</strong> <%= t.getProfessorId() %>
                                    </div>
                                    <div class="col-sm-2">
                                        <strong>DiscID:</strong> <%= t.getDisciplinaId() %>
                                    </div>
                                    <div class="col-sm-2">
                                        <strong>Nota:</strong> <%= t.getNota() %>
                                    </div>
                                    <div class="col-sm-2 text-end">
                                        <form action="/aplicacaoMVC/aluno/InscricoesController" method="POST">
                                            <input type="hidden" name="id" value="<%= t.getId() %>">
                                            <input type="submit" name="btEnviar" value="Incluir" class="btn btn-primary btn-sm">
                                        </form>
                                    </div>
                                </div>
                    <%
                            }
                        } else {
                    %>
                    <div class="alert alert-info">
                        Nenhuma turma disponível para inscrição.
                    </div>
                <%
                    }
                %>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>