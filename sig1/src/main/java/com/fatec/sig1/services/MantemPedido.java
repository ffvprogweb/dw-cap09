package com.fatec.sig1.services;
import java.util.List;

import com.fatec.sig1.model.Pedido;
import com.fatec.sig1.model.PedidoDTO;
public interface MantemPedido {
	public List<Pedido> consultaTodos();
	public void exclui(Long pedidoId);
	public Pedido cadastrar(PedidoDTO pedidoDTO);
}
