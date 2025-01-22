<%@page import="entidade.Aluno"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="entidade.Turma"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Lista de Inscrições</title>
  <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
  <style>
    .table-actions a {
      margin-right: 5px;
    }
    h1, h2 {
      color: #333;
    }
    .btn-success {
      background-color: #28a745;
      border-color: #28a745;
    }
    .btn-success:hover {
      background-color: #218838;
      border-color: #1e7e34;
    }
  </style>
</head>
<body>
  <div class="container">
    <jsp:include page="../../comum/menu.jsp" />
  </div>

  <div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <h1 class="mb-0">Área Restrita (Aluno)</h1>
        <h3 class="mb-0 mt-2 text-muted">
        <%
          Aluno alunoLogado = (Aluno) session.getAttribute("aluno");
          if (alunoLogado != null) {
              out.print(alunoLogado.getNome());
          } else {
              out.print("Aluno não identificado");
          }
        %>
      </h3>
        <p class="text-muted">Minhas Inscrições</p>
      </div>
      <a href="/aplicacaoMVC/aluno/InscricoesController?acao=Incluir" class="btn btn-success">
        Inscrever em Nova Turma
      </a>
    </div>

    <div class="table-responsive">
      <table class="table table-hover table-striped table-bordered">
        <thead class="table-dark">
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Código da Turma</th>
            <th scope="col">ID do Professor</th>
            <th scope="col">ID da Disciplina</th>
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
            <th><%= turma.getId() %></th>
            <td><%= turma.getCodigoTurma() %></td>
            <td><%= turma.getProfessorNome() %></td>
            <td><%= turma.getDisciplinaNome() %></td>
            <td><%= turma.getNota() %></td>
            <td>
              <a href="/aplicacaoMVC/aluno/InscricoesController?acao=Excluir&id=<%=turma.getId()%>"
                class="btn btn-danger btn-sm"
                onclick="return confirm('Tem certeza que deseja excluir esta turma?');">
               Remover Inscrição
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

  <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
