package com.codegu.comprovecrudfile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegu.comprovecrudfile.model.Anexo;
import com.codegu.comprovecrudfile.model.Usuario;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {

	List<Anexo> findByUsuario(Usuario usuario);
	
}
