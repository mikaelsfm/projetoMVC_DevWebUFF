<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.*" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <%
                                    HttpSession sessao = request.getSession(false);
                                    if (sessao != null) {
                                        Administrador administradorLogado = (Administrador) sessao.getAttribute("administrador");
                                        Aluno alunoLogado = (Aluno) sessao.getAttribute("aluno");
                                        Professor professorLogado = (Professor) sessao.getAttribute("professor");

                                        if (administradorLogado != null) {
                                %>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/dashboard">Dashboard</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Listar">Alunos</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/ProfessorController?acao=Listar">Professores</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/AdministradorController?acao=Listar">Administrador</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar">Disciplinas</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Turmas</a>
                                            <a class="nav-link" href="/aplicacaoMVC/admin/RelatorioController?acao=Listar">Relatório</a>
                                            <a class="nav-link" href="/aplicacaoMVC/common/logOut">Logout</a>
                                <%
                                        } else if (alunoLogado != null) {
                                %>          
                                            <a class="nav-link" href="/aplicacaoMVC/aluno/InscricoesController?acao=Listar">Minhas Inscrições</a>
                                            <a class="nav-link" href="/aplicacaoMVC/aluno/InscricoesController?acao=Incluir">Inscrever em Disciplina/Turma</a>
                                            <a class="nav-link" href="/aplicacaoMVC/common/logOut">Logout</a>
                                <%
                                        } else if (professorLogado != null) {
                                %>
                                          
                                            <a class="nav-link" href="/aplicacaoMVC/professor/LancarNotasController?acao=Listar">
                                                Lançar Notas
                                            </a>
                                            <a class="nav-link" href="/aplicacaoMVC/professor/ListarAlunosController?acao=Listar">
                                                Listagem de Alunos
                                            </a>
                                            <a class="nav-link" href="/aplicacaoMVC/common/logOut">Logout</a>
                                <%
                                        }  else {
                                %>
                                            <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                                <%
                                        }
                                    }
                                %>
            </div>
        </div>
    </div>
</nav>