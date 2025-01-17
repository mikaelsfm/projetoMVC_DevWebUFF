package model;

import entidade.Aluno;
import java.sql.*;
import java.util.ArrayList;

public class AlunoDAO implements Dao<Aluno> {

    @Override
    public Aluno get(int id) {
        Conexao conexao = new Conexao();
        Aluno aluno = new Aluno();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM alunos WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    aluno.setId(resultado.getInt("id"));
                    aluno.setNome(resultado.getString("nome"));
                    aluno.setEmail(resultado.getString("email"));
                    aluno.setCelular(resultado.getString("celular"));
                    aluno.setCpf(resultado.getString("cpf"));
                    aluno.setSenha(resultado.getString("senha"));
                    aluno.setEndereco(resultado.getString("endereco"));
                    aluno.setCidade(resultado.getString("cidade"));
                    aluno.setBairro(resultado.getString("bairro"));
                    aluno.setCep(resultado.getString("cep"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get aluno) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return aluno;
    }

    @Override
    public void inserir(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO alunos (nome, email, celular, cpf, senha, endereco, cidade, bairro, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de insert (aluno) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE alunos SET nome = ?, email = ?, celular = ?, cpf = ?, senha = ?, endereco = ?, cidade = ?, bairro = ?, cep = ? WHERE id = ?"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.setInt(10, aluno.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de update (alterar aluno) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM alunos WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir aluno) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Aluno> getAll() {
        ArrayList<Aluno> meusAlunos = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM alunos";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    Aluno aluno = new Aluno(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("email"),
                        resultado.getString("celular"),
                        resultado.getString("cpf"),
                        resultado.getString("senha"),
                        resultado.getString("endereco"),
                        resultado.getString("cidade"),
                        resultado.getString("bairro"),
                        resultado.getString("cep")
                    );
                    meusAlunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - alunos) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return meusAlunos;
    }
    
    public Aluno logar(Aluno aluno) {
    Conexao conexao = new Conexao();
    Aluno alunoObtido = new Aluno();
    try {
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "SELECT * FROM alunos WHERE cpf=? AND senha=? LIMIT 1"
        );
        sql.setString(1, aluno.getCpf());
        sql.setString(2, aluno.getSenha());

        ResultSet rs = sql.executeQuery();
        if (rs != null && rs.next()) {
            alunoObtido.setId(rs.getInt("id"));
            alunoObtido.setNome(rs.getString("nome"));
            alunoObtido.setEmail(rs.getString("email"));
            alunoObtido.setCelular(rs.getString("celular"));
            alunoObtido.setCpf(rs.getString("cpf"));
            alunoObtido.setSenha(rs.getString("senha"));
            alunoObtido.setEndereco(rs.getString("endereco"));
            alunoObtido.setCidade(rs.getString("cidade"));
            alunoObtido.setBairro(rs.getString("bairro"));
            alunoObtido.setCep(rs.getString("cep"));
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao logar aluno: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return alunoObtido;
}
}