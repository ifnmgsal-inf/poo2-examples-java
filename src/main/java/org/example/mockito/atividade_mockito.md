# Atividade prática: testes automatizados em Java com Mockito

## Tema
Testes automatizados em Java com Mockito.

## Público-alvo
Turma do 5º período do curso superior de Sistemas de Informação do Instituto Federal do Norte de Minas Gerais, na disciplina de Programação Orientada a Objetos.

## Objetivo
Desenvolver testes unitários para isolar a classe de serviço, simulando o comportamento do repositório com Mockito e validando regras de negócio.

## Contexto do problema
Uma loja virtual precisa gerenciar pedidos. O sistema possui:
- uma entidade `Pedido`;
- um repositório `PedidoRepository`;
- um serviço `PedidoService`.

A classe de serviço deve validar os dados do pedido, calcular o total com desconto e salvar o pedido apenas quando estiver tudo correto.

## Requisitos da atividade
Implemente os testes automatizados para:
- verificar a criação de pedidos válidos;
- impedir a criação de pedidos com valor inválido;
- testar o cálculo do total com desconto;
- simular o retorno do repositório com Mockito;
- verificar se o método `save` foi chamado corretamente.

## O que deve ser usado
- JUnit 5;
- Mockito;
- anotações `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`;
- métodos `when(...)`, `verify(...)` e, se quiser, `thenReturn(...)` e `assertThrows(...)`.

## Código base

### Entidade `Pedido`
```java
package br.edu.ifnmg.poo.model;

public class Pedido {
    private Long id;
    private String cliente;
    private double valor;
    private double desconto;

    public Pedido() {}

    public Pedido(Long id, String cliente, double valor, double desconto) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double calcularTotal() {
        return valor - (valor * desconto / 100.0);
    }
}
```

### Repositório `PedidoRepository`
```java
package br.edu.ifnmg.poo.repository;

import br.edu.ifnmg.poo.model.Pedido;

public interface PedidoRepository {
    Pedido save(Pedido pedido);
    Pedido findById(Long id);
}
```

### Serviço `PedidoService`
```java
package br.edu.ifnmg.poo.service;

import br.edu.ifnmg.poo.model.Pedido;
import br.edu.ifnmg.poo.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }
        if (pedido.getCliente() == null || pedido.getCliente().isBlank()) {
            throw new IllegalArgumentException("Cliente obrigatório");
        }
        if (pedido.getValor() <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if (pedido.getDesconto() < 0 || pedido.getDesconto() > 100) {
            throw new IllegalArgumentException("Desconto inválido");
        }
        return pedidoRepository.save(pedido);
    }

    public double calcularTotalComDesconto(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }
        return pedido.calcularTotal();
    }

    public Pedido buscarPedido(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }
        return pedidoRepository.findById(id);
    }
}
```

## Tarefa para os estudantes
Crie uma classe de teste para `PedidoService` e implemente os testes abaixo:
- deve criar um pedido válido com sucesso;
- deve lançar exceção quando o cliente estiver vazio;
- deve lançar exceção quando o valor for menor ou igual a zero;
- deve calcular corretamente o total com desconto;
- deve verificar se `save` foi chamado uma vez ao criar o pedido;
- deve simular a busca de um pedido por id.

## Orientações para os testes
- Use `@Mock` para simular `PedidoRepository`.
- Use `@InjectMocks` para injetar o mock no `PedidoService`.
- Use `@ExtendWith(MockitoExtension.class)` para integrar Mockito e JUnit 5.
- Use `when(...).thenReturn(...)` para definir retornos simulados.
- Use `verify(...)` para confirmar interações com o repositório.

