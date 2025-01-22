package controller.aluno;

import entidade.Aluno;
import entidade.Turma;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.TurmaDAO;
import model.ProfessorDAO;
import model.DisciplinaDAO;
import model.AlunoDAO;

@WebServlet(name = "InscricoesController", urlPatterns = {"/aluno/InscricoesController"})
public class InscricoesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Aluno alunoLogado = (session != null) ? (Aluno) session.getAttribute("aluno") : null;
        if (alunoLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController");
            return;
        }

        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "";
        }
        TurmaDAO turmaDAO = new TurmaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmasAluno = turmaDAO.getAllDoAluno(alunoLogado.getId());
                preencherNomes(listaTurmasAluno, professorDAO, disciplinaDAO); // Adiciona os nomes

                request.setAttribute("listaTurmas", listaTurmasAluno);
                rd = request.getRequestDispatcher("/views/aluno/inscricao/listaInscricoes.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                ArrayList<Turma> turmasDisponiveis = turmaDAO.getTurmasDisponiveisParaAluno(alunoLogado.getId());

                for (Turma turma : turmasDisponiveis) {
                    if (turma.getProfessorId() != 0 && (turma.getProfessorNome() == null || turma.getProfessorNome().isEmpty())) {
                        turma.setProfessorNome(professorDAO.get(turma.getProfessorId()).getNome());
                    }
                    if (turma.getDisciplinaId() != 0 && (turma.getDisciplinaNome() == null || turma.getDisciplinaNome().isEmpty())) {
                        turma.setDisciplinaNome(disciplinaDAO.getDisciplina(turma.getDisciplinaId()).getNome());
                    }
                }
                
                preencherNomes(turmasDisponiveis, professorDAO, disciplinaDAO);

                request.setAttribute("listaTurmas", turmasDisponiveis);
                rd = request.getRequestDispatcher("/views/aluno/inscricao/formInscricao.jsp");
                rd.forward(request, response);
                break;

            case "Excluir":
                int idTurma = Integer.parseInt(request.getParameter("id"));
                Turma turmaRemover = turmaDAO.get(idTurma);
                if (turmaRemover.getNota().compareTo(BigDecimal.ZERO) > 0) {
                    request.setAttribute("msgOperacaoRealizada", 
                            "Não é possível remover inscrição após lançamento de nota!");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    return;
                }
                if (turmaRemover.getAlunoId() != alunoLogado.getId()) {
                    request.setAttribute("msgOperacaoRealizada", "Você não está inscrito nessa turma!");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    return;
                }
                turmaDAO.removerAluno(idTurma);

                ArrayList<Turma> listaTurmasAtualizada = turmaDAO.getAllDoAluno(alunoLogado.getId());
                preencherNomes(listaTurmasAtualizada, professorDAO, disciplinaDAO);

                request.setAttribute("listaTurmas", listaTurmasAtualizada);
                request.setAttribute("msgOperacaoRealizada", "Inscrição removida com sucesso!");
                rd = request.getRequestDispatcher("/views/aluno/inscricao/listaInscricoes.jsp");
                rd.forward(request, response);
                break;

            default:
                response.sendRedirect("/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            Aluno alunoLogado = (session != null) ? (Aluno) session.getAttribute("aluno") : null;
            if (alunoLogado == null) {
                response.sendRedirect("/aplicacaoMVC/AutenticaController");
                return;
            }

            String btEnviar = request.getParameter("btEnviar");
            TurmaDAO turmaDAO = new TurmaDAO();
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    int idTurma = Integer.parseInt(request.getParameter("id"));

                    int totalTurmasAluno = turmaDAO.countTurmasPorAluno(alunoLogado.getId());
                    if (totalTurmasAluno >= 2) {
                        request.setAttribute("msgOperacaoRealizada", 
                            "Erro: Você já está inscrito em duas turmas. Não é possível se inscrever em mais.");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    try {
                        turmaDAO.duplicarTurmaParaNovoAluno(idTurma, alunoLogado.getId());
                        request.setAttribute("msgOperacaoRealizada", "Inscrição realizada com sucesso!");
                    } catch (RuntimeException ex) {
                        request.setAttribute("msgOperacaoRealizada", "Erro: " + ex.getMessage());
                    }
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idTurmaExcluir = Integer.parseInt(request.getParameter("id"));
                    Turma turmaRemover = turmaDAO.get(idTurmaExcluir);
                    if (turmaRemover.getNota().compareTo(BigDecimal.ZERO) > 0) {
                        request.setAttribute("msgOperacaoRealizada", 
                            "Não é possível remover inscrição após lançamento de nota!");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                        RequestDispatcher rdEx = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rdEx.forward(request, response);
                        return;
                    }
                    if (turmaRemover.getAlunoId() != alunoLogado.getId()) {
                        request.setAttribute("msgOperacaoRealizada", "Você não está inscrito nessa turma!");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                        RequestDispatcher rdEx = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rdEx.forward(request, response);
                        return;
                    }
                    turmaDAO.removerAluno(idTurmaExcluir);
                    request.setAttribute("msgOperacaoRealizada", "Inscrição removida com sucesso!");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                    RequestDispatcher rdEx = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rdEx.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("msgError", "Erro ao processar a inscrição. Por favor, tente novamente.");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);
        }
    }


    private void preencherNomes(ArrayList<Turma> listaTurmas, ProfessorDAO professorDAO, DisciplinaDAO disciplinaDAO) {
        for (Turma turma : listaTurmas) {
            if (turma.getProfessorId() != 0) {
                turma.setProfessorNome(professorDAO.get(turma.getProfessorId()).getNome());
            } else {
                turma.setProfessorNome("(Sem professor)");
            }

            if (turma.getDisciplinaId() != 0) {
                turma.setDisciplinaNome(disciplinaDAO.getDisciplina(turma.getDisciplinaId()).getNome());
            } else {
                turma.setDisciplinaNome("(Sem disciplina)");
            }
        }
    }
}
