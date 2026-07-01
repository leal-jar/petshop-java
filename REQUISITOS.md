# Requisitos do Sistema

## Overload — mesmo método, parâmetros diferentes

### ClienteService
```java
public Cliente buscarCliente(int idCliente)
public Cliente buscarCliente(String cpf)
```

### FuncionarioService
```java
public Funcionario buscarFuncionario(int idFuncionario)
public Funcionario buscarFuncionario(String cpf)
```

---

## Polimorfismo — mesmo método, comportamentos diferentes

`Pessoa` define `toString()` como base.  
Cada subclasse sobrescreve o método à sua maneira:

- `Cliente` → exibe id, nome, CPF, telefone e crédito
- `Funcionario` → exibe id, nome, CPF, cargo, área, salário e status
- `Pet` → exibe id, nome, animal, raça e peso

```java
Pessoa p1 = new Cliente(...);
Pessoa p2 = new Funcionario(...);

System.out.println(p1);
System.out.println(p2);
```

---

## Override — subclasse sobrescreve um método da superclasse

`Pessoa` define `toString()` como base.  
As subclasses sobrescrevem com `@Override`:

- `Cliente` → `toString()` exibe id, nome, CPF, telefone e crédito
- `Funcionario` → `toString()` exibe id, nome, CPF, cargo, área, salário e status
- `Pet` → `toString()` exibe id, nome, animal, raça e peso

---

## Herança — subclasse herda atributos e métodos da superclasse

`Pessoa` é a superclasse com os atributos comuns:
cpf, nome, data de nascimento, gênero, email, endereço e telefone.

As subclasses herdam tudo de `Pessoa` e adicionam seus próprios atributos:

- `Cliente extends Pessoa`: adiciona id_cliente, data de cadastro e crédito
- `Funcionario extends Pessoa`: adiciona id_funcionario, data de admissão, cargo, área, salário e status

```java
public class Cliente extends Pessoa { ... }
public class Funcionario extends Pessoa { ... }
```

Ao instanciar um `Cliente`, o construtor chama `super()` reaproveitando
o construtor de `Pessoa` antes de inicializar os campos próprios do cliente.

---

## Interface — padronização das operações básicas

`ICrud` é a classe que define quais métodos devem ser implementados em certas classes

```java
public interface ICrud<T> {
    void inserir(T obj) throws SQLException; // Obrigatório possuir um método inserir
    void atualizar(T obj) throws SQLException; // Obrigatório possuir um método atualizar
    void excluir(int id) throws SQLException; // Obrigatório possuir um método excluir
    List<T> buscarTodos() throws SQLException; // Obrigatório possuir um método buscarTodos
}
```

As classes `Services` utilizam essa interface

```java 
public class ClienteService implements ICrud<Cliente> { ... }
public class FuncionarioService implements ICrud<Funcionario> { ... }
public class PetService implements ICrud<Pet> { ... }
```