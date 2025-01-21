    package controller.professor;

import entidade.Aluno;
    import entidade.Professor;
    import entidade.Turma;
    import model.TurmaDAO;

    import java.io.IOException;
    import java.util.ArrayList;
    import javax.servlet.RequestDispatcher;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.*;
import model.AlunoDAO;

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
            AlunoDAO alunoDAO = new AlunoDAO();
            RequestDispatcher rd;

            switch (acao) {
                case "Listar":
                    ArrayList<Turma> listaTurmas = turmaDAO.getTurmasPorProfessorComAlunos(professorLogado.getId());
                    request.setAttribute("listaTurmas", listaTurmas);
                    rd = request.getRequestDispatcher("/views/professor/notas/listaNotas.jsp");
                    rd.forward(request, response);
                    break;

                case "SelecionarTurma":
                    int turmaId = Integer.parseInt(request.getParameter("turmaId"));
                    ArrayList<Turma> listaTurmasParaDropdown = turmaDAO.getTurmasPorProfessor(professorLogado.getId());
                    ArrayList<Aluno> listaAlunos = alunoDAO.getAlunosPorTurma(turmaId);

                    request.setAttribute("listaTurmas", listaTurmasParaDropdown);
                    request.setAttribute("listaAlunos", listaAlunos);
                    request.setAttribute("turmaSelecionada", turmaId); // Turma atualmente selecionada

                    rd = request.getRequestDispatcher("/views/professor/notas/formLancarNotas.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/professor/LancarNotasController?acao=Listar");
                    break;
            }
        }
    }
