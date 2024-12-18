package controller.admin;

import entidade.Turma;
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

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = (String) request.getParameter("acao");
        Turma turma = new Turma();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;
        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmas = turmaDAO.getAll();
                request.setAttribute("listaTurmas", listaTurmas);

                rd = request.getRequestDispatcher("/views/admin/turma/listaTurmas.jsp");
                rd.forward(request, response);

                break;
            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                turma = turmaDAO.get(id);

                request.setAttribute("turma", turma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                rd.forward(request, response);
                break;
            case "Incluir":
                request.setAttribute("turma", turma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                rd.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int professorId = Integer.parseInt(request.getParameter("professorId"));
        int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        String codigoTurma = request.getParameter("codigoTurma");
        BigDecimal nota = new BigDecimal(request.getParameter("nota"));
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (codigoTurma.isEmpty() || nota == null) {
            Turma turma = new Turma();
            switch (btEnviar) {
                case "Alterar":
                case "Excluir":
                    try {
                        TurmaDAO turmaDAO = new TurmaDAO();
                        turma = turmaDAO.get(id);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        throw new RuntimeException("Falha em uma query para cadastro de Turma");
                    }
                    break;
            }

            request.setAttribute("turma", turma);
            request.setAttribute("acao", btEnviar);

            request.setAttribute("msgError", "É necessário preencher todos os campos");

            rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
            rd.forward(request, response);

        } else {
            Turma turma = new Turma(id, professorId, disciplinaId, alunoId, codigoTurma, nota);
            TurmaDAO turmaDAO = new TurmaDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        turmaDAO.inserir(turma);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;
                    case "Alterar":
                        turmaDAO.update(turma);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;
                    case "Excluir":
                        turmaDAO.delete(id);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (IOException | ServletException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha em uma query para cadastro de Turma");
            }
        }
    }
}
