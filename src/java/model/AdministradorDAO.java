package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Administrador;

/*
-- Estrutura da tabela `Administradors`

CREATE TABLE IF NOT EXISTS `Administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(8) NOT NULL,
  `endereco` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

 */
public class AdministradorDAO {

    public void Inserir(Administrador administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO administrador (nome, cpf, endereco, senha) VALUES (?,?,?,?)");
            sql.setString(1, administrador.getNome());
            sql.setString(2, administrador.getCpf());
            sql.setString(3, administrador.getEndereco());
            sql.setString(4, administrador.getSenha());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao inserir administrador", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public Administrador getAdministrador(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            Administrador administrador = new Administrador();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    administrador.setId(Integer.parseInt(resultado.getString("ID")));
                    administrador.setNome(resultado.getString("NOME"));
                    administrador.setCpf(resultado.getString("CPF"));
                    administrador.setEndereco(resultado.getString("ENDERECO"));
                    administrador.setSenha(resultado.getString("SENHA"));
                }
            }
            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (getAdministrador) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Administrador administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE administrador SET nome = ?, cpf = ?, endereco = ?, senha = ? WHERE ID = ?");
            sql.setString(1, administrador.getNome());
            sql.setString(2, administrador.getCpf());
            sql.setString(3, administrador.getEndereco());
            sql.setString(4, administrador.getSenha());
            sql.setInt(5, administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Administrador administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM administrador WHERE ID = ?");
            sql.setInt(1, administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Administrador> ListaDeAdministradores() {
        ArrayList<Administrador> meusAdministradores = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM administrador ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Administrador administrador = new Administrador(
                        resultado.getString("NOME"),
                        resultado.getString("CPF"),
                        resultado.getString("ENDERECO"),
                        resultado.getString("SENHA")
                    );
                    administrador.setId(Integer.parseInt(resultado.getString("ID")));
                    meusAdministradores.add(administrador);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeAdministradores) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
        return meusAdministradores;
    }

    public Administrador Logar(Administrador administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, administrador.getCpf());
            sql.setString(2, administrador.getSenha());
            ResultSet resultado = sql.executeQuery();
            Administrador administradorObtido = new Administrador();
            if (resultado != null) {
                while (resultado.next()) {
                    administradorObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    administradorObtido.setNome(resultado.getString("NOME"));
                    administradorObtido.setCpf(resultado.getString("CPF"));
                    administradorObtido.setEndereco(resultado.getString("ENDERECO"));
                    administradorObtido.setSenha(resultado.getString("SENHA"));
                }
            }
            return administradorObtido;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (logar) incorreta", e);
        } finally {
            conexao.closeConexao();
        }
    }
}

