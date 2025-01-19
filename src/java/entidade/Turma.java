package entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Turma {
    private int id;
    private int professorId;
    private int disciplinaId;
    private int alunoId;
    private String codigoTurma;
    private BigDecimal nota;
    private String professorNome;
    private String disciplinaNome;
    private String alunoNome;
    private List<String> alunosNomes = new ArrayList<>();


    public Turma() {
        this.id = 0;
        this.professorId = 0;
        this.disciplinaId = 0;
        this.alunoId = 0;
        this.codigoTurma = "";
        this.nota = new BigDecimal(0);
    }

    public Turma(int id, int professorId, int disciplinaId, int alunoId, String codigoTurma, BigDecimal nota) {
        this.id = id;
        this.professorId = professorId;
        this.disciplinaId = disciplinaId;
        this.alunoId = alunoId;
        this.codigoTurma = codigoTurma;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
    
    public String getProfessorNome() {
        return professorNome;
    }
    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }
    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
    }

    public String getAlunoNome() {
        return alunoNome;
    }
    
    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }
    
    public List<String> getAlunosNomes() {
        return alunosNomes;
    }
    
    public void setAlunosNomes(List<String> alunosNomes) {
        this.alunosNomes = alunosNomes;
    }
    
    public void addAlunoNome(String alunoNome) {
        this.alunosNomes.add(alunoNome);
    }
    
    @Override
    public String toString() {
    return "Turma{" +
            "id=" + id +
            ", professorId=" + professorId +
            ", disciplinaId=" + disciplinaId +
            ", alunoId=" + alunoId +
            ", codigoTurma='" + codigoTurma + '\'' +
            ", nota=" + nota +
            '}';
}
}