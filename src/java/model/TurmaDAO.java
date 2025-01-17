package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Turma;

public class TurmaDAO implements Dao<Turma> {

    @Override
    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = new Turma();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM turmas WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    turma.setId(resultado.getInt("id"));
                    turma.setProfessorId(resultado.getInt("professor_id"));
                    turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                    turma.setAlunoId(resultado.getInt("aluno_id"));
                    turma.setCodigoTurma(resultado.getString("codigo_turma"));
                    turma.setNota(resultado.getBigDecimal("nota"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get turma) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    @Override
    public void inserir(Turma t) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, ?)"
            );
            sql.setInt(1, t.getProfessorId());
            sql.setInt(2, t.getDisciplinaId());
            sql.setInt(3, t.getAlunoId());
            sql.setString(4, t.getCodigoTurma());
            sql.setBigDecimal(5, t.getNota());
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de insert (turma) incorreta: " + e.getMessage());
            throw new RuntimeException(e);
}  finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Turma t) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigo_turma = ?, nota = ? WHERE id = ?"
            );
            sql.setInt(1, t.getProfessorId());
            sql.setInt(2, t.getDisciplinaId());
            sql.setInt(3, t.getAlunoId());
            sql.setString(4, t.getCodigoTurma());
            sql.setBigDecimal(5, t.getNota());
            sql.setInt(6, t.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de update (alterar turma) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM turmas WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir turma) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Turma> getAll() {
        ArrayList<Turma> minhasTurmas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM turmas";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Turma turma = new Turma(
                        resultado.getInt("id"),
                        resultado.getInt("professor_id"),
                        resultado.getInt("disciplina_id"),
                        resultado.getInt("aluno_id"),
                        resultado.getString("codigo_turma"),
                        resultado.getBigDecimal("nota")
                    );
                    minhasTurmas.add(turma);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (getAll - turmas) incorreta: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return minhasTurmas;
    }
    
    public ArrayList<Turma> getAllDoAluno(int alunoId) {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT * FROM turmas WHERE aluno_id=? ORDER BY codigo_turma";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();
            while (rs != null && rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setProfessorId(rs.getInt("professor_id"));
                t.setDisciplinaId(rs.getInt("disciplina_id"));
                t.setAlunoId(rs.getInt("aluno_id"));
                t.setCodigoTurma(rs.getString("codigo_turma"));
                t.setNota(rs.getBigDecimal("nota"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas do aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }
    
    public ArrayList<Turma> getVagasDisponiveis() {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT * FROM turmas WHERE aluno_id=0 ORDER BY codigo_turma";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs != null && rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setProfessorId(rs.getInt("professor_id"));
                t.setDisciplinaId(rs.getInt("disciplina_id"));
                t.setAlunoId(rs.getInt("aluno_id"));
                t.setCodigoTurma(rs.getString("codigo_turma"));
                t.setNota(rs.getBigDecimal("nota"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vagas disponíveis: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }
    
    public int countOcupadasPorCodigo(String codigoTurma) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT COUNT(*) AS qt FROM turmas WHERE codigo_turma=? AND aluno_id<>0";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setString(1, codigoTurma);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                return rs.getInt("qt");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar turmas ocupadas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return 0;
    }

    public ArrayList<Turma> getTurmasNaoInscritas(int alunoId) {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT * FROM turmas WHERE aluno_id <> ? ORDER BY codigo_turma";

            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();
            while (rs != null && rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setProfessorId(rs.getInt("professor_id"));
                t.setDisciplinaId(rs.getInt("disciplina_id"));
                t.setAlunoId(rs.getInt("aluno_id"));
                t.setCodigoTurma(rs.getString("codigo_turma"));
                t.setNota(rs.getBigDecimal("nota"));
                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas nao inscritas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }
    
     public void inscreverAluno(int idTurma, int idAluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement ps = conexao.getConexao().prepareStatement(
                "UPDATE turmas SET aluno_id=? WHERE id=?"
            );
            ps.setInt(1, idAluno);
            ps.setInt(2, idTurma);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inscrever aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
     
    public void removerAluno(int idTurma) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement ps = conexao.getConexao().prepareStatement(
                "UPDATE turmas SET aluno_id=0, nota=0 WHERE id=?"
            );
            ps.setInt(1, idTurma);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover inscrição: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
}