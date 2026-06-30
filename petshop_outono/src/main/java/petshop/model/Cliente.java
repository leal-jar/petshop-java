package petshop.model;

import java.time.LocalDate;

public class Cliente extends Pessoa {

    private int idCliente;
    private LocalDate dataCadastro;
    private double credito;

    public Cliente() {}

    public Cliente(String cpf, String nomeCompleto, LocalDate dataNascimento,
                   String genero, String email, String cidade, String bairro,
                   String rua, String numeroEndereco, String complemento, String telefone,
                   LocalDate dataCadastro, double credito) {
        super(cpf, nomeCompleto, dataNascimento, genero, email,
              cidade, bairro, rua, numeroEndereco, complemento, telefone);
        this.dataCadastro = dataCadastro;
        this.credito = credito;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate d) { this.dataCadastro = d; }

    public double getCredito() { return credito; }
    public void setCredito(double credito) { this.credito = credito; }

    // Override evita precisar criar um outro método para o objeto sair formatado
    @Override
    public String toString() {
        return String.format("[%d] %s | CPF: %s | Tel: %s | Crédito: R$ %.2f", getIdCliente(), getNomeCompleto(), getCpf(), getTelefone(), credito);
    }
}