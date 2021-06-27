package com.buenoezandro.os.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.buenoezandro.os.domain.OrdemServico;
import com.buenoezandro.os.domain.enums.Prioridade;
import com.buenoezandro.os.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrdemServicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAbertura;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataFechamento;

	@NotNull(message = "O campo PRIORIDADE é obrigatório!")
	private Integer prioridade;
	
	@NotEmpty(message = "O campo OBSERVAÇÕES é obrigatório!")
	private String observacoes;
	
	@NotNull(message = "O campo STATUS é obrigatório!")
	private Integer status;
	
	private Integer tecnicoId;
	private Integer clienteId;

	public OrdemServicoDTO() {
	}

	public OrdemServicoDTO(OrdemServico ordemServico) {
		this.id = ordemServico.getId();
		this.dataAbertura = ordemServico.getDataAbertura();
		this.dataFechamento = ordemServico.getDataFechamento();
		this.prioridade = ordemServico.getPrioridade().getCod();
		this.observacoes = ordemServico.getObservacoes();
		this.status = ordemServico.getStatus().getCod();
		this.tecnicoId = ordemServico.getTecnico().getId();
		this.clienteId = ordemServico.getCliente().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Prioridade getPrioridade() {
		return Prioridade.toEnum(this.prioridade);
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Status getStatus() {
		return Status.toEnum(this.status);
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTecnicoId() {
		return tecnicoId;
	}

	public void setTecnicoId(Integer tecnicoId) {
		this.tecnicoId = tecnicoId;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

}
