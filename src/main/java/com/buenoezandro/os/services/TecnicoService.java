package com.buenoezandro.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buenoezandro.os.domain.Pessoa;
import com.buenoezandro.os.domain.Tecnico;
import com.buenoezandro.os.dtos.TecnicoDTO;
import com.buenoezandro.os.repositories.PessoaRepository;
import com.buenoezandro.os.repositories.TecnicoRepository;
import com.buenoezandro.os.services.exceptions.DataIntegrityViolationException;
import com.buenoezandro.os.services.exceptions.ObjectNotFoundException;
import com.buenoezandro.os.utils.MensagemUtils;

@Service
public class TecnicoService {

	private TecnicoRepository tecnicoRepository;
	private PessoaRepository pessoaRepository;

	@Autowired
	public TecnicoService(TecnicoRepository tecnicoRepository, PessoaRepository pessoaRepository) {
		this.tecnicoRepository = tecnicoRepository;
		this.pessoaRepository = pessoaRepository;
	}

	@Transactional(readOnly = true)
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tec = this.tecnicoRepository.findById(id);
		return tec.orElseThrow(() -> new ObjectNotFoundException(
				MensagemUtils.OBJETO_NAO_ENCONTRADO + id + ", Tipo: " + Tecnico.class.getName()));
	}

	@Transactional(readOnly = true)
	public List<Tecnico> findAll() {
		return this.tecnicoRepository.findAll();
	}

	@Transactional
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		if (this.findByCPF(tecnicoDTO) != null) {
			throw new DataIntegrityViolationException(MensagemUtils.CPF_JA_EXISTE + tecnicoDTO.getCpf());
		}

		return this.tecnicoRepository
				.save(new Tecnico(null, tecnicoDTO.getNome(), tecnicoDTO.getCpf(), tecnicoDTO.getTelefone()));
	}

	@Transactional
	public Tecnico update(Integer id, TecnicoDTO tecnicoDTO) {
		var tecnico = this.findById(id);

		if (this.findByCPF(tecnicoDTO) != null && this.findByCPF(tecnicoDTO).getId() != id) {
			throw new DataIntegrityViolationException(MensagemUtils.CPF_JA_EXISTE + tecnicoDTO.getCpf());
		}

		tecnico.setNome(tecnicoDTO.getNome());
		tecnico.setCpf(tecnicoDTO.getCpf());
		tecnico.setTelefone(tecnicoDTO.getTelefone());

		return this.tecnicoRepository.save(tecnico);
	}

	@Transactional
	public void delete(Integer id) {
		var tecnico = this.findById(id);

		if (!tecnico.getList().isEmpty()) {
			throw new DataIntegrityViolationException(MensagemUtils.TECNICO_NAO_PODE_SER_DELETADO);
		}

		this.tecnicoRepository.delete(tecnico);
	}

	private Pessoa findByCPF(TecnicoDTO tecnicoDTO) {
		Pessoa tecnico = this.pessoaRepository.findByCPF(tecnicoDTO.getCpf());

		if (tecnico != null) {
			return tecnico;
		}
		
		return null;
	}

}
