package controller.admin;

import entidade.Disciplina;
import model.DisciplinaDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/admin/DisciplinaController"})
public class DisciplinaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Disciplina disciplina = new Disciplina();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Disciplina> listaDisciplinas = disciplinaDAO.ListaDeDisciplinas();
                request.setAttribute("listaDisciplinas", listaDisciplinas);
                rd = request.getRequestDispatcher("/views/admin/disciplina/listaDisciplinas.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                disciplina = disciplinaDAO.getDisciplina(id);
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String requisito = request.getParameter("requisito");
        String ementa = request.getParameter("ementa");
        String cargaHoraria = request.getParameter("carga_horaria");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (nome.isEmpty() || cargaHoraria.isEmpty()) {
            Disciplina disciplina = new Disciplina();
            switch (btEnviar) {
                case "Alterar":
                case "Excluir":
                    try {
                        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                        disciplina = disciplinaDAO.getDisciplina(id);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        throw new RuntimeException("Erro ao buscar a disciplina.");
                    }
                    break;
            }

            request.setAttribute("disciplina", disciplina);
            request.setAttribute("acao", btEnviar);
            request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
            rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
            rd.forward(request, response);

        } else {
            int cargaHorariaInt = Integer.parseInt(cargaHoraria);
            Disciplina disciplina = new Disciplina(id, nome, requisito, ementa, cargaHorariaInt);
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        disciplinaDAO.Inserir(disciplina);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;

                    case "Alterar":
                        disciplinaDAO.Alterar(disciplina);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;

                    case "Excluir":
                        disciplinaDAO.Excluir(disciplina);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/DisciplinaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Erro ao realizar a operação na disciplina.");
            }
        }
    }
}