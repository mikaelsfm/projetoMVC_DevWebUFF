package controller.professor;

import entidade.Professor;
import entidade.Turma;
import model.TurmaDAO;
import model.AlunoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmas = turmaDAO.getTurmasPorProfessor(professorLogado.getId());
                request.setAttribute("listaTurmas", listaTurmas);
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
            int turmaId = Integer.parseInt(request.getParameter("turmaId"));
            int alunoId = Integer.parseInt(request.getParameter("alunoId"));
            BigDecimal nota = new BigDecimal(request.getParameter("nota"));

            TurmaDAO turmaDAO = new TurmaDAO();
            boolean isTurmaDoProfessor = turmaDAO.verificarTurmaDoProfessor(turmaId, professorLogado.getId());

            if (!isTurmaDoProfessor) {
                throw new RuntimeException("A turma selecionada não pertence ao professor.");
            }

            turmaDAO.lancarNota(turmaId, alunoId, nota);

            request.setAttribute("msgOperacaoRealizada", "Nota lançada com sucesso!");
            request.setAttribute("link", "/aplicacaoMVC/professor/LancarNotasController");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            System.out.println("Erro ao lançar nota: " + ex.getMessage());
            request.setAttribute("msgError", "Erro ao lançar nota. Verifique os dados e tente novamente.");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/erro.jsp");
            rd.forward(request, response);
        }
    }
}
