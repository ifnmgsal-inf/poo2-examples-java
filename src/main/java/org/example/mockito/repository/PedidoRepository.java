package org.example.mockito.repository;

import org.example.mockito.model.Pedido;

public interface PedidoRepository {
    Pedido save(Pedido pedido);
    Pedido findById(Long id);
}