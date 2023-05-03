package com.fatec.sig1.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.sig1.model.Imagem;
import com.fatec.sig1.model.ImagemRepository;
import com.fatec.sig1.services.MantemImagemI;

@RestController
@RequestMapping("/imagens")
public class APIImagemController {
	Logger logger = LogManager.getLogger(this.getClass());
	private final MantemImagemI servicoMantemImagem;
	@Autowired
	ImagemRepository imagemRepository;

	public APIImagemController(MantemImagemI mantemImagem) {
		this.servicoMantemImagem = mantemImagem;
	}

//    @PostMapping
//    public ResponseEntity<Imagem> uploadImagem(@RequestParam("imagem") MultipartFile arquivo) throws IOException {
//        Imagem imagem = mantemImagem.salvar(arquivo);
//        return ResponseEntity.ok(imagem);
//    }

	@GetMapping
	public List<Imagem> listarImagens() {
		return servicoMantemImagem.listar();
	}

	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file1") MultipartFile file) {
		logger.info(">>>>>> manipula file upload chamado");
		if (!file.isEmpty()) {
			logger.info(">>>>>> manipula file upload file nao esta vazio");
			try {
				servicoMantemImagem.salvar(file);
				logger.info(">>>>>> manipula file upload enviado com sucesso");
				return ResponseEntity.ok().body("Imagem enviada com sucesso");
			} catch (FileNotFoundException e) {
				logger.info(">>>>>> manipula file upload arquivo não encontrado");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Arquivo nao encontrado");
			} catch (FileUploadException e) {
				logger.info(">>>>>> manipula file upload erro no upload");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao enviar o arquivo");
			} catch (IOException e) {
				logger.info(">>>>>> manipula file upload erro de i/o => " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha erro de I/O");
			}
		} else {
			logger.info(">>>>>> arquivo vazio ");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Arquivo vazio");
		}
	}

}
