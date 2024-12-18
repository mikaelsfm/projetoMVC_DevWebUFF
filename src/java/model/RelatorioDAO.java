package model;

import entidade.Relatorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RelatorioDAO {
    public ArrayList<Relatorio> gerarRelatorio() {
        ArrayList<Relatorio> relatorio = new ArrayList<>();
        Conexao conexao = new Conexao();

        try {
            String query = "SELECT t.codigo_turma, d.nome AS disciplina, a.id AS aluno_id, a.nome AS aluno_nome, t.nota " +
                           "FROM turmas t " +
                           "JOIN disciplinas d ON t.disciplina_id = d.id " +
                           "JOIN alunos a ON t.aluno_id = a.id";

            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            ResultSet resultado = sql.executeQuery();

            while (resultado.next()) {
                Relatorio item = new Relatorio(
                    resultado.getString("codigo_turma"),
                    resultado.getString("disciplina"),
                    resultado.getInt("aluno_id"),
                    resultado.getString("aluno_nome"),
                    resultado.getBigDecimal("nota")
                );
                relatorio.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    return relatorio;
}
}
