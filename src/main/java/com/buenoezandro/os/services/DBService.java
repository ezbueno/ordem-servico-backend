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
		var c1 = new Cliente(null, "Carlos", "996.503.842-27", "(11) 98999-8888");
		var os1 = new OrdemServico(null, Prioridade.ALTA, "Teste de criação de OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);
		
		var t2 = new Tecnico(null, "Pedro", "200.333.950-71", "(11) 98888-8888");
		var c2 = new Cliente(null, "Paulo", "214.288.100-99", "(11) 98777-6666");
		var os2 = new OrdemServico(null, Prioridade.MEDIA, "Teste de criação de OS 2", Status.ANDAMENTO, t2, c2);
		
		t2.getList().add(os2);
		c2.getList().add(os2);
		
		var t3 = new Tecnico(null, "Marcos", "267.500.910-59", "(11) 95555-2222");
		var c3 = new Cliente(null, "Felipe", "243.507.240-60", "(11) 92222-4444");
		var os3 = new OrdemServico(null, Prioridade.BAIXA, "Teste de criação de OS 3", Status.ANDAMENTO, t3, c3);
		
		t3.getList().add(os3);
		c3.getList().add(os3);		
		
		this.tecnicoRepository.saveAll(Arrays.asList(t1, t2, t3));
		this.clienteRepository.saveAll(Arrays.asList(c1, c2, c3));
		this.ordemServicoRepository.saveAll(Arrays.asList(os1, os2, os3));
	}

}
