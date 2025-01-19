package controller.admin;

import entidade.Professor;
import entidade.Turma;
import model.ProfessorDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TurmaDAO;

@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/ProfessorController"})
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Professor professor = new Professor();
        ProfessorDAO professorDAO = new ProfessorDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Professor> listaProfessores = professorDAO.getAll();
                request.setAttribute("listaProfessores", listaProfessores);
                rd = request.getRequestDispatcher("/views/admin/professor/listaProfessores.jsp");
                rd.forward(request, response);
                break;

            case "ListarTurmas":
                int professorId = Integer.parseInt(request.getParameter("professorId"));
                ArrayList<Turma> listaTurmas = turmaDAO.getTurmasPorProfessor(professorId);
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/professor/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "LancarNota":
                int turmaId = Integer.parseInt(request.getParameter("turmaId"));
                Turma turma = turmaDAO.get(turmaId);
                request.setAttribute("turma", turma);
                rd = request.getRequestDispatcher("/views/admin/professor/lancarNota.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                request.setAttribute("professor", professor);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                professor = professorDAO.get(id);
                request.setAttribute("professor", professor);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
                rd.forward(request, response);
                break;

            default:
                response.sendRedirect("/admin/ProfessorController?acao=Listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String btEnviar = request.getParameter("btEnviar");
        ProfessorDAO professorDAO = new ProfessorDAO();
        TurmaDAO turmaDAO = new TurmaDAO();

        if ("SalvarNota".equals(btEnviar)) {
            int turmaId = Integer.parseInt(request.getParameter("turmaId"));
            String[] alunoIds = request.getParameterValues("alunoId");
            String[] notas = request.getParameterValues("nota");

            try {
                for (int i = 0; i < alunoIds.length; i++) {
                    turmaDAO.atualizarNota(Integer.parseInt(alunoIds[i]), new BigDecimal(notas[i]));
                }
                response.sendRedirect("/admin/ProfessorController?acao=ListarTurmas&professorId=" + turmaId);
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao salvar nota: " + ex.getMessage());
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");

            try {
                if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                    throw new RuntimeException("Todos os campos são obrigatórios");
                }

                Professor professor = new Professor(id, nome, email, cpf, senha);

                switch (btEnviar) {
                    case "Incluir":
                        professorDAO.inserir(professor);
                        break;

                    case "Alterar":
                        professorDAO.update(professor);
                        break;

                    case "Excluir":
                        professorDAO.delete(id);
                        break;
                }

                response.sendRedirect("/admin/ProfessorController?acao=Listar");
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao processar ação no ProfessorController: " + ex.getMessage());
            }
        }
    }
}
