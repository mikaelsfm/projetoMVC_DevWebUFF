package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidade.Disciplina;

public class DisciplinaDAO {

    public void Inserir(Disciplina disciplina) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?,?,?,?)");
            sql.setString(1, disciplina.getNome());
            sql.setString(2, disciplina.getRequisito());
            sql.setString(3, disciplina.getEmenta());
            sql.setInt(4, disciplina.getCargaHoraria());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao inserir disciplina", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public Disciplina getDisciplina(int id) {
        Conexao conexao = new Conexao();
        try {
            Disciplina disciplina = new Disciplina();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    disciplina.setId(resultado.getInt("id"));
                    disciplina.setNome(resultado.getString("nome"));
                    disciplina.setRequisito(resultado.getString("requisito"));
                    disciplina.setEmenta(resultado.getString("ementa"));
                    disciplina.setCargaHoraria(resultado.getInt("carga_horaria"));
                }
            }
            return disciplina;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (getDisciplina) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Disciplina disciplina) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE disciplina SET nome = ?, requisito = ?, ementa = ?, carga_horaria = ? WHERE id = ?");
            sql.setString(1, disciplina.getNome());
            sql.setString(2, disciplina.getRequisito());
            sql.setString(3, disciplina.getEmenta());
            sql.setInt(4, disciplina.getCargaHoraria());
            sql.setInt(5, disciplina.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Disciplina disciplina) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM disciplina WHERE id = ?");
            sql.setInt(1, disciplina.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Disciplina> ListaDeDisciplinas() {
        ArrayList<Disciplina> minhasDisciplinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM disciplina ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Disciplina disciplina = new Disciplina(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("requisito"),
                            resultado.getString("ementa"),
                            resultado.getInt("carga_horaria")
                    );
                    minhasDisciplinas.add(disciplina);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeDisciplinas) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
        return minhasDisciplinas;
    }
}