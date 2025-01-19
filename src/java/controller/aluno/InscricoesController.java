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
            acao = "";  // Define um valor padrão
        }
        TurmaDAO turmaDAO = new TurmaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        RequestDispatcher rd;
        Turma turma = new Turma();

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmasAluno = turmaDAO.getAllDoAluno(alunoLogado.getId());
                for (Turma t : listaTurmasAluno) {
                    if (t.getProfessorId() != 0) {
                        t.setProfessorNome(professorDAO.get(t.getProfessorId()).getNome());
                    } else {
                        t.setProfessorNome("(Sem professor)");
                    }

                    if (t.getDisciplinaId() != 0) {
                        t.setDisciplinaNome(disciplinaDAO.getDisciplina(t.getDisciplinaId()).getNome());
                    } else {
                        t.setDisciplinaNome("(Sem disciplina)");
                    }

                    if (t.getAlunoId() == 0) {
                        t.setAlunoNome("Sem Aluno");
                    } else {
                        t.setAlunoNome(alunoDAO.get(t.getAlunoId()).getNome());
                    }
                }

                request.setAttribute("listaTurmas", listaTurmasAluno);
                rd = request.getRequestDispatcher("/views/aluno/inscricao/listaInscricoes.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                ArrayList<Turma> turmasDisponiveis = turmaDAO.getVagasDisponiveis();
                request.setAttribute("listaTurmas", turmasDisponiveis);
                request.setAttribute("acao", "Incluir");
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
                for (Turma t : listaTurmasAtualizada) {
                    if (t.getProfessorId() != 0) {
                        t.setProfessorNome(professorDAO.get(t.getProfessorId()).getNome());
                    } else {
                        t.setProfessorNome("(Sem professor)");
                    }
                    if (t.getDisciplinaId() != 0) {
                        t.setDisciplinaNome(disciplinaDAO.getDisciplina(t.getDisciplinaId()).getNome());
                    } else {
                        t.setDisciplinaNome("(Sem disciplina)");
                    }
                }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try{
        HttpSession session = request.getSession(false);
        Aluno alunoLogado = (session != null) ? (Aluno) session.getAttribute("aluno") : null;
        if (alunoLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController");
            return;
        }
        
        String btEnviar = request.getParameter("btEnviar");
        TurmaDAO turmaDAO = new TurmaDAO();

        switch (btEnviar) {
            case "Incluir":
                int idTurma = Integer.parseInt(request.getParameter("id"));
                Turma turmaIncluir = turmaDAO.get(idTurma);
                if (turmaIncluir.getId() == 0 || turmaDAO.countOcupadasPorCodigo(turmaIncluir.getCodigoTurma()) >= 2 || turmaIncluir.getAlunoId() != 0) {
                    request.setAttribute("msgOperacaoRealizada", "Erro ao realizar a inscrição.");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                    RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    return;
                }
                turmaDAO.inscreverAluno(idTurma, alunoLogado.getId());
                request.setAttribute("msgOperacaoRealizada", "Inscrição realizada com sucesso!");
                request.setAttribute("link", "/aplicacaoMVC/aluno/InscricoesController?acao=Listar");
                RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);
                break;
        }
        } catch (IOException | NumberFormatException | ServletException ex) {
            System.out.println("Erro ao processar inscrição: " + ex.getMessage());
            request.setAttribute("msgError", "Ocorreu um erro ao processar a inscrição. Por favor, tente novamente.");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/erro.jsp");
            rd.forward(request, response);
        }
    }
}