package com.fatec.sig1.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fatec.sig1.model.Cliente;
import com.fatec.sig1.model.ClienteRepository;
import com.fatec.sig1.model.PedidoRepository;
import com.fatec.sig1.model.Produto;
import com.fatec.sig1.model.ProdutoRepository;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(ClienteRepository repository1, ProdutoRepository repository2) {

		return args -> {
			// ******************************************************************************************
			// Cadastrar dois clientes na base de dados
			// ******************************************************************************************
			repository1.deleteAll();
			Cliente cliente1 = new Cliente("Jose da Silva", "01/03/1964", "M", "59672555598", "03694000", "2983");
			cliente1.setEndereco("Aguia de Haia");
			repository1.save(cliente1);
			Cliente cliente2 = new Cliente("Carlos Alberto", "19/08/1970", "M", "16467548671", "04280130", "59");
			cliente2.setEndereco("Rua Frei Joao");
			repository1.save(cliente2);
			// ******************************************************************************************
			// Cadastrar tres produtos na base de dados
			// ******************************************************************************************
			Produto produto1 = new Produto(1L, "parafuso", 10, 30); // descricao, custo e quantidade no estoque
			Produto produto2 = new Produto(2L, "tijolo", 15, 60);
			Produto produto3 = new Produto(3L, "bucha", 5, 50);
			repository2.saveAll(Arrays.asList(produto1, produto2, produto3));
			log.info (">>>>> tabelas carregadas");
		};
	}
}
