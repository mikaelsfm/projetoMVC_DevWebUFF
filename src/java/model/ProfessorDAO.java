package model;

import entidade.Professor;
import java.sql.*;
import java.util.ArrayList;

/*
--
-- Estrutura para tabela `professores`
--

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/

public class ProfessorDAO implements Dao<Professor> {

    @Override
    public Professor get(int id) {
        Conexao conexao = new Conexao();
        Professor professor = new Professor();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM professores WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    professor.setId(resultado.getInt("id"));
                    professor.setNome(resultado.getString("nome"));
                    professor.setEmail(resultado.getString("email"));
                    professor.setCpf(resultado.getString("cpf"));
                    professor.setSenha(resultado.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return professor;
    }

    @Override
    public void inserir(Professor professor) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO professores (nome, email, cpf, senha) VALUES (?, ?, ?, ?)");
            sql.setString(1, professor.getNome());
            sql.setString(2, professor.getEmail());
            sql.setString(3, professor.getCpf());
            sql.setString(4, professor.getSenha());
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query de insert (professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Professor professor) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE professores SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?");
            sql.setString(1, professor.getNome());
            sql.setString(2, professor.getEmail());
            sql.setString(3, professor.getCpf());
            sql.setString(4, professor.getSenha());
            sql.setInt(5, professor.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query de update (alterar professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM professores WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query de delete (excluir professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Professor> getAll() {
        ArrayList<Professor> listaProfessores = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM professores");
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Professor professor = new Professor(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("cpf"),
                            resultado.getString("senha")
                    );
                    listaProfessores.add(professor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (getAll - professores) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return listaProfessores;
    }
}