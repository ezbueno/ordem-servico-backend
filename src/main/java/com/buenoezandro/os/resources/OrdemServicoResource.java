package com.buenoezandro.os.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buenoezandro.os.domain.OrdemServico;
import com.buenoezandro.os.dtos.OrdemServicoDTO;
import com.buenoezandro.os.services.OrdemServicoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/os")
public class OrdemServicoResource {

	private OrdemServicoService ordemServicoService;

	@Autowired
	public OrdemServicoResource(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrdemServicoDTO> findById(@PathVariable Integer id) {
		var ordemServico = this.ordemServicoService.findById(id);
		return ResponseEntity.ok().body(new OrdemServicoDTO(ordemServico));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrdemServicoDTO>> findByAll() {
		List<OrdemServico> ordensServico = this.ordemServicoService.findAll();
		return ResponseEntity.ok().body(ordensServico.stream().map(OrdemServicoDTO::new).collect(Collectors.toList()));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrdemServicoDTO> create(@Valid @RequestBody OrdemServicoDTO ordemServicoDTO) {
		var novaOrdemServico = this.ordemServicoService.create(ordemServicoDTO);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(novaOrdemServico.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrdemServicoDTO> update(@Valid @RequestBody OrdemServicoDTO ordemServicoDTO) {
		var ordemServico = this.ordemServicoService.update(ordemServicoDTO);
		return ResponseEntity.ok().body(new OrdemServicoDTO(ordemServico));
	}

}
