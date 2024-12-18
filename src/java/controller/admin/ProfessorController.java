package controller.admin;

import entidade.Professor;
import model.ProfessorDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/ProfessorController"})
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Professor professor = new Professor();
        ProfessorDAO professorDAO = new ProfessorDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Professor> listaProfessores = professorDAO.getAll();
                request.setAttribute("listaProfessores", listaProfessores);
                rd = request.getRequestDispatcher("/views/admin/professor/listaProfessores.jsp");
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

            case "Incluir":
                request.setAttribute("professor", professor);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            Professor professor = new Professor();
            request.setAttribute("professor", professor);
            request.setAttribute("msgError", "Todos os campos são obrigatórios");
            request.setAttribute("acao", btEnviar);
            rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
            rd.forward(request, response);
        } else {
            Professor professor = new Professor(id, nome, email, cpf, senha);
            ProfessorDAO professorDAO = new ProfessorDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        professorDAO.inserir(professor);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;

                    case "Alterar":
                        professorDAO.update(professor);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;

                    case "Excluir":
                        professorDAO.delete(id);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Erro ao realizar operação com o professor");
            }
        }
    }
}
