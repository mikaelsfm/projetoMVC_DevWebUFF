package entidade;

public class Relatorio {

    private String disciplina;
    private int codigoTurma;
    private String aluno;
    private double nota;

    public Relatorio() {
        this.disciplina = "";
        this.codigoTurma = 0;
        this.aluno = "";
        this.nota = 0;
    }

    public Relatorio(String disciplina, int codigoTurma, String aluno, double nota) {
        this.disciplina = disciplina;
        this.codigoTurma = codigoTurma;
        this.aluno = aluno;
        this.nota = nota;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Relatorio{"
                + "disciplina='" + disciplina + '\''
                + ", codigoTurma=" + codigoTurma
                + ", aluno='" + aluno + '\''
                + ", nota=" + nota
                + '}';
    }
}
