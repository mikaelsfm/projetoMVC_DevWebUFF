<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="#">
    <title>Área Restrita</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container mt-5">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="mt-4">
            <h1 class="text-primary">Área Restrita</h1>
            <%
                Administrador administradorLogado = (Administrador) session.getAttribute("administrador");
                out.println("<h3 class='text-success'>Usuário logado com sucesso</h3>");
                out.println("<h2>Nome: " + administradorLogado.getNome() + "</h2>");
            %>
            
            <!-- Formulário para adicionar aluno -->
            <div class="card mt-4 shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h4>Adicionar Aluno</h4>
                </div>
                <div class="card-body">
                    <form action="adicionarAluno.jsp" method="post">
                        <div class="mb-3">
                            <label for="id" class="form-label">ID:</label>
                            <input type="number" id="id" name="id" class="form-control" required8>
                        </div>
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome:</label>
                            <input type="text" id="nome" name="nome" class="form-control" maxlength="50" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">E-mail:</label>
                            <input type="email" id="email" name="email" class="form-control" maxlength="50" required>
                        </div>
                        <div class="mb-3">
                            <label for="celular" class="form-label">Celular:</label>
                            <input type="text" id="celular" name="celular" class="form-control" maxlength="14" placeholder="(99) 99999-9999" required>
                        </div>
                        <div class="mb-3">
                            <label for="cpf" class="form-label">CPF:</label>
                            <input type="text" id="cpf" name="cpf" class="form-control" maxlength="14" placeholder="999.999.999-99" required>
                        </div>
                        <div class="mb-3">
                            <label for="senha" class="form-label">Senha:</label>
                            <input type="password" id="senha" name="senha" class="form-control" maxlength="255" required>
                        </div>
                        <div class="mb-3">
                            <label for="endereco" class="form-label">Endereço:</label>
                            <input type="text" id="endereco" name="endereco" class="form-control" maxlength="50">
                        </div>
                        <div class="mb-3">
                            <label for="cidade" class="form-label">Cidade:</label>
                            <input type="text" id="cidade" name="cidade" class="form-control" maxlength="30">
                        </div>
                        <div class="mb-3">
                            <label for="bairro" class="form-label">Bairro:</label>
                            <input type="text" id="bairro" name="bairro" class="form-control" maxlength="30">
                        </div>
                        <div class="mb-3">
                            <label for="cep" class="form-label">CEP:</label>
                            <input type="text" id="cep" name="cep" class="form-control" maxlength="9" placeholder="99999-999">
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Salvar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>

</html>
