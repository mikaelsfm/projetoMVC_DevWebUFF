package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Turma;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
            int totalAlunos = countAlunosPorCodigoTurma(t.getCodigoTurma());
            if (totalAlunos >= 2) {
                throw new RuntimeException("Não é possível adicionar mais alunos: a turma já possui 2 alunos.");
            }

            String sql = "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) " +
                         "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, t.getProfessorId());
            ps.setInt(2, t.getDisciplinaId());
            ps.setInt(3, t.getAlunoId());
            ps.setString(4, t.getCodigoTurma());
            ps.setBigDecimal(5, t.getNota());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir nova turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Turma t) {
        Conexao conexao = new Conexao();
        try {
            ArrayList<String> codigosDistintos = getCodigosDistintosPorProfessor(t.getProfessorId());

            Turma turmaAtual = get(t.getId());
            if (!turmaAtual.getCodigoTurma().equals(t.getCodigoTurma()) && !codigosDistintos.contains(t.getCodigoTurma())) {
                codigosDistintos.add(t.getCodigoTurma());
            }

            if (codigosDistintos.size() > 2) {
                throw new RuntimeException("Não é possível alterar: o professor não pode ter mais de dois códigos de turma distintos.");
            }

            String sql = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigo_turma = ?, nota = ? WHERE id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, t.getProfessorId());
            ps.setInt(2, t.getDisciplinaId());
            ps.setInt(3, t.getAlunoId());
            ps.setString(4, t.getCodigoTurma());
            ps.setBigDecimal(5, t.getNota());
            ps.setInt(6, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar turma: " + e.getMessage());
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
            String sql = "SELECT * FROM turmas WHERE aluno_id = ? AND aluno_id <> 0 ORDER BY codigo_turma";
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
    
    public BigDecimal getNota(int turmaId, int alunoId) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT nota FROM turmas WHERE id = ? AND aluno_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, turmaId);
            ps.setInt(2, alunoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal notaEscalada = rs.getBigDecimal("nota");
                return notaEscalada.divide(BigDecimal.TEN);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nota: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return BigDecimal.ZERO;
    }
    
    public ArrayList<Turma> getVagasDisponiveis() {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = 
              "SELECT t.* " +
              "FROM turmas t " +
              "JOIN ( " +
              "   SELECT professor_id, disciplina_id, codigo_turma, COUNT(*) AS qt " +
              "   FROM turmas " +
              "   GROUP BY professor_id, disciplina_id, codigo_turma " +
              "   HAVING qt < 2 " +
              ") temp " +
              "ON t.professor_id = temp.professor_id " +
              "AND t.disciplina_id = temp.disciplina_id " +
              "AND t.codigo_turma = temp.codigo_turma " +
              "ORDER BY t.codigo_turma";

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
            throw new RuntimeException("Erro ao listar turmas com vagas (SQL): " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }
    
    public ArrayList<Turma> getTurmasDisponiveisParaAluno(int alunoId) {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = 
                "SELECT t.* " +
                "FROM turmas t " +
                "WHERE t.codigo_turma NOT IN ( " +
                "    SELECT codigo_turma " +
                "    FROM turmas " +
                "    GROUP BY codigo_turma " +
                "    HAVING COUNT(*) >= 2 " +
                ") " +
                "AND t.aluno_id <> ? " +
                "ORDER BY t.codigo_turma";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
            throw new RuntimeException("Erro ao listar turmas disponíveis para aluno: " + e.getMessage());
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
    
    public int countTurmasPorProfessor(int professorId) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT COUNT(DISTINCT codigo_turma) AS total FROM turmas WHERE professor_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar códigos de turma distintos do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return 0;
    }
    
    public ArrayList<String> getCodigosDistintosPorProfessor(int professorId) {
        Conexao conexao = new Conexao();
        ArrayList<String> codigosDistintos = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT codigo_turma FROM turmas WHERE professor_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                codigosDistintos.add(rs.getString("codigo_turma"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar códigos distintos do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return codigosDistintos;
    }
    
    public int countAlunosPorCodigoTurma(String codigoTurma) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT COUNT(*) AS total FROM turmas WHERE codigo_turma = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setString(1, codigoTurma);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar alunos por código da turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return 0;
    }
    
    public boolean isTurmaDoProfessor(int turmaId, int professorId) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT COUNT(*) FROM turmas WHERE id = ? AND professor_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, turmaId);
            ps.setInt(2, professorId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar turma do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public boolean existeCodigoTurmaParaProfessorEDisciplina(int professorId, int disciplinaId, String codigoTurma) {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT 1 FROM turmas WHERE professor_id = ? AND disciplina_id = ? AND codigo_turma = ? LIMIT 1";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ps.setInt(2, disciplinaId);
            ps.setString(3, codigoTurma);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de código de turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
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
    
    public void inscreverAluno(int turmaId, int alunoId) {
        Conexao conexao = new Conexao();

        try {
            String sqlCount = "SELECT COUNT(*) AS num_alunos FROM turmas WHERE id = ?";
            PreparedStatement psCount = conexao.getConexao().prepareStatement(sqlCount);
            psCount.setInt(1, turmaId);
            ResultSet rsCount = psCount.executeQuery();

            if (rsCount.next() && rsCount.getInt("num_alunos") >= 2) {
                throw new RuntimeException("Turma cheia! Não é possível inscrever mais alunos.");
            }

            String sqlUpdate = "UPDATE turmas SET aluno_id = ? WHERE id = ?";
            PreparedStatement psUpdate = conexao.getConexao().prepareStatement(sqlUpdate);
            psUpdate.setInt(1, alunoId);
            psUpdate.setInt(2, turmaId);
            psUpdate.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inscrever aluno na turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public void duplicarTurmaParaNovoAluno(int idTurmaOriginal, int novoAlunoId) {
        Conexao conexao = new Conexao();
        try {
            String sqlCount = "SELECT COUNT(*) AS total FROM turmas WHERE codigo_turma = (SELECT codigo_turma FROM turmas WHERE id = ?)";
            PreparedStatement psCount = conexao.getConexao().prepareStatement(sqlCount);
            psCount.setInt(1, idTurmaOriginal);
            ResultSet rsCount = psCount.executeQuery();

            if (rsCount.next() && rsCount.getInt("total") >= 2) {
                throw new RuntimeException("A turma já possui 2 alunos.");
            }

            String sqlGet = "SELECT professor_id, disciplina_id, codigo_turma FROM turmas WHERE id = ?";
            PreparedStatement psGet = conexao.getConexao().prepareStatement(sqlGet);
            psGet.setInt(1, idTurmaOriginal);
            ResultSet rsGet = psGet.executeQuery();

            if (rsGet.next()) {
                String sqlInsert = "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, 0)";
                PreparedStatement psInsert = conexao.getConexao().prepareStatement(sqlInsert);
                psInsert.setInt(1, rsGet.getInt("professor_id"));
                psInsert.setInt(2, rsGet.getInt("disciplina_id"));
                psInsert.setInt(3, novoAlunoId);
                psInsert.setString(4, rsGet.getString("codigo_turma"));
                psInsert.executeUpdate();
            } else {
                throw new RuntimeException("Turma original não encontrada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao duplicar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
     
    public void removerAluno(int idTurma) {
        Conexao conexao = new Conexao();
        try {
            String sql = "DELETE FROM turmas WHERE id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, idTurma);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover inscrição (apagar tupla): " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public Turma verificarTurmaDoProfessor(int professorId, int disciplinaId, int alunoId) {
        Conexao conexao = new Conexao();
        Turma turma = null;
        try {
            String sql = "SELECT * FROM turmas WHERE professor_id = ? AND disciplina_id = ? AND aluno_id = ? LIMIT 1";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ps.setInt(2, disciplinaId);
            ps.setInt(3, alunoId);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setProfessorId(rs.getInt("professor_id"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setAlunoId(rs.getInt("aluno_id"));
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setNota(rs.getBigDecimal("nota"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar duplicidade por professor, aluno e disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
}
    
    public ArrayList<Turma> getTurmasPorProfessor(int professorId) {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();

        try {
            String sql = 
                "SELECT t.id, t.codigo_turma, t.disciplina_id, d.nome AS disciplina_nome " +
                "FROM turmas t " +
                "JOIN disciplina d ON t.disciplina_id = d.id " +
                "WHERE t.professor_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setDisciplinaNome(rs.getString("disciplina_nome"));
                lista.add(turma);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar turmas por professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }

        return lista;
    }
    
    public ArrayList<Turma> getTurmasDisponiveisParaAluno() {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = 
                "SELECT t.* " +
                "FROM turmas t " +
                "LEFT JOIN alunos a ON t.aluno_id = a.id " +
                "WHERE t.aluno_id IS NULL OR t.aluno_id <> ? " +
                "ORDER BY t.codigo_turma";

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
            throw new RuntimeException("Erro ao listar turmas disponíveis para o aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }
    
     public ArrayList<Turma> getTurmasPorProfessorComAlunos(int professorId) {
        ArrayList<Turma> lista = new ArrayList<>();
        Conexao conexao = new Conexao();

        try {
            String sql = 
                "SELECT t.id AS turma_id, t.codigo_turma, t.disciplina_id, t.professor_id, " +
                "       d.nome AS disciplina_nome, a.id AS aluno_id, a.nome AS aluno_nome, t.nota " +
                "FROM turmas t " +
                "JOIN disciplina d ON t.disciplina_id = d.id " +
                "LEFT JOIN alunos a ON t.aluno_id = a.id " +
                "WHERE t.professor_id = ? " +
                "ORDER BY t.codigo_turma";

            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();

            Map<Integer, Turma> turmasMap = new HashMap<>();

            while (rs.next()) {
                int turmaId = rs.getInt("turma_id");
                Turma turma = turmasMap.getOrDefault(turmaId, new Turma());
                turma.setId(turmaId);
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setProfessorId(rs.getInt("professor_id"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setDisciplinaNome(rs.getString("disciplina_nome"));
                turma.setNota(rs.getBigDecimal("nota"));

                String alunoNome = rs.getString("aluno_nome");
                if (alunoNome != null) {
                    turma.addAlunoNome(alunoNome); 
                }

                turmasMap.put(turmaId, turma);
            }

            lista.addAll(turmasMap.values());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas e alunos do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }

    public void atualizarNota(int alunoId, BigDecimal nota) {
        Conexao conexao = new Conexao();
        try {
            String sql = "UPDATE turmas SET nota = ? WHERE aluno_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setBigDecimal(1, nota);
            ps.setInt(2, alunoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nota: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public void lancarNota(int turmaId, int alunoId, BigDecimal nota) {
        Conexao conexao = new Conexao();
        try {
            String sql = "UPDATE turmas SET nota = ? WHERE id = ? AND aluno_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setBigDecimal(1, nota);
            ps.setInt(2, turmaId);
            ps.setInt(3, alunoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao lançar nota: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public boolean podeAdicionarTurma(int professorId) {
        Conexao conexao = new Conexao();

        try {
            String sql = "SELECT COUNT(*) AS num_turmas FROM turmas WHERE professor_id = ?";
            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("num_turmas") < 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar limite de turmas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }

        return false;
    }
}