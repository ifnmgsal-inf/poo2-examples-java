/**
 * Entidade Pedido (model)
 * @author LeonardoSilva
 */
package org.example.mockito.model;

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