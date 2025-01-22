<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .card {
      margin-top: 20px;
    }
  </style>
</head>
<body>
  <div class="container">
    <jsp:include page="../comum/menu.jsp" />
    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card shadow-sm">
          <div class="card-body">
            <h3 class="card-title text-center mb-4">Login</h3>
            <%
              String msgError = (String) request.getAttribute("msgError");
              if (msgError != null && !msgError.isEmpty()) {
            %>
            <div class="alert alert-danger" role="alert"><%= msgError %></div>
            <%
              }
            %>
            <form action="/aplicacaoMVC/AutenticaController?acao=login" method="POST">
              <div class="mb-3">
                <label for="userType" class="form-label">Tipo de Usuário</label>
                <select name="userType" class="form-select" required>
                  <option value="administrador">Administrador</option>
                  <option value="aluno">Aluno</option>
                  <option value="professor">Professor</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="cpf" class="form-label">CPF</label>
                <input type="text" name="cpf" value="249.252.810-38" class="form-control">
              </div>
              <div class="mb-3">
                <label for="senha" class="form-label">Senha</label>
                <input type="password" name="senha" value="111" class="form-control">
              </div>
              <input type="submit" value="Enviar" class="btn btn-primary">
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
