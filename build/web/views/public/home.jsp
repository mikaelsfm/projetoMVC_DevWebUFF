<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="#">
  <title>Login</title>
  <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
  <style>
    .welcome-card {
      max-width: 500px;
      margin: auto;
    }
    .welcome-card .card-header {
      background-color: #007bff;
      color: #fff;
      text-align: center;
    }
    .welcome-card .card-body {
      text-align: center;
    }
  </style>
</head>
<body>
  <div class="container">
    <jsp:include page="../comum/menu.jsp" />
    <div class="welcome-card mt-5 card shadow-sm">
      <div class="card-header">
        <h3>Bem-vindo(a)!</h3>
      </div>
      <div class="card-body">
        <p class="lead">Seja bem-vindo(a) ao sistema de controle escolar</p>
      </div>
    </div>
  </div>
  <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
