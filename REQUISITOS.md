# Requisitos do Sistema

## Overload — mesmo método, parâmetros diferentes

### ClienteService
```java
public Cliente buscarCliente(int idCliente)  // busca por ID
public Cliente buscarCliente(String cpf)     // busca por CPF
```

### FuncionarioService
```java
public Funcionario buscarFuncionario(int idFuncionario)  // busca por ID
public Funcionario buscarFuncionario(String cpf)         // busca por CPF
```

---

## Polimorfismo — mesmo método, comportamentos diferentes

`Pessoa` define `toString()` como base.  
Cada subclasse sobrescreve o método à sua maneira:

- `Cliente` → exibe id, nome, CPF, telefone e crédito
- `Funcionario` → exibe id, nome, CPF, cargo, área, salário e status
- `Pet` → exibe id, nome, animal, raça e peso

Na prática, o Java decide em tempo de execução qual implementação chamar:

```java
Pessoa p1 = new Cliente(...);
Pessoa p2 = new Funcionario(...);

System.out.println(p1); // executa toString() de Cliente
System.out.println(p2); // executa toString() de Funcionario
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

`Pessoa` é a superclasse abstrata com os atributos comuns:
cpf, nome, data de nascimento, gênero, email, endereço e telefone.

As subclasses herdam tudo de `Pessoa` e adicionam seus próprios atributos:

- `Cliente extends Pessoa` → adiciona id_cliente, data de cadastro e crédito
- `Funcionario extends Pessoa` → adiciona id_funcionario, data de admissão, cargo, área, salário e status

```java
public class Cliente extends Pessoa { ... }
public class Funcionario extends Pessoa { ... }
```

Ao instanciar um `Cliente`, o construtor chama `super()` reaproveitando
o construtor de `Pessoa` antes de inicializar os campos próprios do cliente.
