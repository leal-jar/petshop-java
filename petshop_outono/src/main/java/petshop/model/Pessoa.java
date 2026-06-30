package petshop.model;

import java.time.LocalDate;

public abstract class Pessoa {

    private int idPessoa;
    private String cpf;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String genero;
    private String email;
    private String cidade;
    private String bairro;
    private String rua;
    private String numeroEndereco;
    private String complemento;
    private String telefone;

    public Pessoa() {}

    public Pessoa(String cpf, String nomeCompleto, LocalDate dataNascimento,
                  String genero, String email, String cidade, String bairro,
                  String rua, String numeroEndereco, String complemento, String telefone) {
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.email = email;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroEndereco = numeroEndereco;
        this.complemento = complemento;
        this.telefone = telefone;
    }

    public int getIdPessoa() { return idPessoa; }
    public void setIdPessoa(int idPessoa) { this.idPessoa = idPessoa; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate d) { this.dataNascimento = d; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getNumeroEndereco() { return numeroEndereco; }
    public void setNumeroEndereco(String n) { this.numeroEndereco = n; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return String.format("[%d] %s | CPF: %s | Tel: %s", idPessoa, nomeCompleto, cpf, telefone);
    }
}
