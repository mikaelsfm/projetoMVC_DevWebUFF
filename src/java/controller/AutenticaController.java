package controller;

import entidade.Administrador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;
import model.AlunoDAO;
import entidade.Aluno;


@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        String cpf_user = request.getParameter("cpf");
        String senha_user = request.getParameter("senha");
        String userType = request.getParameter("userType");

        if (cpf_user.isEmpty() || senha_user.isEmpty()) {
            request.setAttribute("msgError", "Usuário e/ou senha incorreto");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
            return;
        }

        if ("administrador".equals(userType)) {
            AdministradorDAO adminDAO = new AdministradorDAO();
            Administrador administrador = new Administrador(cpf_user, senha_user);
            Administrador administradorObtido;
            try {
                administradorObtido = adminDAO.Logar(administrador);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha na query para Logar");
            }

            if (administradorObtido.getId() != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("administrador", administradorObtido);

                rd = request.getRequestDispatcher("/views/admin/dashboard/areaRestritaAdmin.jsp");
                rd.forward(request, response);

            } else {
                request.setAttribute("msgError", "Usuário e/ou senha incorreto (Admin)");
                rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                rd.forward(request, response);
            }

        } else if ("aluno".equals(userType)) {
            AlunoDAO alunoDAO = new AlunoDAO();
            Aluno aluno = new Aluno();
            aluno.setCpf(cpf_user);
            aluno.setSenha(senha_user);

            Aluno alunoObtido = null;
            try {
                alunoObtido = alunoDAO.logar(aluno);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Falha na query para Logar Aluno");
            }

            if (alunoObtido != null && alunoObtido.getId() != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("aluno", alunoObtido);

                rd = request.getRequestDispatcher("/views/admin/dashboard/areaRestritaAluno.jsp");
                rd.forward(request, response);

            } else {
                request.setAttribute("msgError", "Usuário e/ou senha incorreto (Aluno)");
                rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                rd.forward(request, response);
            }
        } else {
            request.setAttribute("msgError", "Tipo de usuário inválido");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
        }
    }

}
