package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioDAO {

    public List<Map<String, Object>> gerarRelatorio() throws Exception {
        Conexao conexao = new Conexao();
        List<Map<String, Object>> relatorio = new ArrayList<>();
        try {
            String query =
                "SELECT " +
                "    d.nome AS disciplina_nome, " +
                "    t.codigo_turma AS codigo_turma, " +
                "    p.nome AS professor_nome, " +
                "    a.nome AS aluno_nome, " +
                "    t.nota AS nota " +
                "FROM " +
                "    turmas t " +
                "JOIN " +
                "    disciplina d ON t.disciplina_id = d.id " +
                "JOIN " +
                "    professores p ON t.professor_id = p.id " +
                "JOIN " +
                "    alunos a ON t.aluno_id = a.id " +
                "ORDER BY " +
                "    d.nome, t.codigo_turma, a.nome";

            PreparedStatement stmt = conexao.getConexao().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("disciplina_nome", rs.getString("disciplina_nome"));
                linha.put("codigo_turma", rs.getString("codigo_turma"));
                linha.put("professor_nome", rs.getString("professor_nome"));
                linha.put("aluno_nome", rs.getString("aluno_nome"));
                linha.put("nota", rs.getDouble("nota"));
                relatorio.add(linha);
            }
        } finally {
            conexao.closeConexao();
        }
        return relatorio;
    }
}