<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Área Restrita Administrador</title>
  <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #eef2f5;
    }
    .info-box {
      max-width: 600px;
      margin: 30px auto;
      padding: 20px;
      background-color: #ffffff;
      border-radius: 8px;
      border: 1px solid #ddd;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    .info-box h1 {
      font-size: 1.8rem;
      color: #007bff;
    }
    .info-box h3 {
      font-size: 1.3rem;
      color: #28a745;
    }
    .info-box h2 {
      font-size: 1.5rem;
      margin-top: 15px;
      color: #333;
    }
  </style>
</head>
<body>
  <div class="container">
    <jsp:include page="../../comum/menu.jsp" />
    <div class="info-box text-center">
      <h1>Área Restrita - Administrador</h1>
      <%
        Administrador administradorLogado = (Administrador) session.getAttribute("administrador");
        if (administradorLogado != null) {
      %>
      <h3>Usuário logado com sucesso</h3>
      <h2>Nome: <%= administradorLogado.getNome() %></h2>
      <%
        } else {
      %>
      <h3 class="text-danger">Erro: Usuário não encontrado.</h3>
      <%
        }
      %>
    </div>
  </div>
  <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
