package com.fatec.sig1.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemDePedidoRepository extends JpaRepository<ItemDePedido, Long>{

}

