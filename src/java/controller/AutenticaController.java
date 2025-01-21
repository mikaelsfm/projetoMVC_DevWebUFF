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
import entidade.Professor;
import model.ProfessorDAO;


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
        
        if ("249.252.810-38".equals(cpf_user)) {
            AdministradorDAO adminDAO = new AdministradorDAO();
            try {
                Administrador adminExistente = adminDAO.getAdministradorByCpf(cpf_user);
                if (adminExistente == null) {
                    Administrador novoAdmin = new Administrador("admin", cpf_user, "Endereço padrão", senha_user, "S");
                    adminDAO.Inserir(novoAdmin);
                }
            } catch (Exception e) {
                System.out.println("Erro ao verificar ou inserir administrador padrão: " + e.getMessage());
                throw new RuntimeException("Erro ao verificar ou inserir administrador padrão.", e);
            }
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
        } else if ("professor".equals(userType)) {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = new Professor();
            professor.setCpf(cpf_user);
            professor.setSenha(senha_user);

            Professor professorObtido = null;
            try {
                professorObtido = professorDAO.logar(professor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Falha na query para Logar Aluno");
            }

            if (professorObtido != null && professorObtido.getId() != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("professor", professorObtido);

                rd = request.getRequestDispatcher("/views/admin/dashboard/areaRestritaProfessor.jsp");
                rd.forward(request, response);

            } else {
                request.setAttribute("msgError", "Usuário e/ou senha incorreto (Professor)");
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
