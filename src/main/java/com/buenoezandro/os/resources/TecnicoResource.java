package com.buenoezandro.os.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buenoezandro.os.domain.Tecnico;
import com.buenoezandro.os.dtos.TecnicoDTO;
import com.buenoezandro.os.services.TecnicoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	private TecnicoService tecnicoService;

	@Autowired
	public TecnicoResource(TecnicoService tecnicoService) {
		this.tecnicoService = tecnicoService;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		var tecnico = this.tecnicoService.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> lista = this.tecnicoService.findAll();
		return ResponseEntity.ok().body(lista.stream().map(TecnicoDTO::new).collect(Collectors.toList()));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
		var novoTecnico = this.tecnicoService.create(tecnicoDTO);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoTecnico.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDTO) {
		var tecnico = this.tecnicoService.update(id, tecnicoDTO);
		return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
