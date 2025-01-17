package controller.aluno;

import entidade.Aluno;
import entidade.Turma;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.TurmaDAO;

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
        TurmaDAO turmaDAO = new TurmaDAO();
        Turma turma = new Turma();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmasAluno = turmaDAO.getAllDoAluno(alunoLogado.getId());
                request.setAttribute("listaTurmas", listaTurmasAluno);

                rd = request.getRequestDispatcher("/views/aluno/inscricao/listaInscricoes.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                if (request.getParameter("id") != null) {
                    int idTurma = Integer.parseInt(request.getParameter("id"));
                    turma = turmaDAO.get(idTurma);
                    request.setAttribute("turma", turma);
                } else {
                    ArrayList<Turma> turmasDisponiveis = turmaDAO.getVagasDisponiveis();
                    request.setAttribute("listaTurmas", turmasDisponiveis);
                }
                request.setAttribute("acao", "Incluir");
                rd = request.getRequestDispatcher("/views/aluno/inscricao/formInscricao.jsp");
                rd.forward(request, response);
                break;

            case "Excluir":
                int idTurma = Integer.parseInt(request.getParameter("id"));
                turma = turmaDAO.get(idTurma);
                request.setAttribute("turma", turma);
                request.setAttribute("acao", "Excluir");
                rd = request.getRequestDispatcher("/views/aluno/inscricao/formInscricao.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Aluno alunoLogado = (session != null) ? (Aluno) session.getAttribute("aluno") : null;
        if (alunoLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController");
            return;
        }

        String btEnviar = request.getParameter("btEnviar");

        String idStr = request.getParameter("id");
        int idTurma = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;

        RequestDispatcher rd;
        TurmaDAO turmaDAO = new TurmaDAO();

        try {
            switch (btEnviar) {
                case "Incluir":
                    Turma turmaIncluir = turmaDAO.get(idTurma);
                    if (turmaIncluir.getId() == 0) {
                        request.setAttribute("msgOperacaoRealizada", "Turma inválida.");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    int ocupadas = turmaDAO.countOcupadasPorCodigo(turmaIncluir.getCodigoTurma());
                    if (ocupadas >= 2) {
                        request.setAttribute("msgOperacaoRealizada", "Turma lotada! (máx 2)");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    if (turmaIncluir.getAlunoId() != 0) {
                        request.setAttribute("msgOperacaoRealizada", "Essa vaga já foi ocupada por outro aluno!");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    turmaDAO.inscreverAluno(idTurma, alunoLogado.getId());
                    request.setAttribute("msgOperacaoRealizada", "Inscrição realizada com sucesso!");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    Turma turmaRemover = turmaDAO.get(idTurma);
                    if (turmaRemover.getNota().compareTo(BigDecimal.ZERO) > 0) {
                        request.setAttribute("msgOperacaoRealizada", "Não é possível remover inscrição após lançamento de nota!");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    if (turmaRemover.getAlunoId() != alunoLogado.getId()) {
                        request.setAttribute("msgOperacaoRealizada", "Você não está inscrito nessa turma!");
                        request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                        rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    turmaDAO.removerAluno(idTurma);
                    request.setAttribute("msgOperacaoRealizada", "Inscrição removida com sucesso!");
                    request.setAttribute("link", "/aplicacaoMVC/aluno/InscricaoController?acao=Listar");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha ao executar ação em InscricaoController");
        }
    }
}
