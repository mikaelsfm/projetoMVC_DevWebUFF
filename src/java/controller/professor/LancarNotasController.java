package controller.professor;

import entidade.Aluno;
import entidade.Professor;
import entidade.Turma;
import model.TurmaDAO;
import model.AlunoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LancarNotasController", urlPatterns = {"/professor/LancarNotasController"})
public class LancarNotasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Professor professorLogado = (session != null) ? (Professor) session.getAttribute("professor") : null;
        if (professorLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController");
            return;
        }

        String acao = request.getParameter("acao");
        if (acao == null || acao.isEmpty()) {
            acao = "Listar";
        }

        TurmaDAO turmaDAO = new TurmaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
            case "SelecionarTurma":
                String turmaIdParam = request.getParameter("turmaId");
                if (turmaIdParam != null && !turmaIdParam.isEmpty()) {
                    try {
                        int turmaId = Integer.parseInt(turmaIdParam);

                        Turma turmaSelecionada = turmaDAO.get(turmaId);
                        if (turmaSelecionada != null) {
                            ArrayList<Aluno> listaAlunos = alunoDAO.getAlunosPorCodigoTurmaEDisciplina(
                                turmaSelecionada.getCodigoTurma(), 
                                turmaSelecionada.getDisciplinaId()
                            );
                            request.setAttribute("listaAlunos", listaAlunos);
                        }

                        request.setAttribute("turmaSelecionada", turmaId);

                    } catch (NumberFormatException ex) {
                        System.out.println("Erro ao converter turmaId: " + ex.getMessage());
                        request.setAttribute("msgError", "Parâmetro turmaId inválido.");
                    }
                }

                ArrayList<Turma> todasTurmas = turmaDAO.getTurmasPorProfessor(professorLogado.getId());
                Map<String, List<Turma>> turmasAgrupadas = todasTurmas.stream()
                    .collect(Collectors.groupingBy(
                        turma -> turma.getCodigoTurma() + " - " + turma.getDisciplinaNome()
                    ));

                request.setAttribute("turmasAgrupadas", turmasAgrupadas);

                rd = request.getRequestDispatcher("/views/professor/notas/formLancarNotas.jsp");
                rd.forward(request, response);
                break;

            default:
                response.sendRedirect("/aplicacaoMVC/professor/LancarNotasController?acao=Listar");
                break;
        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Professor professorLogado = (session != null) ? (Professor) session.getAttribute("professor") : null;
        if (professorLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController");
            return;
        }

        try {
            String turmaIdParam = request.getParameter("turmaId");
            String alunoIdParam = request.getParameter("alunoId");

            if (turmaIdParam == null || turmaIdParam.isEmpty() || alunoIdParam == null || alunoIdParam.isEmpty()) {
                throw new RuntimeException("Parâmetros inválidos: turmaId ou alunoId estão ausentes.");
            }

            int turmaId = Integer.parseInt(turmaIdParam);
            int alunoId = Integer.parseInt(alunoIdParam);

            String notaStr = request.getParameter("nota").replace(",", ".");
            BigDecimal nota = new BigDecimal(notaStr);

            TurmaDAO turmaDAO = new TurmaDAO();
            boolean isTurmaDoProfessor = turmaDAO.isTurmaDoProfessor(turmaId, professorLogado.getId());

            if (!isTurmaDoProfessor) {
                throw new RuntimeException("A turma selecionada não pertence ao professor.");
            }

            turmaDAO.lancarNota(turmaId, alunoId, nota);

            request.setAttribute("msgOperacaoRealizada", "Nota lançada com sucesso!");
            request.setAttribute("link", "/aplicacaoMVC/professor/LancarNotasController");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);
        } catch (RuntimeException | IOException | ServletException ex) {
            System.out.println("Erro ao lançar nota: " + ex.getMessage());
            request.setAttribute("msgError", "Erro ao lançar nota: " + ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);
        }

    }
}
 