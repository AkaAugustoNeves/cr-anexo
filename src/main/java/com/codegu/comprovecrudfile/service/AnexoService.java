package com.codegu.comprovecrudfile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codegu.comprovecrudfile.model.Anexo;
import com.codegu.comprovecrudfile.model.Usuario;
import com.codegu.comprovecrudfile.repository.AnexoRepository;
import com.codegu.comprovecrudfile.repository.UsuarioRepository;

@Service
public class AnexoService {
	
	@Autowired
	private AnexoRepository ar;
	
	@Autowired
	private UsuarioRepository ur;
	
	public Anexo uploadBanco(MultipartFile file, Long idUsuario) {
		
		Optional<Usuario> usuario = ur.findById(idUsuario);
		if(usuario.isPresent()) {
			Anexo anexo = new Anexo();
			try {
				anexo.setDados(file.getBytes());
				anexo.setTipo(file.getContentType());
				anexo.setNome(file.getOriginalFilename());
				anexo.setUsuario(usuario.get());
				Anexo anexoF = ar.save(anexo);
				return anexoF;			
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
		
	}
	
	public Anexo download(Long id) {
		Optional<Anexo> anexo = ar.findById(id);
		if (anexo.isPresent()) {
			return anexo.get();
		}else {
			return null;
		}
		
	}
	
	public List<Anexo> listPorUser(Long idUsuario){
		Usuario usuario = ur.findById(idUsuario).get();
		List<Anexo> anexos = ar.findByUsuario(usuario);
		return anexos;
	}
	
}
