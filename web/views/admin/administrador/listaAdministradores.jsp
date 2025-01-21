<%@page import="entidade.Administrador"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Administradores</title>
    <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="mt-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1>Área Restrita</h1>
                    <h2>Lista de Administradores</h2>
                </div>
                <a href="/aplicacaoMVC/admin/AdministradorController?acao=Incluir" class="btn btn-success">
                    Incluir Administrador
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Nome</th>
                            <th scope="col">CPF</th>
                            <th scope="col">Endereço</th>
                            <th scope="col">Aprovado</th>
                            <th scope="col">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Administrador> listaAdministradores = (ArrayList<Administrador>) request.getAttribute("listaAdministradores");
                            if(listaAdministradores != null && !listaAdministradores.isEmpty()) {
                                for(Administrador administrador : listaAdministradores) {
                        %>
                        <tr>
                            <th scope="row"><%= administrador.getId() %></th>
                            <td><%= administrador.getNome() %></td>
                            <td><%= administrador.getCpf() %></td>
                            <td><%= administrador.getEndereco() %></td>
                            <td><%= "S".equals(administrador.getAprovado()) ? "Sim" : "Não" %></td>
                            <td>
                                <a href="/aplicacaoMVC/admin/AdministradorController?acao=Alterar&id=<%= administrador.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
                                <a href="/aplicacaoMVC/admin/AdministradorController?acao=Excluir&id=<%= administrador.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="6" class="text-center">Nenhum administrador encontrado.</td>
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
