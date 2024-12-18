package entidade;

import java.math.BigDecimal;

public class Relatorio {
    private String codigoTurma;
    private String nomeDisciplina;
    private int alunoId;
    private String alunoNome;
    private BigDecimal nota;

    public Relatorio() {
        this.codigoTurma = "";
        this.nomeDisciplina = "";
        this.alunoId = 0;
        this.alunoNome = "";
        this.nota = new BigDecimal(0);
    }

    public Relatorio(String codigoTurma, String nomeDisciplina, int alunoId, String alunoNome, BigDecimal nota) {
        this.codigoTurma = codigoTurma;
        this.nomeDisciplina = nomeDisciplina;
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.nota = nota;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
}