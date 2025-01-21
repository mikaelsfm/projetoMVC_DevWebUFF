package controller.admin;

import entidade.Administrador;
import model.AdministradorDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministradorController", urlPatterns = {"/admin/AdministradorController"})
public class AdministradorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = request.getParameter("acao");
            Administrador administrador = new Administrador();
            AdministradorDAO administradorDAO = new AdministradorDAO();
            RequestDispatcher rd;

            switch (acao) {
                case "Listar":
                    ArrayList<Administrador> listaAdministradores = administradorDAO.ListaDeAdministradores();
                    request.setAttribute("listaAdministradores", listaAdministradores);
                    rd = request.getRequestDispatcher("/views/admin/administrador/listaAdministradores.jsp");
                    rd.forward(request, response);
                    break;

                case "Alterar":
                case "Excluir":
                    int id = Integer.parseInt(request.getParameter("id"));
                    administrador = administradorDAO.getAdministrador(id);
                    request.setAttribute("administrador", administrador);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/administrador/formAdministrador.jsp");
                    rd.forward(request, response);
                    break;

                case "Incluir":
                    request.setAttribute("administrador", administrador);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/administrador/formAdministrador.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    throw new ServletException("Ação não reconhecida.");
            }

        } catch (Exception ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        String nome = request.getParameter("nome");
        String cpfAdmin = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("endereco");
        String aprovado = request.getParameter("aprovado");
        String btEnviar = request.getParameter("btEnviar");

        Administrador administrador = new Administrador(nome, cpfAdmin, endereco, senha, aprovado);
        AdministradorDAO administradorDao = new AdministradorDAO();

        try {
            switch (btEnviar) {
                case "Incluir":
                    administradorDao.Inserir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    break;

                case "Alterar":
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    administrador.setId(idAlterar);
                    administradorDao.Alterar(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    administrador.setId(idExcluir);
                    administradorDao.Excluir(administrador);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;

                default:
                    throw new ServletException("Ação não reconhecida.");
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/AdministradorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha em uma query para cadastro de administrador", ex);
        }
    }
}