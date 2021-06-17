package com.buenoezandro.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buenoezandro.os.domain.Cliente;
import com.buenoezandro.os.domain.OrdemServico;
import com.buenoezandro.os.domain.Tecnico;
import com.buenoezandro.os.domain.enums.Prioridade;
import com.buenoezandro.os.domain.enums.Status;
import com.buenoezandro.os.repositories.ClienteRepository;
import com.buenoezandro.os.repositories.OrdemServicoRepository;
import com.buenoezandro.os.repositories.TecnicoRepository;

@Service
public class DBService {

	private TecnicoRepository tecnicoRepository;
	private ClienteRepository clienteRepository;
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	public DBService(TecnicoRepository tecnicoRepository, ClienteRepository clienteRepository,
			OrdemServicoRepository ordemServicoRepository) {
		this.tecnicoRepository = tecnicoRepository;
		this.clienteRepository = clienteRepository;
		this.ordemServicoRepository = ordemServicoRepository;

	}

	public void inserirDadosTeste() {
		var t1 = new Tecnico(null, "João", "311.239.896-30", "(11) 99999-9999");
		var t2 = new Tecnico(null, "Pedro", "200.333.950-71", "(11) 98888-8888");
		var c1 = new Cliente(null, "Carlos", "996.503.842-27", "(11) 98999-8888");
		var os1 = new OrdemServico(null, Prioridade.ALTA, "Teste de criação de OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		this.tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		this.clienteRepository.saveAll(Arrays.asList(c1));
		this.ordemServicoRepository.saveAll(Arrays.asList(os1));
	}

}
