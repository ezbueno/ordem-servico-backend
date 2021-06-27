package com.buenoezandro.os.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buenoezandro.os.domain.OrdemServico;
import com.buenoezandro.os.domain.enums.Prioridade;
import com.buenoezandro.os.domain.enums.Status;
import com.buenoezandro.os.dtos.OrdemServicoDTO;
import com.buenoezandro.os.repositories.OrdemServicoRepository;
import com.buenoezandro.os.services.exceptions.IllegalArgumentException;
import com.buenoezandro.os.services.exceptions.ObjectNotFoundException;
import com.buenoezandro.os.utils.MensagemUtils;

@Service
public class OrdemServicoService {

	private OrdemServicoRepository ordemServicoRepository;
	private TecnicoService tecnicoService;
	private ClienteService clienteService;

	@Autowired
	public OrdemServicoService(OrdemServicoRepository ordemServicoRepository, TecnicoService tecnicoService,
			ClienteService clienteService) {
		this.ordemServicoRepository = ordemServicoRepository;
		this.tecnicoService = tecnicoService;
		this.clienteService = clienteService;
	}

	@Transactional(readOnly = true)
	public OrdemServico findById(Integer id) {
		var ordemServico = this.ordemServicoRepository.findById(id);
		return ordemServico.orElseThrow(() -> new ObjectNotFoundException(
				MensagemUtils.OBJETO_NAO_ENCONTRADO + id + ", Tipo: " + OrdemServico.class.getName()));
	}

	@Transactional(readOnly = true)
	public List<OrdemServico> findAll() {
		return this.ordemServicoRepository.findAll();
	}

	@Transactional
	public OrdemServico create(OrdemServicoDTO ordemServicoDTO) {
		var tecnico = this.tecnicoService.findById(ordemServicoDTO.getTecnicoId());
		var cliente = this.clienteService.findById(ordemServicoDTO.getClienteId());

		var novaOrdemServico = new OrdemServico();
		novaOrdemServico.setId(null);
		novaOrdemServico.setPrioridade(Prioridade.toEnum(ordemServicoDTO.getPrioridade().getCod()));
		novaOrdemServico.setObservacoes(ordemServicoDTO.getObservacoes());
		novaOrdemServico.setStatus(Status.toEnum(ordemServicoDTO.getStatus().getCod()));
		novaOrdemServico.setTecnico(tecnico);
		novaOrdemServico.setCliente(cliente);

		return this.ordemServicoRepository.save(novaOrdemServico);
	}

	@Transactional
	public OrdemServico update(OrdemServicoDTO ordemServicoDTO) {
		this.findById(ordemServicoDTO.getId());

		var tecnico = this.tecnicoService.findById(ordemServicoDTO.getTecnicoId());
		var cliente = this.clienteService.findById(ordemServicoDTO.getClienteId());

		var atualizaOrdemServico = new OrdemServico();
		
		if (ordemServicoDTO.getPrioridade() == null || ordemServicoDTO.getPrioridade().getCod() > 2) {
			throw new IllegalArgumentException(MensagemUtils.PRIORIDADE_INVALIDA);
		}
	
		if (ordemServicoDTO.getStatus() == null || ordemServicoDTO.getStatus().getCod() > 2) {
			throw new IllegalArgumentException(MensagemUtils.STATUS_INVALIDO);
		}
		
		if (ordemServicoDTO.getStatus().getCod() == 2) {
			atualizaOrdemServico.setDataFechamento(LocalDateTime.now());
		}

		atualizaOrdemServico.setId(ordemServicoDTO.getId());
		atualizaOrdemServico.setPrioridade(Prioridade.toEnum(ordemServicoDTO.getPrioridade().getCod()));
		atualizaOrdemServico.setObservacoes(ordemServicoDTO.getObservacoes());	
		atualizaOrdemServico.setStatus(Status.toEnum(ordemServicoDTO.getStatus().getCod()));
		atualizaOrdemServico.setTecnico(tecnico);
		atualizaOrdemServico.setCliente(cliente);

		return this.ordemServicoRepository.save(atualizaOrdemServico);
	}

}
