package controller.admin;

import entidade.Relatorio;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RelatorioDAO;

@WebServlet("/admin/RelatorioController")
public class RelatorioController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RelatorioDAO relatorioDAO = new RelatorioDAO();
        ArrayList<Relatorio> relatorio = relatorioDAO.gerarRelatorio();

        request.setAttribute("relatorio", relatorio);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/relatorio/formRelatorio.jsp");
        dispatcher.forward(request, response);
    }
}