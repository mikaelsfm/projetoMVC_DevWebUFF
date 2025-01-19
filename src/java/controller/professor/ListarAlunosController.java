package controller.professor;

import entidade.Professor;
import entidade.Turma;
import model.TurmaDAO;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ListarAlunosController", urlPatterns = {"/professor/ListarAlunosController"})
public class ListarAlunosController extends HttpServlet {

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
                ArrayList<Turma> listaTurmas = turmaDAO.getTurmasPorProfessorComAlunos(professorLogado.getId());
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/professor/notas/listaNotas.jsp");
                rd.forward(request, response);
                break;

            default:
                response.sendRedirect("/aplicacaoMVC/professor/ListarAlunosController?acao=Listar");
                break;
        }
    }
}
