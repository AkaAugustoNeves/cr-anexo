package com.codegu.comprovecrudfile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codegu.comprovecrudfile.controller.dto.AnexoDto;
import com.codegu.comprovecrudfile.model.Anexo;
import com.codegu.comprovecrudfile.service.AnexoService;

@RestController
@RequestMapping("upload")
public class AnexoController {

	@Autowired
	private AnexoService as;
	
	@PostMapping("/db/{idUsuario}")
	public AnexoDto uploadDb(@RequestParam("file") MultipartFile file, @PathVariable Long idUsuario ) {
		Anexo anexo = as.uploadBanco(file, idUsuario);
		AnexoDto anexoDto = new AnexoDto();
		if(anexo != null) {
			String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("upload/download/")
					.path(anexo.getId().toString())
					.toUriString();
			anexoDto.setDownloadUri(downloadUri);
			anexoDto.setId(anexo.getId());
			anexoDto.setTipo(anexo.getTipo());
			anexoDto.setStatus(true);
			anexoDto.setMensagem("arquivo upado com sucesso!");
			anexoDto.setUsuario(anexo.getUsuario().getName());
			return anexoDto;
		}
		anexoDto.setMensagem("teve um erro aqui ein!");
		return anexoDto;
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<ByteArrayResource> download(@PathVariable Long id){
		Anexo anexo = as.download(id);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(anexo.getTipo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+anexo.getNome())
				.body(new ByteArrayResource(anexo.getDados()));	
	}
	
	@GetMapping("/{idUsuario}")
	public List<Anexo> listporid(@PathVariable Long idUsuario) {
		
		return as.listPorUser(idUsuario);
	}

}
