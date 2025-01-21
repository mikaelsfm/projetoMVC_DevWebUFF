package entidade;

public class Administrador {

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String senha;
    private String aprovado;

    public Administrador(String nome, String cpf, String endereco, String senha, String aprovado) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.senha = senha;
        this.aprovado = aprovado;
    }

    public Administrador(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public Administrador() {
        this.id = 0;
        this.nome = "";
        this.cpf = "";
        this.endereco = "";
        this.senha = "";
        this.aprovado = "N";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getAprovado() {
        return aprovado;
    }

    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }

}
