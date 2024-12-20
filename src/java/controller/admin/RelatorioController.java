package controller.admin;

import model.RelatorioDAO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RelatorioController", urlPatterns = {"/admin/RelatorioController"})
public class RelatorioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RelatorioDAO relatorioDAO = new RelatorioDAO();
        try {
            List<Map<String, Object>> relatorio = relatorioDAO.gerarRelatorio();
            request.setAttribute("relatorio", relatorio);
            request.getRequestDispatcher("/views/admin/relatorio/formRelatorio.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao gerar o relatório", e);
        }
    }
}