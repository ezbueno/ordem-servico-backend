package com.buenoezandro.os.utils;

public abstract class MensagemUtils {

	protected MensagemUtils() {
	}

	public static final String PRIORIDADE_INVALIDA = "Prioridade inválida!";
	public static final String STATUS_INVALIDO = "Status inválido!";
	public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado! ID: ";
	public static final String CPF_JA_EXISTE = "CPF já cadastrado na base de dados! CPF: ";
	public static final String CAMPO_INVALIDO = "Erro na validação dos campos!";
	public static final String TECNICO_NAO_PODE_SER_DELETADO = "Técnico possui Ordens de Serviço, não pode ser deletado!";
	public static final String CLIENTE_NAO_PODE_SER_DELETADO = "Cliente possui Ordens de Serviço, não pode ser deletado!";

}
