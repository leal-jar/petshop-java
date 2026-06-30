package petshop.model;

import java.time.LocalDate;

public class Pet {

    private int idPet;
    private int idCliente;
    private String nomePet;
    private LocalDate dataCadastro;
    private String animal;
    private char sexo;
    private String raca;
    private LocalDate dataNascimento;
    private double peso;
    private String alergia;
    private String restricao;
    private String comportamento;

    public Pet() {}

    public Pet(int idCliente, String nomePet, LocalDate dataCadastro,
               String animal, char sexo, String raca, LocalDate dataNascimento,
               double peso, String alergia, String restricao, String comportamento) {
        this.idCliente = idCliente;
        this.nomePet = nomePet;
        this.dataCadastro = dataCadastro;
        this.animal = animal;
        this.sexo = sexo;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.alergia = alergia;
        this.restricao = restricao;
        this.comportamento = comportamento;
    }

    public int getIdPet() { return idPet; }
    public void setIdPet(int idPet) { this.idPet = idPet; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNomePet() { return nomePet; }
    public void setNomePet(String nomePet) { this.nomePet = nomePet; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate d) { this.dataCadastro = d; }

    public String getAnimal() { return animal; }
    public void setAnimal(String animal) { this.animal = animal; }

    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate d) { this.dataNascimento = d; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getAlergia() { return alergia; }
    public void setAlergia(String alergia) { this.alergia = alergia; }

    public String getRestricao() { return restricao; }
    public void setRestricao(String restricao) { this.restricao = restricao; }

    public String getComportamento() { return comportamento; }
    public void setComportamento(String c) { this.comportamento = c; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s | %.1f kg", idPet, nomePet, animal, raca, peso);
    }
}