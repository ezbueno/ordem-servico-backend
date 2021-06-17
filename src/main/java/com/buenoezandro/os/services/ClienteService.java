package com.buenoezandro.os.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buenoezandro.os.domain.Cliente;
import com.buenoezandro.os.domain.Pessoa;
import com.buenoezandro.os.dtos.ClienteDTO;
import com.buenoezandro.os.repositories.ClienteRepository;
import com.buenoezandro.os.repositories.PessoaRepository;
import com.buenoezandro.os.services.exceptions.DataIntegrityViolationException;
import com.buenoezandro.os.services.exceptions.ObjectNotFoundException;
import com.buenoezandro.os.utils.MensagemUtils;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	private PessoaRepository pessoaRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository, PessoaRepository pessoaRepository) {
		this.clienteRepository = clienteRepository;
		this.pessoaRepository = pessoaRepository;
	}

	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Cliente findById(Integer id) {
		var cliente = this.clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				MensagemUtils.OBJETO_NAO_ENCONTRADO + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente create(ClienteDTO clienteDTO) {
		if (this.findByCPF(clienteDTO) != null) {
			throw new DataIntegrityViolationException(MensagemUtils.CPF_JA_EXISTE + clienteDTO.getCpf());
		}

		return this.clienteRepository
				.save(new Cliente(null, clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone()));
	}

	@Transactional
	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		var cliente = this.findById(id);

		if (this.findByCPF(clienteDTO) != null && this.findByCPF(clienteDTO).getId() != id) {
			throw new DataIntegrityViolationException(MensagemUtils.CPF_JA_EXISTE + clienteDTO.getCpf());
		}

		cliente.setNome(clienteDTO.getNome());
		cliente.setCpf(clienteDTO.getCpf());
		cliente.setTelefone(clienteDTO.getTelefone());

		return this.clienteRepository.save(cliente);
	}

	@Transactional
	public void delete(Integer id) {
		var cliente = this.findById(id);

		if (!cliente.getList().isEmpty()) {
			throw new DataIntegrityViolationException(MensagemUtils.CLIENTE_NAO_PODE_SER_DELETADO);
		}

		this.clienteRepository.delete(cliente);

	}

	private Pessoa findByCPF(ClienteDTO clienteDTO) {
		Pessoa cliente = this.pessoaRepository.findByCPF(clienteDTO.getCpf());

		if (cliente != null) {
			return cliente;
		}

		return null;
	}

}
