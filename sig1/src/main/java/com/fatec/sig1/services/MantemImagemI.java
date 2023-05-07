package com.fatec.sig1.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.sig1.model.Imagem;
import com.fatec.sig1.model.ImagemRepository;

@Service
public class MantemImagemI implements MantemImagem {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ImagemRepository imagemRepository;

	private Environment environment = null;

	public MantemImagemI(ImagemRepository imagemRepository, Environment environment) {
		this.imagemRepository = imagemRepository;
		this.environment = environment;
	}

	public Imagem salvar(MultipartFile arquivo) throws IOException {
		// **********************************************************
		// Obter informações sobre o arquivo
		// **********************************************************
		String nome = arquivo.getOriginalFilename();
		String tipo = arquivo.getContentType();
		long tamanho = arquivo.getSize();
		byte[] conteudo = arquivo.getBytes(); // Obtem o conteúdo do arquivo
		// *********************************************************
		// Processar o arquivo
		// *********************************************************
		// String nomeArquivo = UUID.randomUUID().toString() + "_" +
		// arquivo.getOriginalFilename();
		String nomeArquivo = arquivo.getOriginalFilename();
		// String caminhoArquivo = environment.getProperty("imagem.upload-dir") + "/" +
		// nomeArquivo;
		// Path caminhoCompleto =
		// Paths.get(caminhoArquivo).toAbsolutePath().normalize();
		Path caminhoArquivo = Paths.get("imagens/" + nomeArquivo);
		Imagem imagem = new Imagem();
		imagem.setNome(nomeArquivo);
		imagem.setCaminho(caminhoArquivo.toString());
		imagem.setArquivo(arquivo.getBytes());
		Files.write(caminhoArquivo, arquivo.getBytes());
		logger.info(">>>>>> servico mantem imagem salvar executado");
		
		return imagemRepository.save(imagem);
	}

	public List<Imagem> getAll() {
		return imagemRepository.findAll();
	}

	public byte[] getImagem(String nomeArquivo) {
		Path caminhoArquivo = Paths.get("imagens/" + nomeArquivo);
		Imagem dbImagem = imagemRepository.findByNome(nomeArquivo).get();
		return dbImagem.getArquivo();
		
	}
}