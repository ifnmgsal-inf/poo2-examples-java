package org.example.mockito.service;

import org.example.mockito.model.Pedido;
import org.example.mockito.repository.PedidoRepository;

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