package controller.admin;

import entidade.Aluno;
import entidade.Turma;
import entidade.Professor;
import entidade.Disciplina;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TurmaDAO;
import model.ProfessorDAO;
import model.DisciplinaDAO;
import model.AlunoDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Turma turma = new Turma();
        TurmaDAO turmaDAO = new TurmaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        RequestDispatcher rd;
        switch (acao) {
           case "Listar":
                ArrayList<Turma> listaTurmas = turmaDAO.getAll();
                for (Turma t : listaTurmas) {
                    if (t.getProfessorId() != 0) {
                        String nomeProfessor = professorDAO.get(t.getProfessorId()).getNome();
                        t.setProfessorNome(nomeProfessor);
                    } else {
                        t.setProfessorNome("(Sem professor)");
                    }

                    if (t.getDisciplinaId() != 0) {
                        String nomeDisciplina = disciplinaDAO.getDisciplina(t.getDisciplinaId()).getNome();
                        t.setDisciplinaNome(nomeDisciplina);
                    } else {
                        t.setDisciplinaNome("(Sem disciplina)");
                    }

                    if (t.getAlunoId() > 0) { // Verifica se há algum aluno associado
                        String[] alunoIds = String.valueOf(t.getAlunoId()).split(","); // Converte o int em String e separa IDs
                        List<String> nomesAlunos = new ArrayList<>();
                        for (String id : alunoIds) {
                            if (!id.trim().isEmpty()) {
                                Aluno aluno = alunoDAO.get(Integer.parseInt(id.trim()));
                                if (aluno != null) {
                                    nomesAlunos.add(aluno.getNome());
                                }
                            }
                        }
                        // Concatena os nomes dos alunos
                        StringBuilder nomesConcatenados = new StringBuilder();
                        for (int i = 0; i < nomesAlunos.size(); i++) {
                            nomesConcatenados.append(nomesAlunos.get(i));
                            if (i < nomesAlunos.size() - 1) {
                                nomesConcatenados.append(", ");
                            }
                        }
                        t.setAlunoNome(nomesConcatenados.toString());
                    } else {
                        t.setAlunoNome("(Sem alunos)");
                    }
                }

                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/turma/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                request.setAttribute("turma", turma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                ArrayList<Professor> listaProfessores = professorDAO.getAll();
                request.setAttribute("listaProfessores", listaProfessores);

                ArrayList<Disciplina> listaDisciplinas = disciplinaDAO.ListaDeDisciplinas();
                request.setAttribute("listaDisciplinas", listaDisciplinas);

                rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                turma = turmaDAO.get(id);

                request.setAttribute("turma", turma);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                ArrayList<Professor> listaProfessores2 = professorDAO.getAll();
                request.setAttribute("listaProfessores", listaProfessores2);

                ArrayList<Disciplina> listaDisciplinas2 = disciplinaDAO.ListaDeDisciplinas();
                request.setAttribute("listaDisciplinas", listaDisciplinas2);

                rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                rd.forward(request, response);
                break;

            default:
                response.sendRedirect("/aplicacaoMVC/admin/TurmaController?acao=Listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String btEnviar = request.getParameter("btEnviar");

        int id = parseIntOrZero(request.getParameter("id"));
        int professorId = parseIntOrZero(request.getParameter("professorId"));
        int disciplinaId = parseIntOrZero(request.getParameter("disciplinaId"));
        int alunoId = parseIntOrZero(request.getParameter("alunoId"));

        BigDecimal nota = BigDecimal.ZERO;
        String strNota = request.getParameter("nota");
        if (strNota != null && !strNota.trim().isEmpty()) {
            nota = new BigDecimal(strNota);
        }

        String codigoTurma = request.getParameter("codigoTurma");
        if (codigoTurma == null) {
            codigoTurma = "";
        }

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
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Erro ao processar ação no TurmaController");
        }
    }

    private int parseIntOrZero(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }
}
