package petshop.model;

import java.time.LocalDate;

public class Funcionario extends Pessoa {

    private int idFuncionario;
    private LocalDate dataAdmissao;
    private String cargo;
    private String area;
    private double salario;
    private String status;

    public Funcionario() {}

    public Funcionario(String cpf, String nomeCompleto, LocalDate dataNascimento,
                       String genero, String email, String cidade, String bairro,
                       String rua, String numeroEndereco, String complemento, String telefone,
                       LocalDate dataAdmissao, String cargo, String area, double salario, String status) {
        super(cpf, nomeCompleto, dataNascimento, genero, email,
              cidade, bairro, rua, numeroEndereco, complemento, telefone);
        this.dataAdmissao = dataAdmissao;
        this.cargo = cargo;
        this.area = area;
        this.salario = salario;
        this.status = status;
    }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario)  { this.idFuncionario = idFuncionario; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate d) { this.dataAdmissao = d; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%d] %s | CPF: %s | %s | %s | R$ %.2f | %s", getIdFuncionario(), getNomeCompleto(), getCpf(), cargo, area, salario, status);
    }
}